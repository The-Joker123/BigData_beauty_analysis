package com.example.demo.pojo;

/**
 * @author OverChat
 * @date
 */
public class RegionalSales {
    private String name;
    private  double value;

    public RegionalSales() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public RegionalSales(String name, double value) {
        this.name = name;
        this.value = value;
    }
}
