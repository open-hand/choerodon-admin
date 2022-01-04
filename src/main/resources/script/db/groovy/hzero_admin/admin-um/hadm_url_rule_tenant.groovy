package script.db

databaseChangeLog(logicalFilePath: 'script/db/hadm_url_rule_tenant.groovy') {
    def weight_c = 1
    if (helper.isOracle()) {
        weight_c = 2
    }
    if (helper.isOracle()) {
        weight_c = 3
    }
    changeSet(author: "hzero@hand-china.com", id: "2021-06-29-hadm_url_rule_tenant") {
        if (helper.dbType().isSupportSequence()) {
            createSequence(sequenceName: 'hadm_url_rule_tenant_s', startValue: "1")
        }
        createTable(tableName: "hadm_url_rule_tenant", remarks: "URL映射与租户关系表") {
            column(name: "url_rule_id", type: "bigint", remarks: "hadm_url_rule的主键ID") { constraints(nullable: "false") }
            column(name: "source_tenant_id", type: "bigint", remarks: "关联的租户ID") { constraints(nullable: "false") }
            column(name: "created_by", type: "bigint", defaultValue: "-1", remarks: "创建人") { constraints(nullable: "false") }
            column(name: "last_updated_by", type: "bigint", defaultValue: "-1", remarks: "最近更新人") { constraints(nullable: "false") }
            column(name: "creation_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "创建时间") { constraints(nullable: "false") }
            column(name: "last_update_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "最近更新时间") { constraints(nullable: "false") }
            column(name: "object_version_number", type: "bigint", defaultValue: "1", remarks: "行版本号，用来处理锁") { constraints(nullable: "false") }
            column(name: "rule_tenant_id", type: "bigint", autoIncrement: true, remarks: "") { constraints(primaryKey: true) }
        }
    }
}
