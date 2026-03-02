package com.bridgelabz.payroll.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.bridgelabz.payroll.model.EmployeePayrollData;
import com.bridgelabz.payroll.exception.PayrollDBException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class PayrollDBService {

    private static final String URL =
            "jdbc:mysql://localhost:3306/payroll_service";
    private static final String USER = "root";
    private static final String PASSWORD = "Kavish@12";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public List<EmployeePayrollData> readData()
            throws PayrollDBException {

        List<EmployeePayrollData> employeeList = new ArrayList<>();
        String query = "SELECT * FROM employee_payroll";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                String gender = resultSet.getString("gender");
                LocalDate startDate =
                        resultSet.getDate("start").toLocalDate();

                employeeList.add(
                        new EmployeePayrollData(id, name,
                                salary, gender, startDate));
            }

        } catch (SQLException e) {
            throw new PayrollDBException(
                    "Error retrieving employee payroll data");
        }

        return employeeList;
    }
    public void updateSalary(String name, double salary)
            throws PayrollDBException {

        String query = String.format(
                "UPDATE employee_payroll SET salary = %f WHERE name = '%s'",
                salary, name);

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            int rowsAffected = statement.executeUpdate(query);

            if (rowsAffected == 0) {
                throw new PayrollDBException("Employee not found!");
            }

            System.out.println("Salary updated successfully in DB.");

        } catch (SQLException e) {
            throw new PayrollDBException(
                    "Error updating employee salary");
        }
    }
}