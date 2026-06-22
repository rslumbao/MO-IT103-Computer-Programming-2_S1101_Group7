/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mphcr01;

/**
 *
 * @author c_rslumbao
 */

public class SalaryComputationModule {

    public static double computeGrossPay(
            double ratePerDay,
            double hoursWorked) {

        return ratePerDay * hoursWorked;
    }

    public static double computeSSS(double grossPay) {
        return grossPay * 0.045;
    }

    public static double computePhilHealth(double grossPay) {
        return grossPay * 0.025;
    }

    public static double computePagIBIG(double grossPay) {
        return grossPay * 0.02;
    }

    public static double computeWithholdingTax(double grossPay) {
        return grossPay * 0.10;
    }

    // Existing version
    public static double computeDeductions(double grossPay) {

        return computeSSS(grossPay)
                + computePhilHealth(grossPay)
                + computePagIBIG(grossPay)
                + computeWithholdingTax(grossPay);
    }

    // Add this overloaded version
    public static double computeDeductions(
            double sss,
            double philHealth,
            double pagibig,
            double tax) {

        return sss + philHealth + pagibig + tax;
    }

    public static double computeNetPay(
            double grossPay,
            double deductions) {

        return grossPay - deductions;
    }
}