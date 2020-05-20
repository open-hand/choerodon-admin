package io.choerodon.admin.infra.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import io.choerodon.admin.infra.feign.fallback.IamClientFallback;

/**
 * iam service feign client
 * @author superlee
 * @since 2019-06-11
 */
@FeignClient(value = "hzero-iam", fallback = IamClientFallback.class)
public interface IamClient {

    /**
     * 查询所有菜单
     * @return
     */
    @GetMapping("/v1/menus/list")
    ResponseEntity<List<MenuDTO>> list();

}
