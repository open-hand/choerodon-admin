package io.choerodon.admin;

import org.hzero.autoconfigure.admin.EnableHZeroAdmin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients("io.choerodon.admin")
@EnableHZeroAdmin
@SpringBootApplication
public class HzeroAdminApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(HzeroAdminApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean(name = "restTemplateForIp")
    public RestTemplate restTemplateForIp() {
        return new RestTemplate();
    }
}
