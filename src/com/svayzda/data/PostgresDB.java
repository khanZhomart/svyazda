package com.svayzda.data;

import java.sql.*;

public class PostgresDB implements IDB {

    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String connectionUrl = "jdbc:postgresql://localhost:5432/secondDB";
        try {
            System.out.println("1");
            // Here we load the driver’s class file into memory at the runtime
            Class.forName("org.postgresql.Driver");
            System.out.println("2");
            // Establish the connection
            Connection con = DriverManager.getConnection(connectionUrl, "postgres", "kuka2003");
            System.out.println("3");
            return con;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
