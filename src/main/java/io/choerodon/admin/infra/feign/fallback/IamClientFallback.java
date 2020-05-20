package io.choerodon.admin.infra.feign.fallback;


import org.springframework.stereotype.Component;

import io.choerodon.admin.infra.feign.IamClient;

/**
 * iam service feign失败回调函数
 *
 * @author superlee
 * @since 2019-06-11
 */
@Component
public class IamClientFallback implements IamClient {

}
