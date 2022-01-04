package io.choerodon.admin.app.service.impl;

import static io.choerodon.admin.infra.constants.ServerConstants.*;

import java.util.HashSet;
import java.util.Set;

import org.hzero.admin.app.service.HServiceService;
import org.hzero.admin.domain.entity.HService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.choerodon.admin.app.service.ServiceC7nService;

/**
 * @Author: scp
 * @Description:
 * @Date: Created in 2021/1/19
 * @Modified By:
 */
@Service
public class ServiceC7nServiceImpl implements ServiceC7nService {
    @Autowired
    private HServiceService serviceService;

    @Override
    public Set<String> listModels() {
        Set<String> services = new HashSet<>();
        serviceService.selectServices(new HService()).forEach(t -> {
            String serviceCode = t.getServiceCode();
            if (serviceCode.equals(SERVER_AGILE)) {
                services.add(AGILE);
            } else if (serviceCode.equals(SERVER_DEVOPS)) {
                services.add(DEVOPS);
                services.add(RESOURCE_MANAGEMENT);
            } else if (serviceCode.equals(SERVER_GITLAB) || serviceCode.equals(SERVER_WORKFLOW)
                    || serviceCode.equals(SERVER_CODE) || serviceCode.equals(SERVER_PROD)) {
                services.add(DEVOPS);
            } else if (serviceCode.equals(SERVER_TEST)) {
                services.add(TEST);
            }
        });
        return services;
    }
}
