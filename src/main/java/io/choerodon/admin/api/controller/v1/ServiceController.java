package io.choerodon.admin.api.controller.v1;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.hzero.admin.app.service.HServiceService;
import org.hzero.admin.domain.entity.HService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author wanghao
 * @since 2021/1/14 10:34
 */
@RestController
@RequestMapping("/choerodon/v1/services")
public class ServiceController {

    @Autowired
    private HServiceService serviceService;

    @GetMapping("/")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @ApiOperation("查询已经部署的应用服务")
    public ResponseEntity<List<HService>> listServices() {
        return ResponseEntity.ok(serviceService.selectServices(null));
    }
}
