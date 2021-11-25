package io.choerodon.admin.app.service;

import java.util.Map;
import java.util.Set;

import io.choerodon.admin.infra.enums.InvokeCountBusinessType;


/**
 * @author superlee
 */
public interface ApiService {



    /**
     * 根据日期范围和服务名在redis中查询api调用次数
     *
     * @param beginDate             开始日期
     * @param endDate               结束日期
     * @param menuLevel         和日期拼接的额外的key
     * @param paramKey              api或者service集合的参数名
     * @param additionalParamValues 额外的paramValues
     * @param businessType          业务类型
     * @return map
     */
    Map<String, Object> queryInvokeCount(String beginDate,
                                         String endDate,
                                         String code,
                                         String menuLevel,
                                         String paramKey,
                                         Set<String> additionalParamValues,
                                         InvokeCountBusinessType businessType);

}
