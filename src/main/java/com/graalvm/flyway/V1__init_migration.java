package com.graalvm.flyway;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.Statement;

/**
 * Programmatically migration as per docs
 * <a href="https://thorben-janssen.com/java-based-database-migrations-callbacks-flyway/">...</a>
 * <a href="https://github.com/mhalbritter/flyway-native-image/blob/main/src/main/java/db/migration/V3__alter_table_again.java">...</a>
 */
public class V1__init_migration extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        try (Statement statement = context.getConnection().createStatement()) {
            final String migration = """
                    CREATE TABLE IF NOT EXISTS car (
                        car_id BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
                        brand VARCHAR(255),
                        car_key VARCHAR(255),
                        value VARCHAR(255),
                        PRIMARY KEY (car_id)
                    );
                    """;

            for (String table : migration.split(";")) {
                if (table.length() > 1) {
                    statement.execute(table.trim());
                }
            }
            // end of for
        }
        // end of try
    }

}
