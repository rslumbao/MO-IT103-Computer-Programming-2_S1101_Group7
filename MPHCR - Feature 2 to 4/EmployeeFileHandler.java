/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mphcr01;

import java.io.*;
import java.util.*;
/**
 *
 * @author c_rslumbao
 */

public class EmployeeFileHandler {

    private static final String FILE_PATH =
            System.getProperty("user.dir")
            + File.separator
            + "employees.csv";

    public static List<Employee> loadEmployees() {

        List<Employee> list = new ArrayList<>();

        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(FILE_PATH))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] d = line.split(",", -1);

                if (d.length == 12) {

                    list.add(new Employee(
                            d[0],
                            d[1],
                            d[2],
                            d[3],
                            d[4],
                            d[5],
                            d[6],
                            Double.parseDouble(d[7]),
                            Double.parseDouble(d[8]),
                            Double.parseDouble(d[9]),
                            Double.parseDouble(d[10]),
                            Double.parseDouble(d[11])
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
                             new FileWriter(FILE_PATH))) {

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