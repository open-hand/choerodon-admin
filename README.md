## 使用说明
### 概述

管理服务，基础服务之一，把路由、限流、熔断等功能易用化，集中在管理服务来管控，提供自动化的路由刷新、权限刷新、swagger信息刷新服务，提供界面化的服务、配置、路由、限流、熔断管理功能以及Spring Boot Admin控制台。

## 服务配置 

- `application.yml`
```
hzero:
  # Deprecated replace by hzero.auto-refresh.permission.enable
  # permission:
    # 是否自动刷新服务权限
    # parse-permission: ${HZERO_PERMISSION_PARSE_PERMISSION:true}
  config:
    # 跳过特定服务的路由刷新
    route:
      skip-parse-services: register, gateway, oauth
  auto-refresh:
    # 是否开启权限自动刷新
    permisson:
      enable: ${HZERO_AUTO_REFRESH_PERMISSION_ENABLE:true}
    # 是否开启路由自动刷新
    route:
      enable: ${HZERO_AUTO_REFRESH_ROUTE_ENABLE:true}
    # 是否开启swagger信息自动刷新
    swagger:
      enable: ${HZERO_AUTO_REFRESH_SWAGGER_ENABLE:true}

```
- `启动类配置`

@EnableHZeroAdmin是必须加上的注解，用于驱动管理服务的自动配置类，从而启用管理服务功能。

@EnableDiscoveryClient使服务作为服务发现客户端注册到注册中心，推荐使用@EnableDiscoveryClient而不是@EnableEurekaClient，@EnableDiscoveryClient更加灵活。

另外，通过@SpringBootApplication启动Spring Boot自动配置类，启动Spring Boot应用。
```
@EnableHZeroAdmin
@EnableDiscoveryClient
@SpringBootApplication
public class HzeroAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(HzeroAdminApplication.class, args);
    }
}
```


