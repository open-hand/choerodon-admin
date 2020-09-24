# choerodon-admin
平台治理服务

## Introduction
平台治理服务，提供自动化的路由刷新、权限刷新、swagger信息刷新功能，提供界面化的服务、配置、路由、限流、熔断管理功能以及Spring Boot Admin控制台。
此服务是对[hzero-admin](https://github.com/open-hand/hzero-admin.git)的二开，添加了菜单统计的功能。

## Documentation
- 更多详情请参考`hzero-admin`[中文文档](http://open.hand-china.com/document-center/doc/application/10041/10148?doc_id=5133)

## Features

- 服务管理：统一管理系统所有服务
- 服务配置：维护服务配置信息
- 服务治理：配置管理服务的熔断、限流与在线运维等
- 服务监控：提供API统计与服务实例监控

## Architecture

![](http://file.open.hand-china.com/hsop-image/doc_classify/0/b8a7b8d8053446589d46f497959e21a7/Admin%E6%9C%8D%E5%8A%A1%E6%9E%B6%E6%9E%84%E5%9B%BE.png)

## Dependencies

* 服务依赖

```xml
<dependency>
    <groupId>org.hzero</groupId>
    <artifactId>hzero-admin-saas</artifactId>
    <version>${hzero.service.version}</version>
</dependency>
```

## Data initialization

- 创建数据库，本地创建 `hzero_admin` 数据库和默认用户，示例如下：

  ```sql
  CREATE USER 'choerodon'@'%' IDENTIFIED BY "123456";
  CREATE DATABASE hzero_admin DEFAULT CHARACTER SET utf8;
  GRANT ALL PRIVILEGES ON hzero_admin.* TO choerodon@'%';
  FLUSH PRIVILEGES;
  ```

- 初始化 `hzero_admin` 数据库，运行项目根目录下的 `init-database.sh`，该脚本默认初始化数据库的地址为 `localhost`，若有变更需要修改脚本文件

  ```sh
  sh init-database.sh
  ```
  
## Changelog

- [更新日志](./CHANGELOG.zh-CN.md)


## Contributing

欢迎参与项目贡献！比如提交PR修复一个bug，或者新建Issue讨论新特性或者变更。

Copyright (c) 2020-present, CHOERODON
