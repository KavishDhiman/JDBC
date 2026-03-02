package com.bridgelabz.payroll;
import java.time.LocalDate;

import com.bridgelabz.payroll.exception.PayrollDBException;
import com.bridgelabz.payroll.service.PayrollDBService;

public class PayrollApplication {

    public static void main(String[] args) {

        try {
            PayrollDBService service = PayrollDBService.getInstance();

            service.getEmployeesByDateRange(
                    LocalDate.of(2017, 1, 1),
                    LocalDate.now()
            ).forEach(System.out::println);

        } catch (PayrollDBException e) {
            e.printStackTrace();
        }
    }
}