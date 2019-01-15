package com.lpdm.msuser.model.admin;

import java.util.Map;

public class OrderStats {

    private int total;
    private Map<String, Integer> annualTotal;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Map<String, Integer> getAnnualTotal() {
        return annualTotal;
    }

    public void setAnnualTotal(Map<String, Integer> annualTotal) {
        this.annualTotal = annualTotal;
    }
}
