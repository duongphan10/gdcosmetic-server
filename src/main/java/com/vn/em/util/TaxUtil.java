package com.vn.em.util;

public class TaxUtil {
    public static long calculateTax(long taxableIncome) {
        if (taxableIncome <= 5000000)
            return (long) (taxableIncome * 0.05);
        else if (taxableIncome <= 10000000)
            return (long) (taxableIncome * 0.1 - 250000);
        else if (taxableIncome <= 18000000)
            return (long) (taxableIncome * 0.15 - 750000);
        else if (taxableIncome <= 32000000)
            return (long) (taxableIncome * 0.2 - 1650000);
        else if (taxableIncome <= 52000000)
            return (long) (taxableIncome * 0.25 - 3250000);
        else if (taxableIncome <= 80000000)
            return (long) (taxableIncome * 0.3 - 5850000);
        else
            return (long) (taxableIncome * 0.35 - 9850000);
    }
}
