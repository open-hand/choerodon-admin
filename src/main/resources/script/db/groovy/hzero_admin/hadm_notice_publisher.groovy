package script.db

databaseChangeLog(logicalFilePath: 'script/db/hadm_notice_publisher.groovy') {
    def weight_c = 1
    if (helper.isSqlServer()) {
        weight_c = 2
    } else if (helper.isOracle()) {
        weight_c = 3
    }
    changeSet(author: "hzero@hand-china.com", id: "hadm_notice_publisher-2020-11-24-version-1") {
        if (helper.dbType().isSupportSequence()) {
            createSequence(sequenceName: 'hadm_notice_publisher_s', startValue: "1")
        }
        createTable(tableName: "hadm_notice_publisher", remarks: "服务通知") {
            column(name: "notice_publisher_id", type: "bigint", autoIncrement: true, remarks: "通知发布者ID，主键") { constraints(primaryKey: true) }
            column(name: "notice_code", type: "varchar(" + 60 * weight_c + ")", remarks: "话题编码") { constraints(nullable: "false") }
            column(name: "notice_name", type: "varchar(" + 120 * weight_c + ")", remarks: "话题名称") { constraints(nullable: "false") }
            column(name: "service_name", type: "varchar(" + 90 * weight_c + ")", remarks: "发布者服务名称") { constraints(nullable: "false") }
            column(name: "notice_description", type: "varchar(" + 1200 * weight_c + ")", remarks: "通知描述")
            column(name: "object_version_number", type: "bigint", defaultValue: "1", remarks: "行版本号，用来处理锁") { constraints(nullable: "false") }
            column(name: "created_by", type: "bigint", defaultValue: "-1", remarks: "") { constraints(nullable: "false") }
            column(name: "last_updated_by", type: "bigint", defaultValue: "-1", remarks: "") { constraints(nullable: "false") }
            column(name: "creation_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "") { constraints(nullable: "false") }
            column(name: "last_update_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "") { constraints(nullable: "false") }
        }
        addUniqueConstraint(columnNames: "notice_code,service_name", tableName: "hadm_notice_publisher", constraintName: "hadm_notice_publisher_u1")
    }
}
