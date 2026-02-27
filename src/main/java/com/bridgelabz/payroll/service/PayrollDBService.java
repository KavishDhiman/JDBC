package com.bridgelabz.payroll.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PayrollDBService {

    private static final String URL =
            "jdbc:mysql://localhost:3306/payroll_service";
    private static final String USER = "root";
    private static final String PASSWORD = "Kavish@12";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}