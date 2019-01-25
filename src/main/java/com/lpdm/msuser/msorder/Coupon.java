package com.lpdm.msuser.msorder;

public class Coupon {

    private int id;
    private boolean active;
    private double amount;

    public Coupon() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", active=" + active +
                ", amount=" + amount +
                '}';
    }
}
