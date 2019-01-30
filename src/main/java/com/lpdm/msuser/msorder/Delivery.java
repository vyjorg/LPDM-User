package com.lpdm.msuser.msorder;

import javax.validation.constraints.NotNull;

public class Delivery {

    private int id;

    private String method;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", method='" + method + '\'' +
                '}';
    }
}
