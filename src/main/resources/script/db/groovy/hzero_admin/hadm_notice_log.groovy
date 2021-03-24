package script.db

databaseChangeLog(logicalFilePath: 'script/db/hadm_notice_log.groovy') {
    def weight_c = 1
    if (helper.isSqlServer()) {
        weight_c = 2
    } else if (helper.isOracle()) {
        weight_c = 3
    }
    changeSet(author: "hzero@hand-china.com", id: "hadm_notice_log-2020-10-27-version-1") {
        if (helper.dbType().isSupportSequence()) {
            createSequence(sequenceName: 'hadm_notice_log_s', startValue: "1")
        }
        createTable(tableName: "hadm_notice_log", remarks: "通知日志") {
            column(name: "notice_log_id", type: "bigint", autoIncrement: true, remarks: "通知日志ID，主键") { constraints(primaryKey: true) }
            column(name: "notice_id", type: "bigint", remarks: "通知事务ID，hadm_notice.notice_id") { constraints(nullable: "false") }
            column(name: "log_content", type: "longtext", remarks: "日志内容") { constraints(nullable: "false") }
            column(name: "notice_listener", type: "longtext", remarks: "日志内容")
            column(name: "object_version_number", type: "bigint", defaultValue: "1", remarks: "行版本号，用来处理锁") { constraints(nullable: "false") }
            column(name: "created_by", type: "bigint", defaultValue: "-1", remarks: "") { constraints(nullable: "false") }
            column(name: "last_updated_by", type: "bigint", defaultValue: "-1", remarks: "") { constraints(nullable: "false") }
            column(name: "creation_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "") { constraints(nullable: "false") }
            column(name: "last_update_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "") { constraints(nullable: "false") }
        }
        addUniqueConstraint(columnNames: "notice_id", tableName: "hadm_notice_log", constraintName: "hadm_notice_log_u1")
    }
}
