package com.mycompany.mphcr01;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// main window for the MotorPH Employee App
// features 2, 3, and 4 are all here
// we store records as strings in an ArrayList
// format: EmpNo,LastName,FirstName,DailyRate,DaysWorked,Deductions,GrossPay,NetPay,Position

public class EmployeeGUI extends JFrame {

    // list of all employee records
    static ArrayList<String> records = new ArrayList<String>();

    // the main table
    static JTable table;
    static DefaultTableModel model;

    // form fields
    static JTextField empNoField;
    static JTextField lastField;
    static JTextField firstField;
    static JTextField rateField;
    static JTextField daysField;
    static JTextField searchField;

    // status bar
    static JLabel statusLabel;

    // colors
    static Color maroon = new Color(106, 27, 26);
    static Color maroonDark = new Color(74, 15, 14);
    static Color bgColor = new Color(245, 240, 240);

    // column names for the table
    static String[] columns = {
        "Emp No", "Name", "Daily Rate",
        "Days Worked", "Deductions", "Gross Pay", "Net Pay"
    };

    public EmployeeGUI() {

        setTitle("MotorPH Employee Records");
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // add all the panels
        add(makeBanner(), BorderLayout.NORTH);
        add(makeTablePanel(), BorderLayout.CENTER);
        add(makeRightPanel(), BorderLayout.EAST);
        add(makeStatusBar(), BorderLayout.SOUTH);

        // load data and show it
        records = EmployeeFileHandler.loadEmployees();
        showAllRecords();
        statusLabel.setText("Ready.  " + records.size() + " employee record(s) loaded.");

        setVisible(true);
    }

