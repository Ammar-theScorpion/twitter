package com.equiptal.model;

import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class DBConnection {

    public DBConnection() {
        try {
            flyway.migrate();
            System.out.println("Database migration successful!");
        } catch (Exception e) {
            System.err.println("Error during database migration:");
            e.printStackTrace();
        }
        jdbi = Jdbi.create(DB_URL, DB_USER, DB_PASS);
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    private final String DB_URL = "jdbc:postgresql://localhost:5432/twitterDB";
    private final String DB_USER = "postgres";
    private final String DB_PASS = "1234";
    public static Jdbi jdbi;
    private Flyway flyway = Flyway.configure().dataSource(DB_URL, DB_USER, DB_PASS).load();

}
