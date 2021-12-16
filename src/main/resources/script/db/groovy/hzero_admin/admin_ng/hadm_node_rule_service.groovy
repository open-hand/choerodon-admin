package admin_ng

databaseChangeLog(logicalFilePath: 'script/db/hadm_node_rule_service.groovy') {
    def weight_c = 1
    if (helper.isOracle()) {
        weight_c = 2
    }
    if (helper.isOracle()) {
        weight_c = 3
    }
    changeSet(author: "hzero@hand-china.com", id: "hadm_node_rule_service-2021-06-29-version-1") {
        if (helper.dbType().isSupportSequence()) {
            createSequence(sequenceName: 'hadm_node_rule_service_s', startValue: "1")
        }
        createTable(tableName: "hadm_node_rule_service", remarks: "节点组服务") {
            column(name: "rule_service_id", type: "bigint", autoIncrement: true, remarks: "表ID，主键，供其他表做外键") { constraints(primaryKey: true) }
            column(name: "node_rule_id", type: "bigint", remarks: "节点组规则ID") { constraints(nullable: "false") }
            column(name: "service_code", type: "varchar(" + 90 * weight_c + ")", remarks: "服务名称") { constraints(nullable: "false") }
            column(name: "created_by", type: "bigint", defaultValue: "-1", remarks: "创建人") { constraints(nullable: "false") }
            column(name: "last_updated_by", type: "bigint", defaultValue: "-1", remarks: "最近更新人") { constraints(nullable: "false") }
            column(name: "creation_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "创建时间") { constraints(nullable: "false") }
            column(name: "last_update_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "最近更新时间") { constraints(nullable: "false") }
            column(name: "object_version_number", type: "bigint", defaultValue: "1", remarks: "行版本号，用来处理锁") { constraints(nullable: "false") }
        }
        addUniqueConstraint(columnNames: "node_rule_id,service_code", tableName: "hadm_node_rule_service", constraintName: "hadm_node_rule_service_u1")
    }
}
