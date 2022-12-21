package com.example.weeklygrind;

public class ResBlock {

    private String Date, Time, UID, PAX;

    public ResBlock() {
    }

    public ResBlock(String date, String time, String UID, String PAX) {
        Date = date;
        Time = time;
        this.UID = UID;
        this.PAX = PAX;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getPAX() {
        return PAX;
    }

    public void setPAX(String PAX) {
        this.PAX = PAX;
    }
}
