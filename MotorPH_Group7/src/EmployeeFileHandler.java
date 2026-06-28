package com.mycompany.mphcr01;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

// this class handles reading and saving our employee files
// we read from MotorPH_Employee Data.csv and Attendance.csv
// then save the results to Employee_Output.csv
// note: the employee csv has commas inside some values like "90,000"
// so we cant use a simple split, we made parseCsvLine() to fix that

public class EmployeeFileHandler {

    static final String DIR = System.getProperty("user.dir") + File.separator;
    static final String SOURCE_FILE = DIR + "MotorPH_Employee Data.csv";
    static final String ATTENDANCE_FILE = DIR + "Attendance.csv";
    static final String OUTPUT_FILE = DIR + "Employee_Output.csv";

    static final String HEADER =
        "EmpNo,LastName,FirstName,DailyRate,DaysWorked,Deductions,GrossPay,NetPay,Position";

    // load employees - if we already have a saved file use that
    // if not, read from the original csv files
    public static ArrayList<String> loadEmployees() {
        File output = new File(OUTPUT_FILE);
        if (output.exists()) {
            return readOutputFile();
        } else {
            ArrayList<String> list = buildFromSourceFiles();
            saveAll(list);
            return list;
        }
    }

    // read our saved working file
    public static ArrayList<String> readOutputFile() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(OUTPUT_FILE));
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    list.add(line);
                }
            }
            br.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage());
        }
        return list;
    }

    // build records from the original motorph files
    public static ArrayList<String> buildFromSourceFiles() {
        ArrayList<String> list = new ArrayList<String>();
        HashMap<String, Double> hoursMap = getMonthlyAverageHours();

        try {
            BufferedReader br = new BufferedReader(new FileReader(SOURCE_FILE));
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                // use our special parser so quoted values dont break
                String[] d = parseCsvLine(line);
                if (d.length < 14) continue;

                String empNo = d[0].trim();
                String lastName = d[1].trim();
                String firstName = d[2].trim();
                String position = d[11].trim();

                // daily rate = basic salary divided by 22 working days
                double basicSalary = parseNumber(d[13]);
                double dailyRate = basicSalary / 22.0;

                // convert hours to days by dividing by 8
                double avgHours = 0;
                if (hoursMap.containsKey(empNo)) {
                    avgHours = hoursMap.get(empNo);
                }
                double daysWorked = avgHours / 8.0;

                // put the record together
                String record = empNo + "," + lastName + "," + firstName + ","
                        + String.format("%.2f", dailyRate) + ","
                        + String.format("%.2f", daysWorked) + ","
                        + "0.00,0.00,0.00," + position;
                list.add(record);
            }
            br.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                "Could not find: " + SOURCE_FILE + "\nPlace it in the project root folder.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading employee file: " + e.getMessage());
        }
        return list;
    }

    // read attendance.csv and get the average hours per month per employee
    public static HashMap<String, Double> getMonthlyAverageHours() {
        HashMap<String, Double> totalHours = new HashMap<String, Double>();
        HashMap<String, ArrayList<String>> monthsSeen = new HashMap<String, ArrayList<String>>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(ATTENDANCE_FILE));
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] d = line.split(",", -1);
                if (d.length < 6) continue;

                String empNo = d[0].trim();
                double worked = computeHours(d[4].trim(), d[5].trim());

                if (totalHours.containsKey(empNo)) {
                    totalHours.put(empNo, totalHours.get(empNo) + worked);
                } else {
                    totalHours.put(empNo, worked);
                }

                // keep track of which months the employee worked
                String month = getMonth(d[3].trim());
                if (!monthsSeen.containsKey(empNo)) {
                    monthsSeen.put(empNo, new ArrayList<String>());
                }
                if (!monthsSeen.get(empNo).contains(month)) {
                    monthsSeen.get(empNo).add(month);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                "Could not find Attendance.csv. Days worked will be 0.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading attendance: " + e.getMessage());
        }

        // divide total hours by number of months to get the monthly average
        HashMap<String, Double> average = new HashMap<String, Double>();
        for (String empNo : totalHours.keySet()) {
            int months = 1;
            if (monthsSeen.containsKey(empNo)) {
                months = monthsSeen.get(empNo).size();
            }
            if (months == 0) months = 1;
            average.put(empNo, totalHours.get(empNo) / months);
        }
        return average;
    }

    // save all records to our output file
    public static void saveAll(ArrayList<String> records) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(OUTPUT_FILE));
            pw.println(HEADER);
            for (int i = 0; i < records.size(); i++) {
                pw.println(records.get(i));
            }
            pw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage());
        }
    }

    // check if employee number already exists
    public static boolean exists(ArrayList<String> records, String empNo) {
        for (int i = 0; i < records.size(); i++) {
            String[] f = records.get(i).split(",", -1);
            if (f[0].trim().equals(empNo.trim())) {
                return true;
            }
        }
        return false;
    }

    // find which position in the list has this employee number
    public static int findIndex(ArrayList<String> records, String empNo) {
        for (int i = 0; i < records.size(); i++) {
            String[] f = records.get(i).split(",", -1);
            if (f[0].trim().equals(empNo.trim())) {
                return i;
            }
        }
        return -1;
    }

    // this splits a csv line but handles commas inside quotes
    // for example: 10001,Garcia,"Valero Street, Makati" wont break
    public static String[] parseCsvLine(String line) {
        ArrayList<String> result = new ArrayList<String>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                insideQuotes = !insideQuotes;
            } else if (c == ',' && !insideQuotes) {
                result.add(current.toString().trim());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        result.add(current.toString().trim());
        return result.toArray(new String[0]);
    }

    // convert text to number, remove commas first like in "90,000"
    public static double parseNumber(String text) {
        try {
            return Double.parseDouble(text.replace(",", "").replace("\"", "").trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // get the hours between login and logout time
    public static double computeHours(String login, String logout) {
        try {
            String[] in = login.split(":");
            String[] out = logout.split(":");
            int inMin = Integer.parseInt(in[0]) * 60 + Integer.parseInt(in[1]);
            int outMin = Integer.parseInt(out[0]) * 60 + Integer.parseInt(out[1]);
            int diff = outMin - inMin;
            if (diff < 0) diff = 0;
            return diff / 60.0;
        } catch (Exception e) {
            return 0;
        }
    }

    // get just the month and year from the date
    public static String getMonth(String date) {
        String[] parts = date.split("/");
        if (parts.length == 3) {
            return parts[0] + "/" + parts[2];
        }
        return date;
    }
}
