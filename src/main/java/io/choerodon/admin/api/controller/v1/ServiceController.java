package io.choerodon.admin.api.controller.v1;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiOperation;
import org.hzero.admin.app.service.HServiceService;
import org.hzero.admin.domain.entity.HService;
import org.hzero.core.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.choerodon.admin.app.service.ServiceC7nService;
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
@RequestMapping(value = "/choerodon/v1/services")
public class ServiceController {

    @Autowired
    private HServiceService serviceService;
    @Autowired
    private ServiceC7nService serviceC7nService;


    @GetMapping
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @ApiOperation("查询已经部署的应用服务")
    public ResponseEntity<List<HService>> listServices() {
        return ResponseEntity.ok(serviceService.selectServices(new HService()).stream().filter(t -> !t.getServiceCode().contains("hzero")).collect(Collectors.toList()));
    }


    @GetMapping("/service_codes")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @ApiOperation("查询已经部署的应用服务")
    public ResponseEntity<List<String>> listServiceCodes() {
       List<String> serviceCodes= serviceService.selectServices(new HService()).stream().map(HService::getServiceCode).filter(serviceCode -> !serviceCode.contains("hzero")).collect(Collectors.toList());
        return ResponseEntity.ok(serviceCodes);
    }


    @GetMapping(value = "/model")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @ApiOperation(value = "获取已经部署的所有模块")
    public ResponseEntity<Set<String>> listModels() {
        return Results.success(serviceC7nService.listModels());
    }
}
