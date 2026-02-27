package com.bridgelabz.payroll;

import com.bridgelabz.payroll.service.PayrollDBService;

import com.bridgelabz.payroll.service.PayrollDBService;

public class PayrollApplication {

    public static void main(String[] args) {

        try {
            PayrollDBService service = new PayrollDBService();

            service.readData()
                    .forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}