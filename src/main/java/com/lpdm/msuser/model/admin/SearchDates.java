package com.lpdm.msuser.model.admin;

import java.time.LocalDate;

public class SearchDates {

    private LocalDate date1;
    private LocalDate date2;

    public SearchDates(LocalDate date1, LocalDate date2) {
        this.date1 = date1;
        this.date2 = date2;
    }

    public LocalDate getDate1() {
        return date1;
    }

    public void setDate1(LocalDate date1) {
        this.date1 = date1;
    }

    public LocalDate getDate2() {
        return date2;
    }

    public void setDate2(LocalDate date2) {
        this.date2 = date2;
    }

    @Override
    public String toString() {
        return "SearchDates{" +
                "date1=" + date1 +
                ", date2=" + date2 +
                '}';
    }
}
