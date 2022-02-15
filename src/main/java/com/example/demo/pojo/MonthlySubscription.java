package com.example.demo.pojo;

/**
 * @author OverChat
 * @date
 */
public class MonthlySubscription {
    private String order_date;
    private int number_orders;
    private int amount;

    public MonthlySubscription(String order_date, int number_orders, int amount) {
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

    public int getNumber_orders() {
        return number_orders;
    }

    public void setNumber_orders(int number_orders) {
        this.number_orders = number_orders;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
