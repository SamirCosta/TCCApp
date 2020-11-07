package com.samir.TCCApp.classes;

import java.io.Serializable;

public class Product implements Serializable {

    public String name;
    public int image;
    public int qtd = 1;

    public Product(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }
}
