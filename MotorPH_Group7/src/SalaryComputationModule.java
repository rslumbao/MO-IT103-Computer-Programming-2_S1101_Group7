package com.mycompany.mphcr01;

// this class handles all the salary computations
// gross pay = daily rate x days worked
// deductions = SSS + PhilHealth + PagIBIG + withholding tax

public class SalaryComputationModule {

    // multiply rate by days to get gross
    public static double computeGrossPay(double dailyRate, double daysWorked) {
        return dailyRate * daysWorked;
    }

    // philhealth formula from the reference
    public static double computePhilHealth(double monthlyGross) {
        double philHealth = (monthlyGross * 0.03) / 2;
        if (philHealth < 150) philHealth = 150;
        if (philHealth > 900) philHealth = 900;
        return philHealth;
    }

    // pagibig formula from the reference
    public static double computePagIBIG(double monthlyGross) {
        double pagIbig = monthlyGross * 0.02;
        if (pagIbig > 100) pagIbig = 100;
        return pagIbig;
    }

    // SSS uses a bracket table
    public static double computeSSS(double monthlyGross) {
        double sss = 0;
        if (monthlyGross < 3250) sss = 135;
        else if (monthlyGross < 3750) sss = 157.5;
        else if (monthlyGross < 4250) sss = 180;
        else if (monthlyGross < 4750) sss = 202.5;
        else if (monthlyGross < 5250) sss = 225;
        else if (monthlyGross < 5750) sss = 247.5;
        else if (monthlyGross < 6250) sss = 270;
        else if (monthlyGross < 6750) sss = 292.5;
        else if (monthlyGross < 7250) sss = 315;
        else if (monthlyGross < 7750) sss = 337.5;
        else if (monthlyGross < 8250) sss = 360;
        else if (monthlyGross < 8750) sss = 382.5;
        else if (monthlyGross < 9250) sss = 405;
        else if (monthlyGross < 9750) sss = 427.5;
        else if (monthlyGross < 10250) sss = 450;
        else if (monthlyGross < 10750) sss = 472.5;
        else if (monthlyGross < 11250) sss = 495;
        else if (monthlyGross < 11750) sss = 517.5;
        else if (monthlyGross < 12250) sss = 540;
        else if (monthlyGross < 12750) sss = 562.5;
        else if (monthlyGross < 13250) sss = 585;
        else if (monthlyGross < 13750) sss = 607.5;
        else if (monthlyGross < 14250) sss = 630;
        else if (monthlyGross < 14750) sss = 652.5;
        else if (monthlyGross < 15250) sss = 675;
        else if (monthlyGross < 15750) sss = 697.5;
        else if (monthlyGross < 16250) sss = 720;
        else if (monthlyGross < 16750) sss = 742.5;
        else if (monthlyGross < 17250) sss = 765;
        else if (monthlyGross < 17750) sss = 787.5;
        else if (monthlyGross < 18250) sss = 810;
        else if (monthlyGross < 18750) sss = 832.5;
        else if (monthlyGross < 19250) sss = 855;
        else if (monthlyGross < 19750) sss = 877.5;
        else if (monthlyGross < 20250) sss = 900;
        else if (monthlyGross < 20750) sss = 922.5;
        else if (monthlyGross < 21250) sss = 945;
        else if (monthlyGross < 21750) sss = 967.5;
        else if (monthlyGross < 22250) sss = 990;
        else if (monthlyGross < 22750) sss = 1012.5;
        else if (monthlyGross < 23250) sss = 1035;
        else if (monthlyGross < 23750) sss = 1057.5;
        else if (monthlyGross < 24250) sss = 1080;
        else if (monthlyGross < 24750) sss = 1102.5;
        else sss = 1125;
        return sss;
    }

    // withholding tax brackets
    public static double computeWithholdingTax(double taxableIncome) {
        double withholdingTax = 0;
        if (taxableIncome <= 20832) {
            withholdingTax = 0;
        } else if (taxableIncome <= 33332) {
            withholdingTax = (taxableIncome - 20833) * 0.20;
        } else if (taxableIncome <= 66666) {
            withholdingTax = 2500 + (taxableIncome - 33333) * 0.25;
        } else if (taxableIncome <= 166666) {
            withholdingTax = 10833 + (taxableIncome - 66667) * 0.30;
        } else if (taxableIncome <= 666666) {
            withholdingTax = 40833.33 + (taxableIncome - 166667) * 0.32;
        } else {
            withholdingTax = 200833.33 + (taxableIncome - 666667) * 0.35;
        }
        return withholdingTax;
    }

    // compute all deductions and return as array
    // [0]=SSS [1]=PhilHealth [2]=PagIBIG [3]=Tax [4]=Total
    public static double[] computeDeductions(double monthlyGross) {
        double sss = computeSSS(monthlyGross);
        double philHealth = computePhilHealth(monthlyGross);
        double pagIbig = computePagIBIG(monthlyGross);
        double taxableIncome = monthlyGross - sss - philHealth - pagIbig;
        double withholdingTax = computeWithholdingTax(taxableIncome);
        double totalDeductions = sss + philHealth + pagIbig + withholdingTax;
        return new double[]{sss, philHealth, pagIbig, withholdingTax, totalDeductions};
    }

    // net = gross minus total deductions
    public static double computeNetPay(double grossPay, double totalDeductions) {
        double net = grossPay - totalDeductions;
        if (net < 0) net = 0;
        return net;
    }
}
