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
}