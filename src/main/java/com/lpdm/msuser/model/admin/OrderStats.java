package com.lpdm.msuser.model.admin;

import java.util.LinkedHashMap;
import java.util.Map;

public class OrderStats {

    private Map<Object, Object> dataStats;

    public OrderStats() {
    }

    public Map<Object, Object> getDataStats() {
        if(dataStats == null) dataStats = new LinkedHashMap<>();
        return dataStats;
    }

    public void setDataStats(Map<Object, Object> dataStats) {
        this.dataStats = dataStats;
    }

    @Override
    public String toString() {
        return "OrderStats{" +
                "dataStats=" + dataStats +
                '}';
    }
}
