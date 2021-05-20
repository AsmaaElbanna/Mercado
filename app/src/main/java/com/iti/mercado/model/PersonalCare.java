package com.iti.mercado.model;

public class PersonalCare extends Item {

    private String color;
    private String item_brand;
    private String item_features;
    private String item_type;
    private String model_number;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getItem_brand() {
        return item_brand;
    }

    public void setItem_brand(String item_brand) {
        this.item_brand = item_brand;
    }

    public String getItem_features() {
        return item_features;
    }

    public void setItem_features(String item_features) {
        this.item_features = item_features;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getModel_number() {
        return model_number;
    }

    public void setModel_number(String model_number) {
        this.model_number = model_number;
    }
}
