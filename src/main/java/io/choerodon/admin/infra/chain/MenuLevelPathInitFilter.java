package io.choerodon.admin.infra.chain;

import org.hzero.admin.domain.vo.InitChainContext;
import org.hzero.admin.infra.chain.InitChain;
import org.hzero.admin.infra.chain.InitFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import io.choerodon.admin.infra.feign.IamClient;

/**
 * 初始化菜单路径
 */
@Component
public class MenuLevelPathInitFilter implements InitFilter, Ordered {

    @Autowired
    private IamClient iamClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuLevelPathInitFilter.class);

    @Override
    public void doFilter(InitChain chain, InitChainContext context) {
        LOGGER.info(">>>>>>>>> start init menu level path<<<<<<<<<<<<");
        try {
            iamClient.fixMenuLevelPath();
            LOGGER.info(">>>>>>>>> init menu level path success<<<<<<<<<<<<");
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>> init menu level path failed<<<<<<<<<<<<", e);
        }

        chain.initNext(chain, context);
    }

    @Override
    public int getOrder() {
        return 90;
    }
}
