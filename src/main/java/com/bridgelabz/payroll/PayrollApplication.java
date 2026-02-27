package com.bridgelabz.payroll;

import com.bridgelabz.payroll.service.PayrollDBService;

import java.sql.Connection;

public class PayrollApplication {

    public static void main(String[] args) {

        try (Connection connection =
                     PayrollDBService.getConnection()) {

            System.out.println("Connection Established Successfully!");

        } catch (Exception e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
    }
}