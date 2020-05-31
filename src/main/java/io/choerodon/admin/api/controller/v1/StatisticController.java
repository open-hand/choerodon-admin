package io.choerodon.admin.api.controller.v1;

import java.util.List;
import java.util.Map;

import io.choerodon.admin.infra.config.C7nSwaggerApiConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.admin.api.dto.MenuClickDTO;
import io.choerodon.admin.app.service.StatisticService;

import io.choerodon.core.iam.ResourceLevel;

import io.choerodon.swagger.annotation.Permission;

/**
 * @author superlee
 */
@Api(tags = C7nSwaggerApiConfig.CHOERODON_STATISTIC)
@RestController
@RequestMapping(value = "/choerodon/v1/statistic")
public class StatisticController {

    private StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Permission(permissionLogin = true)
    @ApiOperation("菜单点击次数统计保存接口")
    @PostMapping("/menu_click/save")
    public ResponseEntity saveMenuClick(@RequestBody List<MenuClickDTO> menuClickList) {
        statisticService.saveMenuClick(menuClickList);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Permission(level = ResourceLevel.SITE)
    @ApiOperation("根据日期和层级查询菜单的调用次数")
    @GetMapping("/menu_click")
    public ResponseEntity<Map<String, Object>> queryMenuClick(@RequestParam(value = "begin_date")
                                                              @ApiParam(value = "日期格式yyyy-MM-dd", required = true) String beginDate,
                                                              @RequestParam(value = "end_date")
                                                              @ApiParam(value = "日期格式yyyy-MM-dd", required = true) String endDate,
                                                              @RequestParam String code) {
        return new ResponseEntity(statisticService.queryMenuClick(beginDate, endDate, code), HttpStatus.OK);
    }

}
