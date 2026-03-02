package com.bridgelabz.payroll.service;

import com.bridgelabz.payroll.model.EmployeePayrollData;
import com.bridgelabz.payroll.exception.PayrollDBException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PayrollDBService {

    private static PayrollDBService payrollDBService;
    private Connection connection;
    private PreparedStatement preparedStatement;

    private static final String URL =
            "jdbc:mysql://localhost:3306/payroll_service";
    private static final String USER = "root";
    private static final String PASSWORD = "Kavish@12";

    // 🔹 Private Constructor for Singleton
    private PayrollDBService() throws PayrollDBException {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new PayrollDBException("Unable to establish DB connection");
        }
    }

    // 🔹 Singleton Instance
    public static PayrollDBService getInstance() throws PayrollDBException {
        if (payrollDBService == null) {
            payrollDBService = new PayrollDBService();
        }
        return payrollDBService;
    }

    // =========================================================
    // UC2 – Read All Employee Payroll Data
    // =========================================================

    public List<EmployeePayrollData> readData()
            throws PayrollDBException {

        List<EmployeePayrollData> employeeList = new ArrayList<>();
        String query = "SELECT * FROM employee_payroll";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                String gender = resultSet.getString("gender");
                LocalDate startDate =
                        resultSet.getDate("start").toLocalDate();

                employeeList.add(
                        new EmployeePayrollData(
                                id, name, salary, gender, startDate));
            }

        } catch (SQLException e) {
            throw new PayrollDBException(
                    "Error retrieving employee payroll data");
        }

        return employeeList;
    }

    // =========================================================
    // UC3 – Update Salary using Statement
    // =========================================================

    public void updateSalaryUsingStatement(String name, double salary)
            throws PayrollDBException {

        String query = String.format(
                "UPDATE employee_payroll SET salary = %f WHERE name = '%s'",
                salary, name);

        try (Statement statement = connection.createStatement()) {

            int rowsAffected = statement.executeUpdate(query);

            if (rowsAffected == 0) {
                throw new PayrollDBException("Employee not found!");
            }

        } catch (SQLException e) {
            throw new PayrollDBException(
                    "Error updating employee salary using Statement");
        }
    }

    // =========================================================
    // UC4 – Update Salary using PreparedStatement
    // =========================================================

    public void updateSalaryUsingPreparedStatement(String name, double salary)
            throws PayrollDBException {

        String query =
                "UPDATE employee_payroll SET salary = ? WHERE name = ?";

        try (PreparedStatement ps =
                     connection.prepareStatement(query)) {

            ps.setDouble(1, salary);
            ps.setString(2, name);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new PayrollDBException("Employee not found!");
            }

        } catch (SQLException e) {
            throw new PayrollDBException(
                    "Error updating salary using PreparedStatement");
        }
    }

    // =========================================================
    // UC4 Refactor – Cached PreparedStatement + Retrieve by Name
    // =========================================================

    public List<EmployeePayrollData> getEmployeeByName(String name)
            throws PayrollDBException {

        List<EmployeePayrollData> employeeList = new ArrayList<>();
        String query =
                "SELECT * FROM employee_payroll WHERE name = ?";

        try {
            if (preparedStatement == null) {
                preparedStatement =
                        connection.prepareStatement(query);
            }

            preparedStatement.setString(1, name);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String empName = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                String gender = resultSet.getString("gender");
                LocalDate startDate =
                        resultSet.getDate("start").toLocalDate();

                employeeList.add(
                        new EmployeePayrollData(
                                id, empName,
                                salary, gender, startDate));
            }

        } catch (SQLException e) {
            throw new PayrollDBException(
                    "Error retrieving employee by name");
        }

        return employeeList;
    }
    // =========================================================
// UC5 – Retrieve Employees by Date Range
// =========================================================

    public List<EmployeePayrollData> getEmployeesByDateRange(
            LocalDate startDate, LocalDate endDate)
            throws PayrollDBException {

        List<EmployeePayrollData> employeeList = new ArrayList<>();

        String query =
                "SELECT * FROM employee_payroll WHERE start BETWEEN ? AND ?";

        try (PreparedStatement ps =
                     connection.prepareStatement(query)) {

            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                String gender = resultSet.getString("gender");
                LocalDate start =
                        resultSet.getDate("start").toLocalDate();

                employeeList.add(
                        new EmployeePayrollData(
                                id, name, salary, gender, start));
            }

        } catch (SQLException e) {
            throw new PayrollDBException(
                    "Error retrieving employees by date range");
        }

        return employeeList;
    }
    // =========================================================
// UC6 – Aggregate Salary Statistics by Gender
// =========================================================

    public void getSalaryStatisticsByGender()
            throws PayrollDBException {

        String query =
                "SELECT gender, " +
                        "SUM(salary) AS total_salary, " +
                        "AVG(salary) AS avg_salary, " +
                        "MIN(salary) AS min_salary, " +
                        "MAX(salary) AS max_salary, " +
                        "COUNT(*) AS employee_count " +
                        "FROM employee_payroll GROUP BY gender";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {

                String gender = resultSet.getString("gender");
                double total = resultSet.getDouble("total_salary");
                double avg = resultSet.getDouble("avg_salary");
                double min = resultSet.getDouble("min_salary");
                double max = resultSet.getDouble("max_salary");
                int count = resultSet.getInt("employee_count");

                System.out.println("Gender: " + gender);
                System.out.println("Total Salary: " + total);
                System.out.println("Average Salary: " + avg);
                System.out.println("Minimum Salary: " + min);
                System.out.println("Maximum Salary: " + max);
                System.out.println("Employee Count: " + count);
                System.out.println("----------------------------------");
            }

        } catch (SQLException e) {
            throw new PayrollDBException(
                    "Error performing aggregate salary calculations");
        }
    }
}