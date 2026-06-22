/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mphcr01;

import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class EmployeeFileHandler {

    private static final String SOURCE_FILE =
            System.getProperty("user.dir")
                    + File.separator
                    + "MotorPH_Employee Data.csv";

    private static final String OUTPUT_FILE =
            System.getProperty("user.dir")
                    + File.separator
                    + "Employee_Output.csv";

    private static void initializeOutputFile() {

        try {

            File output = new File(OUTPUT_FILE);

            if (!output.exists()) {

                Files.copy(
                        Paths.get(SOURCE_FILE),
                        Paths.get(OUTPUT_FILE),
                        StandardCopyOption.REPLACE_EXISTING
                );

                System.out.println("Output file created.");
            }

        } catch (Exception e) {

            System.out.println("Copy Error: " + e.getMessage());
        }
    }


    public static List<Employee> loadEmployees() {
        List<Employee> list = new ArrayList<>();
        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(OUTPUT_FILE))) {

            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {

                String[] d = line.split(",", -1);
                if (d.length >= 12) {

                    list.add(new Employee(
                            d[0], //Employee #
                            d[1], // Last Name
                            d[2], // First Name
                            d[3], // SSS
                            d[4], // Philhealth
                            d[5], // TIN
                            d[6], // Pag-IBIG
                            Double.parseDouble(d[7]), // Rate Per Day
                            Double.parseDouble(d[8]), // Hours Worked
                            Double.parseDouble(d[9]), // Deductions
                            Double.parseDouble(d[10]), // Gross Pay
                            Double.parseDouble(d[11])  // Net Pay
                    ));
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Load Error: "
                            + e.getMessage());
        }

        return list;
    }



    public static void saveAll(
            List<Employee> employees) {

        try (PrintWriter pw =
                     new PrintWriter(
                             new FileWriter(OUTPUT_FILE))) {
            pw.println("Employee #,Last Name,First Name,SSS,PhilHealth,TIN,Pagibig,Rate Per Day,Hours Worked,Deductions,Gross Pay,Net Pay");
            for (Employee e : employees) {

                pw.println(String.join(",",
                        e.getEmpNo(),
                        e.getLastName(),
                        e.getFirstName(),
                        e.getSss(),
                        e.getPhilHealth(),
                        e.getTin(),
                        e.getPagibig(),
                        String.valueOf(e.getRatePerDay()),
                        String.valueOf(e.getHoursWorked()),
                        String.valueOf(e.getDeductions()),
                        String.valueOf(e.getGrossPay()),
                        String.valueOf(e.getNetPay())
                ));
            }

        } catch (Exception e) {

            System.out.println(
                    "Save Error: "
                            + e.getMessage());
        }
    }

    public static boolean exists(String empNo) {

        for (Employee e : loadEmployees()) {

            if (e.getEmpNo().equals(empNo)) {
                return true;
            }
        }

        return false;
    }
}