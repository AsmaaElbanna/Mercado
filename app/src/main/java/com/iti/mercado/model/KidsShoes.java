package com.iti.mercado.model;

public class KidsShoes extends Item{

    private String brand;
    private String size;
    private String color;
    private String material;
    private String[] slider_image;

    public String[] getSlider_image() {
        return slider_image;
    }

    public void setSlider_image(String[] slider_image) {
        this.slider_image = slider_image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
