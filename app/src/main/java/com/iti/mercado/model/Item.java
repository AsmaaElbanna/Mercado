package com.iti.mercado.model;

import java.io.Serializable;
import java.util.List;

public class Item implements Serializable {

    private String item_id;
    private String item_image;
    private String item_price;
    private String item_title;
    private List<String> slider_images;
    private String oldPrice;
    private String brand;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<String> getSlider_images() {
        return slider_images;
    }

    public void setSlider_images(List<String> slider_images) {
        this.slider_images = slider_images;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }
}
