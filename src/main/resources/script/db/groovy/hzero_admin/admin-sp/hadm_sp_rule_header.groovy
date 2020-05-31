package script.db

databaseChangeLog(logicalFilePath: 'script/db/hadm_sp_rule_header.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2020-04-28-hadm_sp_rule_header") {
        def weight = 1
        if (helper.isSqlServer()) {
            weight = 2
        } else if (helper.isOracle()) {
            weight = 3
        }
        if (helper.dbType().isSupportSequence()) {
            createSequence(sequenceName: 'hadm_sp_rule_header_s', startValue: "1")
        }
        createTable(tableName: "hadm_sp_rule_header", remarks: "sharding-proxy分库分表配置头") {
            column(name: "rule_header_id", type: "bigint", autoIncrement: true, remarks: "表ID，主键") {
                constraints(primaryKey: true)
            }
            column(name: "service_datasource_id", type: "bigint", remarks: "服务数据源ID,hadm_sp_service_datasource.service_datasource_id") {
                constraints(nullable: "false")
            }
            column(name: "datasource_group_id", type: "bigint", remarks: "逻辑数据源组ID,hadm_sp_datasource_group.datasource_group_id") {
                constraints(nullable: "false")
            }
            column(name: "proxy_ds_url", type: "varchar(" + 240 * weight + ")", remarks: "代理数据源url") {
                constraints(nullable: "false")
            }
            column(name: "proxy_ds_username", type: "varchar(" + 60 * weight + ")", remarks: "代理数据源账号") {
                constraints(nullable: "true")
            }
            column(name: "proxy_ds_password", type: "varchar(" + 60 * weight + ")", remarks: "代理数据源密码") {
                constraints(nullable: "true")
            }
            column(name: "object_version_number", type: "bigint", defaultValue: "1", remarks: "行版本号，用来处理锁") {
                constraints(nullable: "false")
            }
            column(name: "creation_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "") {
                constraints(nullable: "false")
            }
            column(name: "created_by", type: "bigint", defaultValue: "-1", remarks: "") {
                constraints(nullable: "false")
            }
            column(name: "last_updated_by", type: "bigint", defaultValue: "-1", remarks: "") {
                constraints(nullable: "false")
            }
            column(name: "last_update_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "") {
                constraints(nullable: "false")
            }
        }
        addUniqueConstraint(columnNames: "service_datasource_id", tableName: "hadm_sp_rule_header", constraintName: "hadm_sp_rule_header_u1")
    }

}