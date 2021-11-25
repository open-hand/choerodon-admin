package io.choerodon.admin.app.service.impl;

import io.choerodon.admin.api.dto.CiCdPipelineRecordVO;
import io.choerodon.admin.api.dto.DevopsCdJobRecordDTO;
import io.choerodon.admin.api.dto.MenuClickDTO;
import io.choerodon.admin.app.service.ApiService;
import io.choerodon.admin.app.service.StatisticService;
import io.choerodon.admin.infra.enums.InvokeCountBusinessType;
import io.choerodon.core.exception.CommonException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author superlee
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    private StringRedisTemplate redisTemplate;

    private ApiService apiService;

    @Autowired
    @Qualifier("restTemplateForIp")
    private RestTemplate restTemplate;

    private static final String COLON = ":";

    private static final String[] ROOT_CODES = {"choerodon.code.top.site", "choerodon.code.top.organization", "choerodon.code.top.project", "choerodon.code.top.user"};

    public StatisticServiceImpl(StringRedisTemplate redisTemplate, ApiService apiService) {
        this.redisTemplate = redisTemplate;
        this.apiService = apiService;
    }

    @Override
    public void saveMenuClick(List<MenuClickDTO> menuClickList) {

        menuClickList.forEach(menuClickDTO -> assertCode(menuClickDTO.getRootCode()));
        LocalDate localDate = LocalDate.now();
        menuClickList.forEach(menuClickDTO -> {
            String code = getCode(menuClickDTO.getRootCode());
            List<MenuClickDTO.Menu> menus = menuClickDTO.getMenus();
            StringBuilder builder = new StringBuilder();
            builder.append(localDate.toString()).append(COLON).append("zSet").append(COLON).append(code);
            String key = builder.toString();
            cache2Redis(menus, key);
        });
    }

    private String getCode(String code) {
        int index = code.lastIndexOf('.');
        return code.substring(index + 1);
    }

    @Override
    public Map<String, Object> queryMenuClick(String beginDate, String endDate, String code) {
        return apiService.queryInvokeCount(beginDate,
                endDate,
                code,
                getCode(code),
                "menu",
                Collections.emptySet(),
                InvokeCountBusinessType.MENU);
    }

    @Override
    public void auidt(CiCdPipelineRecordVO devopsPipelineVO) {
        DevopsCdJobRecordDTO currentCdJob = devopsPipelineVO.getCurrentCdJob();

        try {
            Thread.sleep(1000 * 60 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Map<String, String> map = new HashMap<>();
        map.put("pipeline_record_id",devopsPipelineVO.getCdRecordId().toString());
        map.put("stage_record_id",currentCdJob.getStageRecordId().toString());
        map.put("job_record_id",currentCdJob.getId().toString());
        map.put("callback_token",currentCdJob.getCallbackToken());
        map.put("approval_status",devopsPipelineVO.getCdRecordId() % 2 == 0 ? "true" : "false");


        ResponseEntity<Void> responseEntity = null;
        try {
            restTemplate.put("http://172.23.16.92:30094/devops/v1/cd_pipeline/external_approval_task/callback_url", map);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                throw new RestClientException("error.trigger.external.approval.task");
            }
        } catch (RestClientException e) {
        }

    }

    private void cache2Redis(List<MenuClickDTO.Menu> menus, String key) {
        if (redisTemplate.hasKey(key)) {
            menus.forEach(menu -> {
                int count = menu.getCount() == null ? 0 : menu.getCount();
                String value = menu.getCode();
                redisTemplate.opsForZSet().incrementScore(key, value, count);
            });
        } else {
            menus.forEach(menu -> {
                int count = menu.getCount() == null ? 0 : menu.getCount();
                String value = menu.getCode();
                redisTemplate.opsForZSet().add(key, value, count);
            });
            redisTemplate.expire(key, 31, TimeUnit.DAYS);
        }
    }

    private void assertCode(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new CommonException("error.menuClick.code.empty");
        }
        validateCode(code);
    }

    private void validateCode(String code) {
        if (!Arrays.asList(ROOT_CODES).contains(code)) {
            throw new CommonException("error.menuClick.illegal.code");
        }
    }
}
