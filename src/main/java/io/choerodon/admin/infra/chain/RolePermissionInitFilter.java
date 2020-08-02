package io.choerodon.admin.infra.chain;

import org.hzero.admin.domain.vo.InitChainContext;
import org.hzero.admin.infra.chain.InitChain;
import org.hzero.admin.infra.chain.InitFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.choerodon.admin.infra.feign.IamClient;

/**
 * saga初始化filter
 *
 * @author scp
 * @date 2020/6/28 10:40 上午
 */
@Component
public class RolePermissionInitFilter implements InitFilter, Ordered {

    @Autowired
    private IamClient iamClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(RolePermissionInitFilter.class);


    @Override
    public void doFilter(InitChain chain, InitChainContext context) {
        LOGGER.info(">>>>>>>>> start async role permission >>>>>>>>>>>>>>");
        ResponseEntity<Void> response = iamClient.asyncRolePermision();

        if (!response.getStatusCode().is2xxSuccessful()) {
            LOGGER.error("refresh role permission failed, cause: " + (response.getBody() == null ? response.getStatusCodeValue() : response.getBody()));
        }
        chain.initNext(chain, context);
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
