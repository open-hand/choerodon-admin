package io.choerodon.admin.app.service.impl;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.collections.map.MultiKeyMap;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.choerodon.admin.api.dto.Menu;
import io.choerodon.admin.app.service.ApiService;
import io.choerodon.admin.infra.enums.InvokeCountBusinessType;
import io.choerodon.admin.infra.enums.MenuLabelEnum;
import io.choerodon.admin.infra.feign.IamClient;
import io.choerodon.core.exception.CommonException;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author wanghao
 * @since 2020/5/19 16:56
 */
@Service
public class ApiServiceImpl implements ApiService {
    private static final String COLON = ":";

    private final StringRedisTemplate redisTemplate;
    private final IamClient iamClient;

    public ApiServiceImpl(StringRedisTemplate redisTemplate, IamClient iamClient) {
        this.redisTemplate = redisTemplate;
        this.iamClient = iamClient;
    }

    @Override
    public Map<String, Object> queryInvokeCount(String beginDate,
                                                String endDate,
                                                String code,
                                                String menuLevel,
                                                String paramKey,
                                                Set<String> additionalParamValues,
                                                InvokeCountBusinessType businessType) {
        Map<String, Object> map = new HashMap<>();
        Set<String> date = new LinkedHashSet<>();
        List<Map<String, Object>> details = new ArrayList<>();
        map.put("date", date);
        map.put("details", details);
        LocalDate begin = LocalDate.parse(beginDate);
        LocalDate end = LocalDate.parse(endDate);
        Set<String> paramValues = new HashSet<>();
        paramValues.addAll(additionalParamValues);
        MultiKeyMap multiKeyMap = getInvokeCount(paramValues, date, code, begin, end, menuLevel, businessType);
        Map<String, Double> lastDayCount = new HashMap<>();
        paramValues.forEach(paramValue -> {
            Map<String, Object> apiMap = new HashMap<>(2);
            List<Double> data = new ArrayList<>();
            apiMap.put(paramKey, paramValue);
            apiMap.put("data", data);
            date.forEach(currentDate -> {
                String key = getKeyByDateAndAdditionKey(menuLevel, currentDate);
                Object value = multiKeyMap.get(key, paramValue);
                Double count = (value == null ? 0D : (Double) value);
                lastDayCount.put(paramValue, count);
                data.add(count);
            });

            details.add(apiMap);
        });
        List<String> sortedKey =
                lastDayCount.entrySet()
                        .stream()
                        .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
        map.put(paramKey, sortedKey);
        return map;
    }

    private MultiKeyMap getInvokeCount(Set<String> paramValues,
                                       Set<String> date,
                                       String levelCode,
                                       LocalDate begin,
                                       LocalDate end,
                                       String additionKey,
                                       InvokeCountBusinessType businessType) {
        Map<String, String> menuMap = getMenuMap(levelCode);
        MultiKeyMap multiKeyMap = new MultiKeyMap();
        if (begin.isAfter(end)) {
            throw new CommonException("error.date.order");
        }
        while (!begin.isAfter(end)) {
            String currentDate = begin.toString();
            date.add(currentDate);
            String key = getKeyByDateAndAdditionKey(additionKey, currentDate);
            Set<ZSetOperations.TypedTuple<String>> serviceSet = redisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
            serviceSet.forEach(tuple -> {
                String code = tuple.getValue();
                if (menuMap.get(code) != null) {
                    code = code + COLON + menuMap.get(code);
                    paramValues.add(code);
                    multiKeyMap.put(key, code, tuple.getScore());
                }
            });
            begin = begin.plusDays(1);
        }
        return multiKeyMap;
    }

    private Map<String, String> getMenuMap(String code) {
        Map<String, String> menuMap = new HashMap<>();
        try {
            ResponseEntity<List<Menu>> listResponseEntity = iamClient.listMenuByLevelCode(code);
            List<Menu> menus = listResponseEntity.getBody();
            menus.forEach(m -> menuMap.put(m.getCode(), m.getName()));
        } catch (Exception e) {
            throw new CommonException(e);
        }
        return menuMap;
    }
    private String getKeyByDateAndAdditionKey(String additionalKey, String currentDate) {
        StringBuilder builder = new StringBuilder(currentDate).append(COLON).append("zSet");
        if (!StringUtils.isEmpty(additionalKey)) {
            builder.append(COLON).append(additionalKey);
        }
        return builder.toString();
    }
}
