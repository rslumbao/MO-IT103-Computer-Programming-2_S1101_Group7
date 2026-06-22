/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mphcr01;

/**
 *
 * @author c_rslumbao
 */
import javax.swing.*;
import java.awt.*;

public class PayrollGUI extends JFrame {

    JTextField empNoField;
    JTextField empNameField;
    JTextField rateField;
    JTextField daysField;

    JTextField grossField;
    JTextField sssField;
    JTextField philField;
    JTextField pagibigField;
    JTextField taxField;
    JTextField netField;

    JButton computeBtn;

    // Default Constructor
    public PayrollGUI() {

        setTitle("MotorPH Payroll");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(0, 2, 5, 5));

        empNoField = new JTextField();
        empNameField = new JTextField();
        rateField = new JTextField();
        daysField = new JTextField();

        grossField = new JTextField();
        sssField = new JTextField();
        philField = new JTextField();
        pagibigField = new JTextField();
        taxField = new JTextField();
        netField = new JTextField();

        grossField.setEditable(false);
        sssField.setEditable(false);
        philField.setEditable(false);
        pagibigField.setEditable(false);
        taxField.setEditable(false);
        netField.setEditable(false);

        add(new JLabel("Employee No"));
        add(empNoField);

        add(new JLabel("Employee Name"));
        add(empNameField);

        add(new JLabel("Rate Per Day"));
        add(rateField);

        add(new JLabel("Days Worked"));
        add(daysField);

        computeBtn = new JButton("Compute Payroll");

        add(computeBtn);
        add(new JLabel(""));

        add(new JLabel("Gross Pay"));
        add(grossField);

        add(new JLabel("SSS"));
        add(sssField);

        add(new JLabel("PhilHealth"));
        add(philField);

        add(new JLabel("Pag-IBIG"));
        add(pagibigField);

        add(new JLabel("Tax"));
        add(taxField);

        add(new JLabel("Net Pay"));
        add(netField);

        computeBtn.addActionListener(e -> computePayroll());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Constructor with selected employee
    public PayrollGUI(String empNo, String empName) {

        this();

        empNoField.setText(empNo);
        empNameField.setText(empName);

        empNoField.setEditable(false);
        empNameField.setEditable(false);
    }

    // Payroll Computation
    private void computePayroll() {

        try {

            double rate =
                    Double.parseDouble(rateField.getText());

            double days =
                    Double.parseDouble(daysField.getText());

            double gross =
                    SalaryComputationModule.computeGrossPay(rate, days);

            double sss =
                    SalaryComputationModule.computeSSS(gross);

            double phil =
                    SalaryComputationModule.computePhilHealth(gross);

            double pagibig =
                    SalaryComputationModule.computePagIBIG(gross);

            double tax =
                    SalaryComputationModule.computeWithholdingTax(gross);

            double deductions =
                    SalaryComputationModule.computeDeductions(
                            sss, phil, pagibig, tax);

            double net =
                    SalaryComputationModule.computeNetPay(
                            gross, deductions);

            grossField.setText(String.format("%.2f", gross));
            sssField.setText(String.format("%.2f", sss));
            philField.setText(String.format("%.2f", phil));
            pagibigField.setText(String.format("%.2f", pagibig));
            taxField.setText(String.format("%.2f", tax));
            netField.setText(String.format("%.2f", net));

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric values for Rate Per Day and Days Worked."
            );
        }
    }

    // Test Run
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PayrollGUI::new);
    }
}