    // top banner
    static JPanel makeBanner() {
        JPanel banner = new JPanel(new BorderLayout());
        banner.setBackground(maroon);
        banner.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        JLabel title = new JLabel("MotorPH Employee Management System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel subtitle = new JLabel("Manage records, compute daily salaries, and update employee data");
        subtitle.setForeground(new Color(230, 200, 200));
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        textPanel.setBackground(maroon);
        textPanel.add(title);
        textPanel.add(subtitle);

        banner.add(textPanel, BorderLayout.WEST);
        return banner;
    }

    // middle panel with the table
    static JPanel makeTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 6));

        // search bar at the top
        JPanel searchRow = new JPanel(new BorderLayout(8, 0));
        searchRow.setBackground(bgColor);
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 13));
        searchField = new JTextField();
        JButton searchBtn = new JButton("Search");
        JButton showAllBtn = new JButton("Show All");

        JPanel searchBtns = new JPanel(new GridLayout(1, 2, 5, 0));
        searchBtns.add(searchBtn);
        searchBtns.add(showAllBtn);

        searchRow.add(searchLabel, BorderLayout.WEST);
        searchRow.add(searchField, BorderLayout.CENTER);
        searchRow.add(searchBtns, BorderLayout.EAST);
        panel.add(searchRow, BorderLayout.NORTH);

        // the main table
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(24);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setBackground(maroonDark);
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionBackground(new Color(220, 185, 185));

        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);

        // wire up search buttons
        searchBtn.addActionListener(new SearchListener());
        showAllBtn.addActionListener(new ShowAllListener());

        // click a row to fill the form
        table.getSelectionModel().addListSelectionListener(new RowClickListener());

        return panel;
    }

    // right panel with form and buttons
    static JPanel makeRightPanel() {
        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBackground(bgColor);
        side.setBorder(BorderFactory.createEmptyBorder(12, 6, 12, 12));

        // employee info section
        JPanel empPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        empPanel.setBackground(bgColor);
        empPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(maroon), "Employee Information"));

        empNoField = new JTextField();
        lastField = new JTextField();
        firstField = new JTextField();

        empPanel.add(new JLabel("  Employee No:"));
        empPanel.add(empNoField);
        empPanel.add(new JLabel("  Last Name:"));
        empPanel.add(lastField);
        empPanel.add(new JLabel("  First Name:"));
        empPanel.add(firstField);

        empPanel.setMaximumSize(new Dimension(280, 120));
        empPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        side.add(empPanel);
        side.add(Box.createVerticalStrut(8));

        // payroll info section
        JPanel payPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        payPanel.setBackground(bgColor);
        payPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(maroon), "Payroll Information"));

        rateField = new JTextField();
        daysField = new JTextField();

        payPanel.add(new JLabel("  Daily Rate:"));
        payPanel.add(rateField);
        payPanel.add(new JLabel("  Days Worked:"));
        payPanel.add(daysField);

        payPanel.setMaximumSize(new Dimension(280, 90));
        payPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        side.add(payPanel);
        side.add(Box.createVerticalStrut(12));

        // CRUD buttons
        JPanel crudPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        crudPanel.setBackground(bgColor);
        crudPanel.setMaximumSize(new Dimension(280, 80));
        crudPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton clearBtn = new JButton("Clear Form");

        crudPanel.add(addBtn);
        crudPanel.add(updateBtn);
        crudPanel.add(deleteBtn);
        crudPanel.add(clearBtn);
        side.add(crudPanel);
        side.add(Box.createVerticalStrut(12));

        // salary buttons
        JButton computeBtn = new JButton("Compute Salaries (All)");
        JButton payslipBtn = new JButton("View Payslip");
        JButton reportBtn = new JButton("View Salary Report");
        JButton reloadBtn = new JButton("Reload from Source Files");

        for (JButton b : new JButton[]{computeBtn, payslipBtn, reportBtn, reloadBtn}) {
            b.setMaximumSize(new Dimension(280, 34));
            b.setAlignmentX(Component.LEFT_ALIGNMENT);
            side.add(b);
            side.add(Box.createVerticalStrut(6));
        }

        // connect all buttons to their actions
        addBtn.addActionListener(new AddListener());
        updateBtn.addActionListener(new UpdateListener());
        deleteBtn.addActionListener(new DeleteListener());
        clearBtn.addActionListener(new ClearListener());
        computeBtn.addActionListener(new ComputeListener());
        payslipBtn.addActionListener(new PayslipListener());
        reportBtn.addActionListener(new ReportListener());
        reloadBtn.addActionListener(new ReloadListener());

        return side;
    }

    // bottom status bar
    static JPanel makeStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(232, 215, 215));
        bar.setBorder(BorderFactory.createEmptyBorder(5, 14, 5, 14));
        statusLabel = new JLabel("Starting up...");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        bar.add(statusLabel, BorderLayout.WEST);
        return bar;
    }

    // methods to show records in the table

    static void showAllRecords() {
        model.setRowCount(0);
        for (int i = 0; i < records.size(); i++) {
            addRowToTable(records.get(i));
        }
    }

    static void showSearchResults(String text) {
        model.setRowCount(0);
        text = text.toLowerCase();
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).toLowerCase().contains(text)) {
                addRowToTable(records.get(i));
            }
        }
    }

    // add one record as a row, format money as PHP
    static void addRowToTable(String record) {
        String[] f = record.split(",", -1);
        if (f.length < 7) return;
        String[] row = {
            f[0],               // Emp No
            f[2] + " " + f[1], // Name (First Last)
            peso(f[3]),         // Daily Rate
            f[4],               // Days Worked
            peso(f[5]),         // Deductions
            peso(f[6]),         // Gross Pay
            peso(f[7])          // Net Pay
        };
        model.addRow(row);
    }

    static String peso(String value) {
        try {
            double num = Double.parseDouble(value.trim());
            return "PHP " + String.format("%,.2f", num);
        } catch (NumberFormatException e) {
            return value;
        }
    }

    // form helper methods

    static void clearForm() {
        empNoField.setText("");
        lastField.setText("");
        firstField.setText("");
        rateField.setText("");
        daysField.setText("");
        empNoField.setEditable(true);
        table.clearSelection();
    }

    static boolean validateFields() {
        if (empNoField.getText().trim().isEmpty()
                || lastField.getText().trim().isEmpty()
                || firstField.getText().trim().isEmpty()
                || rateField.getText().trim().isEmpty()
                || daysField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required.");
            return false;
        }
        try {
            double rate = Double.parseDouble(rateField.getText().trim());
            double days = Double.parseDouble(daysField.getText().trim());
            if (rate < 0 || days < 0) {
                JOptionPane.showMessageDialog(null, "Rate and Days cannot be negative.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Rate and Days must be valid numbers.");
            return false;
        }
        return true;
    }

    // button action listeners

    // search
    static class SearchListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String text = searchField.getText().trim();
            if (text.isEmpty()) {
                showAllRecords();
            } else {
                showSearchResults(text);
            }
        }
    }

    // show all records
    static class ShowAllListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            searchField.setText("");
            showAllRecords();
            statusLabel.setText("Showing all " + records.size() + " record(s).");
        }
    }

    // when user clicks a row, fill the form
    static class RowClickListener implements javax.swing.event.ListSelectionListener {
        public void valueChanged(javax.swing.event.ListSelectionEvent e) {
            int row = table.getSelectedRow();
            if (row < 0) return;
            String empNo = model.getValueAt(row, 0).toString();
            int pos = EmployeeFileHandler.findIndex(records, empNo);
            if (pos >= 0) {
                String[] f = records.get(pos).split(",", -1);
                empNoField.setText(f[0]);
                lastField.setText(f[1]);
                firstField.setText(f[2]);
                rateField.setText(f[3]);
                daysField.setText(f[4]);
                // lock the emp no so it cant be changed
                empNoField.setEditable(false);
                statusLabel.setText("Selected: " + f[2] + " " + f[1] + " (" + f[0] + ")");
            }
        }
    }

    // add new employee
    static class AddListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            empNoField.setEditable(true);
            if (!validateFields()) return;

            String empNo = empNoField.getText().trim();
            if (EmployeeFileHandler.exists(records, empNo)) {
                JOptionPane.showMessageDialog(null, "Employee No " + empNo + " already exists.");
                return;
            }

            String newRecord = empNo + ","
                    + lastField.getText().trim() + ","
                    + firstField.getText().trim() + ","
                    + rateField.getText().trim() + ","
                    + daysField.getText().trim() + ","
                    + "0.00,0.00,0.00,";  // deductions/gross/net/position = 0 until computed

            records.add(newRecord);
            EmployeeFileHandler.saveAll(records);
            showAllRecords();
            clearForm();
            JOptionPane.showMessageDialog(null, "Employee " + empNo + " added successfully.");
            statusLabel.setText("Added employee " + empNo + ". Total: " + records.size() + " record(s).");
        }
    }

    // update selected employee
    static class UpdateListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String empNo = empNoField.getText().trim();
            if (empNo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select an employee from the table first.");
                return;
            }
            if (!validateFields()) return;

            int pos = EmployeeFileHandler.findIndex(records, empNo);
            if (pos == -1) {
                JOptionPane.showMessageDialog(null, "Employee not found.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null,
                    "Save changes to employee " + empNo + "?",
                    "Confirm Update", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            // keep the old pay values, just update name/rate/days
            String[] old = records.get(pos).split(",", -1);
            String ded = old.length > 5 ? old[5] : "0.00";
            String gross = old.length > 6 ? old[6] : "0.00";
            String net = old.length > 7 ? old[7] : "0.00";
            String position = old.length > 8 ? old[8] : "";

            String updated = empNo + ","
                    + lastField.getText().trim() + ","
                    + firstField.getText().trim() + ","
                    + rateField.getText().trim() + ","
                    + daysField.getText().trim() + ","
                    + ded + "," + gross + "," + net + "," + position;

            records.set(pos, updated);
            EmployeeFileHandler.saveAll(records);
            showAllRecords();
            JOptionPane.showMessageDialog(null,
                    "Employee " + empNo + " updated.\nClick 'Compute Salaries' to refresh pay.");
            statusLabel.setText("Updated employee " + empNo + ".");
        }
    }

    // delete selected employee
    static class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String empNo = empNoField.getText().trim();
            if (empNo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select an employee from the table first.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete employee " + empNo + "?\nThis cannot be undone.",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm != JOptionPane.YES_OPTION) return;

            int pos = EmployeeFileHandler.findIndex(records, empNo);
            if (pos == -1) {
                JOptionPane.showMessageDialog(null, "Employee not found.");
                return;
            }

            records.remove(pos);
            EmployeeFileHandler.saveAll(records);
            showAllRecords();
            clearForm();
            JOptionPane.showMessageDialog(null, "Employee " + empNo + " deleted.");
            statusLabel.setText("Deleted employee " + empNo + ". Total: " + records.size() + " record(s).");
        }
    }

    // clear the form
    static class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            clearForm();
            statusLabel.setText("Form cleared.");
        }
    }

    // compute salary for all employees
    static class ComputeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (records.size() == 0) {
                JOptionPane.showMessageDialog(null, "No records to compute.");
                return;
            }

            for (int i = 0; i < records.size(); i++) {
                String[] f = records.get(i).split(",", -1);
                if (f.length < 5) continue;

                double dailyRate = EmployeeFileHandler.parseNumber(f[3]);
                double daysWorked = EmployeeFileHandler.parseNumber(f[4]);
                double gross = SalaryComputationModule.computeGrossPay(dailyRate, daysWorked);
                double[] ded = SalaryComputationModule.computeDeductions(gross);
                double net = SalaryComputationModule.computeNetPay(gross, ded[4]);

                // update the record with new values
                String position = f.length > 8 ? f[8] : "";
                String updated = f[0] + "," + f[1] + "," + f[2] + ","
                        + String.format("%.2f", dailyRate) + ","
                        + String.format("%.2f", daysWorked) + ","
                        + String.format("%.2f", ded[4]) + ","
                        + String.format("%.2f", gross) + ","
                        + String.format("%.2f", net) + ","
                        + position;
                records.set(i, updated);
            }

            EmployeeFileHandler.saveAll(records);
            showAllRecords();
            JOptionPane.showMessageDialog(null,
                    "Salaries computed for all " + records.size() + " employee(s).");
            statusLabel.setText("Salaries computed and saved.");
        }
    }

    // show the payslip for the selected employee
    static class PayslipListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String empNo = empNoField.getText().trim();
            if (empNo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select an employee from the table first.");
                return;
            }

            int pos = EmployeeFileHandler.findIndex(records, empNo);
            if (pos == -1) {
                JOptionPane.showMessageDialog(null, "Employee not found.");
                return;
            }

            String[] f = records.get(pos).split(",", -1);
            double dailyRate = EmployeeFileHandler.parseNumber(f[3]);
            double daysWorked = EmployeeFileHandler.parseNumber(f[4]);
            double gross = SalaryComputationModule.computeGrossPay(dailyRate, daysWorked);
            double[] ded = SalaryComputationModule.computeDeductions(gross);
            double net = SalaryComputationModule.computeNetPay(gross, ded[4]);

            String slip = "";
            slip += "=================================================\n";
            slip += "              MotorPH  -  PAYSLIP               \n";
            slip += "=================================================\n";
            slip += " Employee No. : " + f[0] + "\n";
            slip += " Name         : " + f[2] + " " + f[1] + "\n";
            slip += " Position     : " + (f.length > 8 ? f[8] : "") + "\n";
            slip += "-------------------------------------------------\n";
            slip += " EARNINGS\n";
            slip += "   Daily Rate         : PHP " + String.format("%,.2f", dailyRate) + "\n";
            slip += "   Days Worked        : " + String.format("%.2f", daysWorked) + "\n";
            slip += "   Gross Pay          : PHP " + String.format("%,.2f", gross) + "\n";
            slip += "-------------------------------------------------\n";
            slip += " DEDUCTIONS\n";
            slip += "   SSS                : PHP " + String.format("%,.2f", ded[0]) + "\n";
            slip += "   PhilHealth         : PHP " + String.format("%,.2f", ded[1]) + "\n";
            slip += "   Pag-IBIG           : PHP " + String.format("%,.2f", ded[2]) + "\n";
            slip += "   Withholding Tax    : PHP " + String.format("%,.2f", ded[3]) + "\n";
            slip += "   Total Deductions   : PHP " + String.format("%,.2f", ded[4]) + "\n";
            slip += "-------------------------------------------------\n";
            slip += " NET PAY             : PHP " + String.format("%,.2f", net) + "\n";
            slip += "=================================================\n";

            JTextArea area = new JTextArea(slip, 24, 50);
            area.setEditable(false);
            area.setFont(new Font("Monospaced", Font.PLAIN, 13));
            JScrollPane scrollPane = new JScrollPane(area);
            JOptionPane.showMessageDialog(null, scrollPane,
                    "Payslip - " + empNo, JOptionPane.INFORMATION_MESSAGE);

            statusLabel.setText("Viewed payslip for " + empNo + ".");
        }
    }

    // show salary report for all employees
    static class ReportListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (records.size() == 0) {
                JOptionPane.showMessageDialog(null, "No records to report.");
                return;
            }

            double totalGross = 0;
            double totalNet = 0;
            String report = "========== MotorPH Salary Summary ==========\n\n";

            for (int i = 0; i < records.size(); i++) {
                String[] f = records.get(i).split(",", -1);
                if (f.length < 8) continue;
                double gross = EmployeeFileHandler.parseNumber(f[6]);
                double net = EmployeeFileHandler.parseNumber(f[7]);
                totalGross += gross;
                totalNet += net;
                report += f[0] + "  " + f[2] + " " + f[1] + "\n";
                report += "   Gross Pay : PHP " + String.format("%,.2f", gross) + "\n";
                report += "   Net Pay   : PHP " + String.format("%,.2f", net) + "\n\n";
            }

            double avgNet = records.size() > 0 ? totalNet / records.size() : 0;
            report += "-------------------------------------------\n";
            report += "Employees       : " + records.size() + "\n";
            report += "Total Gross Pay : PHP " + String.format("%,.2f", totalGross) + "\n";
            report += "Total Net Pay   : PHP " + String.format("%,.2f", totalNet) + "\n";
            report += "Average Net Pay : PHP " + String.format("%,.2f", avgNet) + "\n";

            JTextArea area = new JTextArea(report, 24, 55);
            area.setEditable(false);
            area.setFont(new Font("Monospaced", Font.PLAIN, 13));
            JScrollPane scrollPane = new JScrollPane(area);
            JOptionPane.showMessageDialog(null, scrollPane,
                    "Salary Report", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // reload from the original csv files
    static class ReloadListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Reload from the original source files?\n"
                    + "This rebuilds all records and discards any saved changes.",
                    "Confirm Reload", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            // delete working file so it rebuilds from source
            java.io.File out = new java.io.File(EmployeeFileHandler.OUTPUT_FILE);
            if (out.exists()) out.delete();

            records = EmployeeFileHandler.loadEmployees();
            showAllRecords();
            statusLabel.setText("Rebuilt " + records.size() + " record(s) from source files.");
        }
    }

    // main method to run the app
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EmployeeGUI();
            }
        });
    }
}
