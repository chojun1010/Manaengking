package com.example.manaengking;

public class ShoppingBasket {
    int _id;
    String tobuy;

    public ShoppingBasket(int _id, String tobuy){
        this._id = _id;
        this.tobuy = tobuy;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTobuy() {
        return tobuy;
    }

    public void setTobuy(String tobuy) {
        this.tobuy = tobuy;
    }
}
