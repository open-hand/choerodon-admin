package io.choerodon.admin.infra.feign.fallback;


import io.choerodon.admin.api.dto.Menu;
import io.choerodon.core.exception.CommonException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.choerodon.admin.infra.feign.IamClient;

import java.util.List;
import java.util.Set;

/**
 * iam service feign失败回调函数
 *
 * @author superlee
 * @since 2019-06-11
 */
@Component
public class IamClientFallback implements IamClient {

    @Override
    public ResponseEntity<List<Menu>> listMenuByLabel(Set<String> labels) {
        throw new CommonException("error.query.menu");
    }

    @Override
    public ResponseEntity<Void> asyncRolePermision() {
        throw new CommonException("error.async.role.permission");
    }

    @Override
    public ResponseEntity<Void> fixMenuLevelPath() {
        throw new CommonException("error.fix.menu.levelPath");
    }
}
