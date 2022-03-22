package script.db

databaseChangeLog(logicalFilePath: 'script/db/z_fix_data.groovy') {
    changeSet(author: "scp", id: "2020-10-14-fix-route") {
        sql("""
                DELETE
                FROM
                hadm_service_route
                WHERE
                hadm_service_route.service_code LIKE 'hzero-%';
            """)
    }

    changeSet(author: "changping.shi@hand-china.com", id: "hadm_service_version-2022-03-17-change-column") {
        sql("""
            ALTER TABLE `hadm_service_version` MODIFY COLUMN `meta_version` VARCHAR ( 128 ) NOT NULL COMMENT '标记版本' AFTER `version_number`;
        """)
    }
}