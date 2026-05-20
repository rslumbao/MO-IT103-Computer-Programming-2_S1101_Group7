
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

public class EmployeeGUI extends JFrame {

    // =========================
    // INPUT FIELDS
    // =========================
    private JTextField txtEmpNo = new JTextField();
    private JTextField txtFirstName = new JTextField();
    private JTextField txtLastName = new JTextField();
    private JTextField txtFromDate = new JTextField();
    private JTextField txtToDate = new JTextField();

    private JButton btnSearch = new JButton("Search");

    // SERVICE LAYER
    private EmployeeService service = new EmployeeService();

    // =========================
    // CONSTRUCTOR (UI DESIGN)
    // =========================
    public EmployeeGUI() {

        setTitle("Employee Payroll System");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(6, 2, 10, 10));

        ((JPanel) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        // UI COMPONENTS
        add(new JLabel("Employee Number:"));
        add(txtEmpNo);

        add(new JLabel("First Name:"));
        add(txtFirstName);

        add(new JLabel("Last Name:"));
        add(txtLastName);

        add(new JLabel("Pay Coverage From (MM/DD/YYYY):"));
        add(txtFromDate);

        add(new JLabel("Pay Coverage To (MM/DD/YYYY):"));
        add(txtToDate);

        add(new JLabel());
        add(btnSearch);

        btnSearch.addActionListener(e -> search());
    }

    // =========================
    // UX LOGIC (VALIDATION + FLOW)
    // =========================
    private void search() {

        try {

            // INPUTS
            int empNo = Integer.parseInt(txtEmpNo.getText().trim());
            String firstName = txtFirstName.getText().trim();
            String lastName = txtLastName.getText().trim();
            String fromDate = txtFromDate.getText().trim();
            String toDate = txtToDate.getText().trim();

            // =========================
            // VALIDATION PATTERNS
            // =========================
            String namePattern = "^[a-zA-Z\\s]+$";
            String datePattern = "\\d{2}/\\d{2}/\\d{4}";

            // NAME VALIDATION
            if (!firstName.matches(namePattern) || !lastName.matches(namePattern)) {

                JOptionPane.showMessageDialog(this,
                        "First Name and Last Name must contain letters only.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // EMPTY FIELD VALIDATION
            if (firstName.isEmpty() || lastName.isEmpty()
                    || fromDate.isEmpty() || toDate.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Please fill in all required fields.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // DATE FORMAT VALIDATION
            if (!fromDate.matches(datePattern) || !toDate.matches(datePattern)) {

                JOptionPane.showMessageDialog(this,
                        "Dates must follow MM/DD/YYYY format.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // FULL NAME BUILD
            String fullName = firstName + " " + lastName;

            // CALL SERVICE LAYER
            String result = service.searchEmployee(empNo, fullName, fromDate, toDate);

            // DISPLAY RESULT
            JOptionPane.showMessageDialog(this, result);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this,
                    "Employee Number must be numeric.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new EmployeeGUI().setVisible(true);
        });
    }
}
