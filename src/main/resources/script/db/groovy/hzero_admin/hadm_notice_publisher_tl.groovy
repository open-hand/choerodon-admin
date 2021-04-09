package script.db

databaseChangeLog(logicalFilePath: 'script/db/hadm_notice_publisher_tl.groovy') {
    def weight_c = 1
    if (helper.isSqlServer()) {
        weight_c = 2
    } else if (helper.isOracle()) {
        weight_c = 3
    }
    changeSet(author: "hzero@hand-china.com", id: "hadm_notice_publisher_tl-2020-11-27-version-1") {
        if (helper.dbType().isSupportSequence()) {
            createSequence(sequenceName: 'hadm_notice_publisher_tl_s', startValue: "1")
        }
        createTable(tableName: "hadm_notice_publisher_tl", remarks: "服务通知多语言") {
            column(name: "notice_publisher_id", type: "bigint", remarks: "服务通知ID") { constraints(nullable: "false") }
            column(name: "lang", type: "varchar(" + 16 * weight_c + ")", remarks: "语言") { constraints(nullable: "false") }
            column(name: "notice_name", type: "varchar(" + 120 * weight_c + ")", remarks: "发布者服务名称") { constraints(nullable: "false") }
            column(name: "notice_description", type: "varchar(" + 1200 * weight_c + ")", remarks: "通知描述")
        }
        addUniqueConstraint(columnNames: "notice_publisher_id,lang", tableName: "hadm_notice_publisher_tl", constraintName: "hadm_notice_publisher_tl_u1")
    }
}
