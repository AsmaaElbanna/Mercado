package com.iti.mercado.model;

public class SkinCare extends Item{

    private String brand;
    private String item_description;
    private String item_rating;
    private String recommended_use;
    private String size;
    private String texture;
    private String type;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public String getRecommended_use() {
        return recommended_use;
    }

    public void setRecommended_use(String recommended_use) {
        this.recommended_use = recommended_use;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
