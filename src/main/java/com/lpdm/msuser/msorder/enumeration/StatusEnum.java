package com.lpdm.msuser.msorder.enumeration;

public enum StatusEnum {

    UNFINALIZED(1), PAID(2), PROCESSED(3), IN_DELIVERING(4), DELIVERED(5);

    private int id;

    StatusEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

