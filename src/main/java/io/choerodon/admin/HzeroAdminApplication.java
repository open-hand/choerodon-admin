package io.choerodon.admin;

import io.choerodon.resource.annoation.EnableChoerodonResourceServer;
import org.hzero.autoconfigure.admin.EnableHZeroAdmin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableHZeroAdmin
@SpringBootApplication
@EnableChoerodonResourceServer
public class HzeroAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(HzeroAdminApplication.class, args);
    }

}
