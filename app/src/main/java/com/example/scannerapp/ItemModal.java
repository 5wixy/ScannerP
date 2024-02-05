package com.example.scannerapp;



/*
    Item modal class for storing an item and its params in one object
*/


public class ItemModal {
    public String code;
    public String name;
    public String price;
    public String date;
    public ItemModal(){

    }
    public ItemModal(String code, String name, String price,String date) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) { this.code = code; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public String getDate(){
        return this.date;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
