package io.choerodon.admin.infra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.choerodon.admin.infra.feign.fallback.AsgardClientFallback;

/**
 * @author scp
 * @date 2020/7/16
 * @description
 */
@FeignClient(value = "hzero-asgard", fallback = AsgardClientFallback.class)
public interface AsgardClient {
    @PostMapping(value = "/v1/sagas/fresh")
    ResponseEntity<Void> refresh(@RequestParam("serviceName") String serviceName);
}
