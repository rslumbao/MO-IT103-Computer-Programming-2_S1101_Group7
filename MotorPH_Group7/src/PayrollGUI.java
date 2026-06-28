package com.mycompany.mphcr01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// PayrollGUI - a single-employee payroll calculator
// Can be opened standalone or pre-filled from EmployeeGUI
// Uses daily rate: gross = dailyRate * daysWorked
// Deductions use the MotorPH reference formulas

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
    JTextField totalDedField;
    JTextField netField;

    static Color maroon = new Color(106, 27, 26);

    // Default constructor
    public PayrollGUI() {

        setTitle("MotorPH Payroll Calculator");
        setSize(480, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // banner
        JPanel banner = new JPanel();
        banner.setBackground(maroon);
        JLabel title = new JLabel("MotorPH Payroll Calculator");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        banner.add(title);
        add(banner, BorderLayout.NORTH);

        // form
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        empNoField = new JTextField();
        empNameField = new JTextField();
        rateField = new JTextField();
        daysField = new JTextField();

        grossField = new JTextField(); grossField.setEditable(false);
        sssField = new JTextField(); sssField.setEditable(false);
        philField = new JTextField(); philField.setEditable(false);
        pagibigField = new JTextField(); pagibigField.setEditable(false);
        taxField = new JTextField(); taxField.setEditable(false);
        totalDedField = new JTextField(); totalDedField.setEditable(false);
        netField = new JTextField(); netField.setEditable(false);

        form.add(new JLabel("Employee No:"));
        form.add(empNoField);
        form.add(new JLabel("Employee Name:"));
        form.add(empNameField);
        form.add(new JLabel("Daily Rate:"));
        form.add(rateField);
        form.add(new JLabel("Days Worked:"));
        form.add(daysField);

        form.add(new JLabel("--- Results ---"));
        form.add(new JLabel(""));

        form.add(new JLabel("Gross Pay:"));
        form.add(grossField);
        form.add(new JLabel("SSS:"));
        form.add(sssField);
        form.add(new JLabel("PhilHealth:"));
        form.add(philField);
        form.add(new JLabel("Pag-IBIG:"));
        form.add(pagibigField);
        form.add(new JLabel("Withholding Tax:"));
        form.add(taxField);
        form.add(new JLabel("Total Deductions:"));
        form.add(totalDedField);
        form.add(new JLabel("NET PAY:"));
        form.add(netField);

        add(form, BorderLayout.CENTER);

        // button
        JButton computeBtn = new JButton("Compute Payroll");
        JPanel btnPanel = new JPanel();
        btnPanel.add(computeBtn);
        add(btnPanel, BorderLayout.SOUTH);

        computeBtn.addActionListener(new ComputeAction());

        setVisible(true);
    }

    // Constructor with pre-filled employee info
    public PayrollGUI(String empNo, String empName, String dailyRate, String daysWorked) {
        this();
        empNoField.setText(empNo);
        empNameField.setText(empName);
        rateField.setText(dailyRate);
        daysField.setText(daysWorked);
        empNoField.setEditable(false);
        empNameField.setEditable(false);
    }

    // Compute button action
    class ComputeAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                double rate = Double.parseDouble(rateField.getText().trim());
                double days = Double.parseDouble(daysField.getText().trim());

                if (rate < 0 || days < 0) {
                    JOptionPane.showMessageDialog(null, "Rate and Days cannot be negative.");
                    return;
                }

                double gross = SalaryComputationModule.computeGrossPay(rate, days);
                double[] ded = SalaryComputationModule.computeDeductions(gross);
                double net = SalaryComputationModule.computeNetPay(gross, ded[4]);

                grossField.setText(String.format("%,.2f", gross));
                sssField.setText(String.format("%,.2f", ded[0]));
                philField.setText(String.format("%,.2f", ded[1]));
                pagibigField.setText(String.format("%,.2f", ded[2]));
                taxField.setText(String.format("%,.2f", ded[3]));
                totalDedField.setText(String.format("%,.2f", ded[4]));
                netField.setText(String.format("%,.2f", net));

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Please enter valid numbers for Daily Rate and Days Worked.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PayrollGUI();
            }
        });
    }
}
