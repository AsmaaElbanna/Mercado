package com.iti.mercado.model;

import java.util.List;

public class KidsClothing extends Item {

    private String size;
    private String quality;
    private String color;
    private List<String> slider_image;

    public List<String> getSlider_image() {
        return slider_image;
    }

    public void setSlider_image(List<String> slider_image) {
        this.slider_image = slider_image;
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
