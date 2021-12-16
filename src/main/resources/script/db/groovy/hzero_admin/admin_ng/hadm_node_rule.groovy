package script.db

databaseChangeLog(logicalFilePath: 'script/db/hadm_node_rule.groovy') {
    changeSet(author: "qingsheng.chen@hand-china.com", id: "2019-01-02-hadm_node_rule") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hadm_node_rule_s', startValue:"1")
        }
        createTable(tableName: "hadm_node_rule", remarks: "节点组规则配置") {
            column(name: "node_rule_id", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "rule_code", type: "varchar(" + 120 * weight + ")",  remarks: "规则编码")  {constraints(nullable:"false")}
            column(name: "rule_name", type: "varchar(" + 240 * weight + ")",  remarks: "规则名称")  {constraints(nullable:"false")}
            column(name: "priority", type: "int",  remarks: "优先级")  {constraints(nullable:"false")}
            column(name: "enabled_flag", type: "tinyint",   defaultValue:"1",   remarks: "启用禁用")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

    }

    changeSet(author: "hzero", id: "2021-06-29-hadm_node_rule") {
        addColumn(tableName: 'hadm_node_rule') {
            column(name: "overall_tenant_flag", type: "tinyint", defaultValue: "0", remarks: "是否应用到所有租户")  {constraints(nullable:"false")}
        }
        addColumn(tableName: 'hadm_node_rule') {
            column(name: "overall_service_flag", type: "tinyint", defaultValue: "0", remarks: "是否应用到所有服务")  {constraints(nullable:"false")}
        }
    }
    changeSet(author: "hzero@hand-china.com", id: "hadm_node_rule-2021-07-08-version-3") {
        createIndex (tableName: "hadm_node_rule", indexName: "hadm_node_rule_n1") {
            column (name: "rule_code")
        }
    }
}