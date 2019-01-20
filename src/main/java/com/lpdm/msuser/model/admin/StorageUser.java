package com.lpdm.msuser.model.admin;

public class StorageUser {

    private int id;
    private boolean restricted;

    public StorageUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    @Override
    public String toString() {
        return "StorageUser{" +
                "id=" + id +
                ", restricted=" + restricted +
                '}';
    }
}
