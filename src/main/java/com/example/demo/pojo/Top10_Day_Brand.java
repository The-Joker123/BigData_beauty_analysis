package com.example.demo.pojo;


public class Top10_Day_Brand {
    private String year;
    private String Brand;
    private int sale_count;
    private int sale_amount;
    private int rank;

    public Top10_Day_Brand(String year, String brand, int sale_count, int sale_amount, int rank) {

        this.year = year;
        Brand = brand;
        this.sale_count = sale_count;
        this.sale_amount = sale_amount;
        this.rank = rank;
    }
    public Top10_Day_Brand(String year) {

        this.year = year;

    }

    public Top10_Day_Brand() {

    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public int getSale_count() {
        return sale_count;
    }

    public void setSale_count(int sale_count) {
        this.sale_count = sale_count;
    }

    public int getSale_amount() {
        return sale_amount;
    }

    public void setSale_amount(int sale_amount) {
        this.sale_amount = sale_amount;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Top10_Day_Brand{" +
                "year='" + year + '\'' +
                ", Brand='" + Brand + '\'' +
                ", sale_count=" + sale_count +
                ", sale_amount=" + sale_amount +
                ", rank='" + rank + '\'' +
                '}';
    }
}
