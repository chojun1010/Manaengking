package com.example.manaengking;


public class ItemData {
    public String name;
    public String type;
    public String remaining;
    long dDay;

    public ItemData(String name, String type, String remaining, long dDay) {
        this.name = name;
        this.type = type;
        this.remaining = remaining;
        this.dDay = dDay;
    }

    public String getName() {
        return name;
    }
}
