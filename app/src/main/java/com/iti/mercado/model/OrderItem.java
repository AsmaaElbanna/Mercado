package com.iti.mercado.model;

public class OrderItem {

    private int count;
    private String itemId;

    public OrderItem() {}

    public OrderItem(int count, String itemId) {
        this.count = count;
        this.itemId = itemId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
