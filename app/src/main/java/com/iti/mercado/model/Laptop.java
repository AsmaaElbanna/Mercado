package com.iti.mercado.model;

public class Laptop extends Item {

    private String color;
    private String display_size;
    private String graphics_card_type;
    private String model;
    private String operating_system;
    private String processor;
    private String ram;
    private String storage;


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDisplay_size() {
        return display_size;
    }

    public void setDisplay_size(String display_size) {
        this.display_size = display_size;
    }

    public String getGraphics_card_type() {
        return graphics_card_type;
    }

    public void setGraphics_card_type(String graphics_card_type) {
        this.graphics_card_type = graphics_card_type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOperating_system() {
        return operating_system;
    }

    public void setOperating_system(String operating_system) {
        this.operating_system = operating_system;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

}
