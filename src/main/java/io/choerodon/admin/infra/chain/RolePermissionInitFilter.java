package io.choerodon.admin.infra.chain;

import org.apache.commons.lang3.StringUtils;
import org.hzero.admin.domain.vo.InitChainContext;
import org.hzero.admin.domain.vo.Service;
import org.hzero.admin.infra.chain.InitChain;
import org.hzero.admin.infra.chain.InitFilter;
import org.hzero.core.redis.RedisHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    private static final String CHOERODON_ROLE_PERMISION_ASYNC_PREFIX = "choerodon:permission:async";

    @Autowired
    private IamClient iamClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(RolePermissionInitFilter.class);


    @Override
    public void doFilter(InitChain chain, InitChainContext context) {
        LOGGER.info(">>>>>>>>> do async role permission filter<<<<<<<<<<<<");
        Service service = context.getService();
        String key = CHOERODON_ROLE_PERMISION_ASYNC_PREFIX + ":" + service.getServiceName() + ":" + service.getVersion();
        boolean notAsyncFlag = StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(key));
        if (notAsyncFlag) {
            LOGGER.info(">>>>>>>>> start async role permission. serviceName:{}, serviceVersion:{} >>>>>>>>>>>>>>", service.getServiceName(), service.getVersion());
            ResponseEntity<Void> response = iamClient.asyncRolePermision();
            if (!response.getStatusCode().is2xxSuccessful()) {
                LOGGER.error("refresh role permission failed, cause: " + (response.getBody() == null ? response.getStatusCodeValue() : response.getBody()));
            } else {
                stringRedisTemplate.opsForValue().set(key, "true");
            }
        } else {
            LOGGER.info(">>>>>>>>> serviceName:{}, serviceVersion:{} role permission Synced. Skip >>>>>>>>>>>>>>", service.getServiceName(), service.getVersion());
        }
        chain.initNext(chain, context);
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
