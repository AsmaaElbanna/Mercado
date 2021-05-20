package com.iti.mercado.model;

public class KidsClothing extends Item{


    private String brand;
    private String size;
    private String quality;
    private String color;
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

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
