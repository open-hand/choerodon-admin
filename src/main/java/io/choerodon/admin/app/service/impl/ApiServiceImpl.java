package io.choerodon.admin.app.service.impl;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.collections.map.MultiKeyMap;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.choerodon.admin.app.service.ApiService;
import io.choerodon.admin.infra.enums.InvokeCountBusinessType;
import io.choerodon.admin.infra.enums.MenuLabelEnum;
import io.choerodon.admin.infra.feign.IamClient;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.iam.ResourceLevel;

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

    private StringRedisTemplate redisTemplate;
    private IamClient iamClient;

    @Override
    public Map<String, Object> queryInvokeCount(String beginDate, String endDate, String additionalKey, String paramKey, Set<String> additionalParamValues, InvokeCountBusinessType businessType) {
        Map<String, Object> map = new HashMap<>();
        Set<String> date = new LinkedHashSet<>();
        List<Map<String, Object>> details = new ArrayList<>();
        map.put("date", date);
        map.put("details", details);
        LocalDate begin = LocalDate.parse(beginDate);
        LocalDate end = LocalDate.parse(endDate);
        Set<String> paramValues = new HashSet<>();
        paramValues.addAll(additionalParamValues);
        MultiKeyMap multiKeyMap = getInvokeCount(paramValues, date, begin, end, additionalKey, businessType);
        Map<String, Double> lastDayCount = new HashMap<>();
        paramValues.forEach(paramValue -> {
            Map<String, Object> apiMap = new HashMap<>(2);
            List<Double> data = new ArrayList<>();
            apiMap.put(paramKey, paramValue);
            apiMap.put("data", data);
            date.forEach(currentDate -> {
                String key = getKeyByDateAndAdditionKey(additionalKey, currentDate);
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

    private MultiKeyMap getInvokeCount(Set<String> paramValues, Set<String> date,
                                       LocalDate begin, LocalDate end, String additionKey,
                                       InvokeCountBusinessType businessType) {
        Map<String, String> menuMap = getMenuMap(businessType,additionKey);
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
                }
                paramValues.add(code);
                multiKeyMap.put(key, code, tuple.getScore());
            });
            begin = begin.plusDays(1);
        }
        return multiKeyMap;
    }

    private Map<String, String> getMenuMap(InvokeCountBusinessType businessType, String additionKey) {
        Map<String, String> menuMap = new HashMap<>();
        if (InvokeCountBusinessType.MENU.equals(businessType)) {
            try {
                String label;
                if (ResourceLevel.SITE.value().equals(additionKey)) {
                    label = MenuLabelEnum.SITE_MENU.value();
                }
                if (ResourceLevel.ORGANIZATION.value().equals(additionKey)) {
                    label = MenuLabelEnum.TENANT_MENU.value();
                }
                if (ResourceLevel.PROJECT.value().equals(additionKey)) {
                    label = MenuLabelEnum.GENERAL_MENU.value();
                }
                if (ResourceLevel.USER.value().equals(additionKey)) {
                    label = MenuLabelEnum.USER_MENU.value();
                }
                ResponseEntity<List<>> response = iamClient.listMenuByLabel(label);
                List<MenuDTO> menus = response.getBody();
                menus.forEach(m -> menuMap.put(m.getCode(), m.getName()));
            } catch (Exception e) {
                throw new CommonException(e);
            }
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
