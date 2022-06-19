package com.example.manaengking;

import java.util.Date;

public class ItemData {
    public String name;
    public String type;
    public String remaining;
    long dDay;

    public ItemData(String name, String type, String remaining) {
        this.name = name;
        this.type = type;
        this.remaining = remaining;
    }
}
