package com.dimatechs.ecard.Model;

public class AdminOrders
{
    private String name,phone,date,time,state,totalAmount;

    public AdminOrders() {
    }

    public AdminOrders(String name, String phone, String date, String time, String state, String totalAmount) {
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.state = state;
        this.totalAmount = totalAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
