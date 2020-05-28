package io.choerodon.admin;

import org.hzero.autoconfigure.admin.EnableHZeroAdmin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("io.choerodon.admin")
@EnableHZeroAdmin
@SpringBootApplication
public class HzeroAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(HzeroAdminApplication.class, args);
    }

}
