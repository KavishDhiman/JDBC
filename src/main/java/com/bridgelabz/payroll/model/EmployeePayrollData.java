package com.bridgelabz.payroll.model;

import java.time.LocalDate;

public class EmployeePayrollData {

    private int id;
    private String name;
    private double salary;
    private String gender;
    private LocalDate startDate;

    public EmployeePayrollData(int id, String name,
                               double salary,
                               String gender,
                               LocalDate startDate) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.gender = gender;
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + salary +
                " | " + gender + " | " + startDate;
    }
}