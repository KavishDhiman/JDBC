package com.bridgelabz.payroll;
import java.sql.SQLException;
import com.bridgelabz.payroll.exception.PayrollDBException;
import com.bridgelabz.payroll.service.PayrollDBService;

public class PayrollApplication {

    public static void main(String[] args) {

        try {
            PayrollDBService service = new PayrollDBService();

            service.updateSalaryUsingPreparedStatement("Terisa", 4000000);

            service.readData()
                    .forEach(System.out::println);

        } catch (PayrollDBException e) {
            e.printStackTrace();
        }
    }
}