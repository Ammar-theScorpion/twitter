package com.equiptal.model;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.exception.FlywayValidateException;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class DBConnection {

    public DBConnection() {
        try {
            flyway.migrate();
        } catch (FlywayValidateException e) {
            System.err.println(e);
            System.exit(0);
        }

        jdbi = Jdbi.create(DB_URL, DB_USER, DB_PASS);
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    private final String DB_URL = "jdbc:postgresql://localhost:5432/twitterDB";
    private final String DB_USER = "postgres";
    private final String DB_PASS = "1234";
    private final Flyway flyway = Flyway.configure().dataSource(DB_URL, DB_USER, DB_PASS)
            .locations("classpath:db/migration").load();

    public static Jdbi jdbi;
}
