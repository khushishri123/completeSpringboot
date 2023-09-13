package com.example.springbootLearning;

import org.springframework.stereotype.Component;

@Component("lap1")
public class Laptop {
    private int lid;
    private String brand;

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Laptop(int lid , String brand) {
        this.lid = lid;
        this.brand = brand;
    }

    public Laptop() {
        System.out.println ("Laptop constructor got called");
    }
}
