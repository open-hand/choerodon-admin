package script.db

databaseChangeLog(logicalFilePath: 'script/db/hadm_sp_datasource.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2020-04-28-hadm_sp_datasource") {
        def weight = 1
        if (helper.isSqlServer()) {
            weight = 2
        } else if (helper.isOracle()) {
            weight = 3
        }
        if (helper.dbType().isSupportSequence()) {
            createSequence(sequenceName: 'hadm_sp_datasource_s', startValue: "1")
        }
        createTable(tableName: "hadm_sp_datasource", remarks: "逻辑数据源") {
            column(name: "datasource_id", type: "bigint", autoIncrement: true, remarks: "表ID，主键") {
                constraints(primaryKey: true)
            }
            column(name: "datasource_group_id", type: "bigint", remarks: "逻辑数据源组ID,hadm_sp_datasource_group.datasource_group_id") {
                constraints(nullable: "false")
            }
            column(name: "datasource_info", type: "longtext", remarks: "数据源信息json，主从结构或单数据源结构") {
                constraints(nullable: "false")
            }
            column(name: "order", type: "int(11)", remarks: "用于排序") {
                constraints(nullable: "false")
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
        createIndex(tableName: "hadm_sp_datasource", indexName: "hadm_sp_datasource_n1") {
            column(name: "datasource_group_id")
        }
        addUniqueConstraint(columnNames: "order", tableName: "hadm_sp_datasource", constraintName: "hadm_sp_datasource_u1")
    }

}