
package com.mycompany.mphcr01;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeGUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    JTextField empNoField, lastField, firstField,
        sssField, philField, tinField, pagibigField,
        rateField, hoursField;

    JButton addBtn, updateBtn, deleteBtn, reloadBtn, computeBtn;

    public EmployeeGUI() {

        setTitle("MotorPH Employee Records (CRUD)");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= TABLE =================
        model = new DefaultTableModel(new String[]{
        "Emp No",
        "Last",
        "First",
        "Rate",
        "Hours Worked",
        "Deductions",
        "Gross Pay",
        "Net Pay"
        }, 0);

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ================= FORM =================
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        // ================= EMPLOYEE INFO =================
        JPanel employeePanel = new JPanel(new GridLayout(2, 4, 10, 10));
        employeePanel.setBorder(
                BorderFactory.createTitledBorder("Employee Information"));

        empNoField = new JTextField();
        lastField = new JTextField();
        firstField = new JTextField();
        tinField = new JTextField();

        employeePanel.add(new JLabel("Employee No"));
        employeePanel.add(empNoField);

        employeePanel.add(new JLabel("Last Name"));
        employeePanel.add(lastField);

        employeePanel.add(new JLabel("First Name"));
        employeePanel.add(firstField);

        employeePanel.add(new JLabel("TIN"));
        employeePanel.add(tinField);

        // ================= PAYROLL INFO =================
        JPanel payrollPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        payrollPanel.setBorder(
                BorderFactory.createTitledBorder("Payroll Information"));

        sssField = new JTextField();
        philField = new JTextField();
        pagibigField = new JTextField();
        rateField = new JTextField();
        hoursField = new JTextField();

        payrollPanel.add(new JLabel("Rate Per Day"));
        payrollPanel.add(rateField);

        payrollPanel.add(new JLabel("Hours Worked"));
        payrollPanel.add(hoursField);

        payrollPanel.add(new JLabel("SSS"));
        payrollPanel.add(sssField);

        payrollPanel.add(new JLabel("PhilHealth"));
        payrollPanel.add(philField);

        payrollPanel.add(new JLabel("Pag-IBIG"));
        payrollPanel.add(pagibigField);

        topPanel.add(employeePanel);
        topPanel.add(payrollPanel);

        add(topPanel, BorderLayout.NORTH);

        // ================= BUTTONS =================
        JPanel panel = new JPanel();

        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        reloadBtn = new JButton("Reload");
        computeBtn = new JButton("Compute Salaries");

        panel.add(addBtn);
        panel.add(updateBtn);
        panel.add(deleteBtn);
        panel.add(reloadBtn);
        panel.add(computeBtn);

        add(panel, BorderLayout.SOUTH);

        // ================= EVENTS =================
        addBtn.addActionListener(e -> addEmployee());
        updateBtn.addActionListener(e -> updateEmployee());
        deleteBtn.addActionListener(e -> deleteEmployee());
        reloadBtn.addActionListener(e -> loadEmployees());
        computeBtn.addActionListener(e -> computeSalaries());

        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());

        loadEmployees();
        setVisible(true);
    }

    // ================= RECORD SELECTION =================
    private void fillFormFromTable() {

        int row = table.getSelectedRow();

        if (row == -1) {
        return;
        }

        empNoField.setText(model.getValueAt(row, 0).toString());
        lastField.setText(model.getValueAt(row, 1).toString());
        firstField.setText(model.getValueAt(row, 2).toString());

        rateField.setText(model.getValueAt(row, 3).toString());
        hoursField.setText(model.getValueAt(row, 4).toString());
        sssField.setText("");
        philField.setText("");
        tinField.setText("");
        pagibigField.setText("");
    }

    // ================= LOAD =================
    private void loadEmployees() {

        model.setRowCount(0);

        List<Employee> list = EmployeeFileHandler.loadEmployees();

        for (Employee e : list) {
            model.addRow(e.toRow());
        }
    }

    // ================= ADD =================
    private void addEmployee() {

        if (!validateFields()) return;

        List<Employee> list = EmployeeFileHandler.loadEmployees();

        for (Employee e : list) {
            if (e.getEmpNo().equals(empNoField.getText())) {
                JOptionPane.showMessageDialog(this, "Duplicate Employee No!");
                return;
            }
        }

        list.add(new Employee(
        empNoField.getText(),
        lastField.getText(),
        firstField.getText(),
        sssField.getText(),
        philField.getText(),
        tinField.getText(),
        pagibigField.getText(),
        Double.parseDouble(rateField.getText()),
        Double.parseDouble(hoursField.getText()),
        0.0,
        0.0,
        0.0
));

        EmployeeFileHandler.saveAll(list);
        loadEmployees();
        clearFields();

        JOptionPane.showMessageDialog(this, "Employee Added!");
    }

    // ================= UPDATE EMPLOYEE =================
    private void updateEmployee() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a record first!");
            return;
        }

        if (!validateFields()) return;

        List<Employee> list = EmployeeFileHandler.loadEmployees();
        String selectedEmpNo = model.getValueAt(row, 0).toString();

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getEmpNo().equals(selectedEmpNo)) {

                list.set(i, new Employee(
                        empNoField.getText(),
                        lastField.getText(),
                        firstField.getText(),
                        sssField.getText(),
                        philField.getText(),
                        tinField.getText(),
                        pagibigField.getText(),
                        Double.parseDouble(rateField.getText()),
                        Double.parseDouble(hoursField.getText()),
                        list.get(i).getDeductions(),
                        list.get(i).getGrossPay(),
                        list.get(i).getNetPay()
                )); 
            }
        }

        EmployeeFileHandler.saveAll(list);
        loadEmployees();

        JOptionPane.showMessageDialog(this, "Employee Updated!");
    }

    // ================= DELETE EMPLOYEE =================
    private void deleteEmployee() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a record first!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete this employee?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        List<Employee> list = EmployeeFileHandler.loadEmployees();
        String empNo = model.getValueAt(row, 0).toString();

        list.removeIf(e -> e.getEmpNo().equals(empNo));

        EmployeeFileHandler.saveAll(list);

        loadEmployees();
        clearFields();

        JOptionPane.showMessageDialog(this, "Employee Deleted!");
    }

    // ================= VALIDATION =================
    private boolean validateFields() {

    if (empNoField.getText().trim().isEmpty()
            || lastField.getText().trim().isEmpty()
            || firstField.getText().trim().isEmpty()
            || rateField.getText().trim().isEmpty()
            || hoursField.getText().trim().isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "All required fields must be completed."
        );
        return false;
    }

    try {

        Integer.parseInt(empNoField.getText());

        double rate =
                Double.parseDouble(rateField.getText());

        double hours =
                Double.parseDouble(hoursField.getText());

        if (rate < 0 || hours < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Rate and Hours cannot be negative."
            );

            return false;
        }

    } catch (NumberFormatException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Invalid numeric value detected."
        );

        return false;
    }

    return true;
}

    // ================= CLEAR =================
    private void clearFields() {

        empNoField.setText("");
        lastField.setText("");
        firstField.setText("");
        sssField.setText("");
        philField.setText("");
        tinField.setText("");
        pagibigField.setText("");
        rateField.setText("");
        hoursField.setText("");
    }

    
    // ================= SALARY COMPUTATION =================
    private void computeSalaries() {

    List<Employee> employees =
            EmployeeFileHandler.loadEmployees();

    for (Employee emp : employees) {

        double gross =
                SalaryComputationModule.computeGrossPay(
                        emp.getRatePerDay(),
                        emp.getHoursWorked());

        double deductions =
                SalaryComputationModule.computeDeductions(
                        gross);

        double net =
                SalaryComputationModule.computeNetPay(
                        gross,
                        deductions);

        emp.setGrossPay(gross);
        emp.setDeductions(deductions);
        emp.setNetPay(net);
    }

    EmployeeFileHandler.saveAll(employees);

    loadEmployees();

    JOptionPane.showMessageDialog(
            this,
            "Salary computation completed successfully.\n"
            + "Results saved to CSV."
    );
}

    // ================= MAIN =================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeGUI::new);
    }
}