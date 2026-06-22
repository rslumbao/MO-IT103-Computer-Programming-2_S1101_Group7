/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mphcr01;
/**
 *
 * @author c_rslumbao
 */

public class Employee {

    private String empNo;
    private String lastName;
    private String firstName;
    private String sss;
    private String philHealth;
    private String tin;
    private String pagibig;
    private double ratePerDay;
    private double hoursWorked;
    private double deductions;
    private double grossPay;
    private double netPay;

    public Employee(
            String empNo,
            String lastName,
            String firstName,
            String sss,
            String philHealth,
            String tin,
            String pagibig,
            double ratePerDay,
            double hoursWorked,
            double deductions,
            double grossPay,
            double netPay) {

        this.empNo = empNo;
        this.lastName = lastName;
        this.firstName = firstName;
        this.sss = sss;
        this.philHealth = philHealth;
        this.tin = tin;
        this.pagibig = pagibig;

        this.ratePerDay = ratePerDay;
        this.hoursWorked = hoursWorked;
        this.deductions = deductions;
        this.grossPay = grossPay;
        this.netPay = netPay;
    }

    // Getters

    public String getEmpNo() {
        return empNo;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSss() {
        return sss;
    }

    public String getPhilHealth() {
        return philHealth;
    }

    public String getTin() {
        return tin;
    }

    public String getPagibig() {
        return pagibig;
    }

    public double getRatePerDay() {
        return ratePerDay;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getDeductions() {
        return deductions;
    }

    public double getGrossPay() {
        return grossPay;
    }

    public double getNetPay() {
        return netPay;
    }

    // Setters for payroll computation

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public void setGrossPay(double grossPay) {
        this.grossPay = grossPay;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }

    // JTable row

    public String[] toRow() {
    return new String[]{
        empNo,
        lastName,
        firstName,
        String.valueOf(ratePerDay),
        String.valueOf(hoursWorked),
        String.format("%.2f", deductions),
        String.format("%.2f", grossPay),
        String.format("%.2f", netPay)
    };
}
}    