package com.iti.mercado.model;

public class MakeUp extends Item {

    private String brand;
    private String color;
    private String dimensions;
    private String item_description;
    private String item_rating;
    private String weight;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String getItem_rating() {
        return item_rating;
    }

    public void setItem_rating(String item_rating) {
        this.item_rating = item_rating;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
