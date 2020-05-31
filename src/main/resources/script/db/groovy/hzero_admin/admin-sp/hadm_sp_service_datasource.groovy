package script.db

databaseChangeLog(logicalFilePath: 'script/db/hadm_sp_service_datasource.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2020-04-28-hadm_sp_service_datasource") {
        def weight = 1
        if (helper.isSqlServer()) {
            weight = 2
        } else if (helper.isOracle()) {
            weight = 3
        }
        if (helper.dbType().isSupportSequence()) {
            createSequence(sequenceName: 'hadm_sp_service_datasource_s', startValue: "1")
        }
        createTable(tableName: "hadm_sp_service_datasource", remarks: "服务与数据源映射关系") {
            column(name: "service_datasource_id", type: "bigint", autoIncrement: true, remarks: "表ID，主键") {
                constraints(primaryKey: true)
            }
            column(name: "service_code", type: "varchar(" + 60 * weight + ")", remarks: "服务编码") {
                constraints(nullable: "false")
            }
            column(name: "service_version", type: "varchar(" + 30 * weight + ")", remarks: "服务版本") {
                constraints(nullable: "true")
            }
            column(name: "ds_url", type: "varchar(" + 240 * weight + ")", remarks: "数据源url"){
                constraints(nullable: "false")
            }
            column(name: "ds_username", type: "varchar(" + 60 * weight + ")", remarks: "数据源账号"){
                constraints(nullable: "true")
            }
            column(name: "ds_password", type: "varchar(" + 60 * weight + ")", remarks: "数据源密码"){
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
        addUniqueConstraint(columnNames: "service_code,service_version", tableName: "hadm_sp_service_datasource", constraintName: "hadm_sp_service_datasource_u1")
    }

}