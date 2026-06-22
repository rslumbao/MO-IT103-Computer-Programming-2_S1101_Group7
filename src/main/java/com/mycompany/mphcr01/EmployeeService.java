
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.mphcr01;
/**
 *
 * @author c_rslumbao
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EmployeeService {

    public String searchEmployee(int empNo,
                                 String fullName,
                                 String fromDate,
                                 String toDate) {

        return "Employee Payroll Summary\n\n"
                + "Employee Number: " + empNo
                + "\nEmployee Name: " + fullName
                + "\nPay Coverage From: " + fromDate
                + "\nPay Coverage To: " + toDate;
    }
}