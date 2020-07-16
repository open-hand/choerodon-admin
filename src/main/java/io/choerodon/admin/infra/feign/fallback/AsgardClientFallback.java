package io.choerodon.admin.infra.feign.fallback;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.choerodon.admin.infra.feign.AsgardClient;
import io.choerodon.core.exception.CommonException;

/**
 * @author scp
 * @date 2020/7/16
 * @description
 */
@Component
public class AsgardClientFallback implements AsgardClient {
    @Override
    public ResponseEntity<Void> refresh(String serviceName) {
        throw new CommonException("error.refresh.saga");
    }
}
