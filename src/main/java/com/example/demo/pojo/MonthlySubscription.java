package com.example.demo.pojo;

/**
 * @author OverChat
 * @date
 */
public class MonthlySubscription {
    private String order_date;
    private double number_orders;
    private double amount;

    public MonthlySubscription(String order_date,  double number_orders,  double amount) {
        this.order_date = order_date;
        this.number_orders = number_orders;
        this.amount = amount;
    }

    public MonthlySubscription() {

    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public  double getNumber_orders() {
        return number_orders;
    }

    public void setNumber_orders( double number_orders) {
        this.number_orders = number_orders;
    }

    public  double getAmount() {
        return amount;
    }

    public void setAmount( double amount) {
        this.amount = amount;
    }
}
