package com.iti.mercado.model;

public class LaptopBag extends Item {

    private String color;
    private String compatible_with;
    private String model;
    private String warranty_years;
    private String water_resistant;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCompatible_with() {
        return compatible_with;
    }

    public void setCompatible_with(String compatible_with) {
        this.compatible_with = compatible_with;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getWarranty_years() {
        return warranty_years;
    }

    public void setWarranty_years(String warranty_years) {
        this.warranty_years = warranty_years;
    }

    public String getWater_resistant() {
        return water_resistant;
    }

    public void setWater_resistant(String water_resistant) {
        this.water_resistant = water_resistant;
    }
}
