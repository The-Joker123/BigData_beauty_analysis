package com.example.demo.pojo;

/**
 * @author OverChat
 * @date
 */
public class Top10_Sum_Brand {
    private double value;
    private String name;

    public Top10_Sum_Brand(int value, String name) {
        this.value = value;
        this.name = name;
    }
    public Top10_Sum_Brand() {

    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
