package com.lpdm.msuser.msauthentication;

public class CountryBean {

    private int id;
    private String name;

    public CountryBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CountryBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
