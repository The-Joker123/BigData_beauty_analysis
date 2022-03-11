package com.example.demo.pojo;

/**
 * @author OverChat
 * @date
 */
public class WherePeopleBeauty {
    private String location;
    private  double amount;
    private int ranking;

    public WherePeopleBeauty(String location, double amount, int ranking) {
        this.location = location;
        this.amount = amount;
        this.ranking = ranking;
    }

    public WherePeopleBeauty() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}
