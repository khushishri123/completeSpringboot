package com.example.springbootLearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class Alien {
    private int aid;
    private String name;
    @Autowired
    @Qualifier("lap1") //here give the same name "lap1"  as  you have specified in Laptop class declaration
    private Laptop laptop;

    public Alien()
    {
        System.out.println ("Alien object created");
    }
    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }
    public void show(){
        System.out.println (this.aid+" "+this.name+" "+this.laptop.getBrand ());
    }
}
