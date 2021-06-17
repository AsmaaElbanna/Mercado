package com.iti.mercado.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart extends ItemPath implements Serializable {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static List<OrderItem> getOrderItems(List<Cart> carts) {

        List<OrderItem> items = new ArrayList<>();
        for (Cart cart: carts) {
            items.add(new OrderItem(cart.getCount(), cart.getItemId()));
        }
        return  items;
    }
}