package script.db

databaseChangeLog(logicalFilePath: 'script/db/hadm_notice.groovy') {
    def weight_c = 1
    if (helper.isSqlServer()) {
        weight_c = 2
    } else if (helper.isOracle()) {
        weight_c = 3
    }
    changeSet(author: "hzero@hand-china.com", id: "hadm_notice-2020-10-27-version-1") {
        if (helper.dbType().isSupportSequence()) {
            createSequence(sequenceName: 'hadm_notice_s', startValue: "1")
        }
        createTable(tableName: "hadm_notice", remarks: "服务间通知事务") {
            column(name: "notice_id", type: "bigint", autoIncrement: true, remarks: "通知事务ID，主键") { constraints(primaryKey: true) }
            column(name: "notice_topic", type: "varchar(" + 120 * weight_c + ")", remarks: "通知话题") { constraints(nullable: "false") }
            column(name: "origin_host", type: "varchar(" + 30 * weight_c + ")", remarks: "通知来源Host") { constraints(nullable: "false") }
            column(name: "origin_port", type: "int", remarks: "通知来源端口") { constraints(nullable: "false") }
            column(name: "origin_service_name", type: "varchar(" + 90 * weight_c + ")", remarks: "通知来源服务名称") { constraints(nullable: "false") }
            column(name: "notice_content", type: "longtext", remarks: "消息内容") { constraints(nullable: "false") }
            column(name: "send_date_time", type: "datetime", remarks: "发送时间") { constraints(nullable: "false") }
            column(name: "receiver_count", type: "int", remarks: "接收者数量") { constraints(nullable: "false") }
            column(name: "send_status", type: "varchar(" + 30 * weight_c + ")", remarks: "发送结果[SENDING(发送中),SUCCESS(成功),FAIL(失败)]") { constraints(nullable: "false") }
            column(name: "retryable_flag", type: "tinyint", remarks: "可重试标记") { constraints(nullable: "false") }
            column(name: "object_version_number", type: "bigint", defaultValue: "1", remarks: "行版本号，用来处理锁") { constraints(nullable: "false") }
            column(name: "created_by", type: "bigint", defaultValue: "-1", remarks: "创建人") { constraints(nullable: "false") }
            column(name: "last_updated_by", type: "bigint", defaultValue: "-1", remarks: "最后更新人") { constraints(nullable: "false") }
            column(name: "creation_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "创建时间") { constraints(nullable: "false") }
            column(name: "last_update_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "最后更新时间") { constraints(nullable: "false") }
        }
        createIndex(tableName: "hadm_notice", indexName: "hadm_notice_n1") {
            column(name: "notice_topic")
        }
        createIndex(tableName: "hadm_notice", indexName: "hadm_notice_n2") {
            column(name: "send_status")
        }
        createIndex(tableName: "hadm_notice", indexName: "hadm_notice_n3") {
            column(name: "send_date_time")
        }
    }
}
