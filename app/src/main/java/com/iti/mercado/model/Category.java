package com.iti.mercado.model;

public class Category {

    private String category_name;
    private String category_icon;
    private SubCategory[] subCategories;

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_icon() {
        return category_icon;
    }

    public void setCategory_icon(String category_icon) {
        this.category_icon = category_icon;
    }

    public SubCategory[] getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(SubCategory[] subCategories) {
        this.subCategories = subCategories;
    }
}
