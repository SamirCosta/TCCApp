package com.samir.TCCApp.classes;

import java.io.Serializable;

public class Product implements Serializable {

//            NomeProd;
    private String name;
    private int image;
    private int qtd = 1;
    private int IdProd;
    private String DescProd;
    private float ValorProd;
    private String Observacao;
    private String TipoProd;
    private String CategoriaProd;

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

    public int getIdProd() {
        return IdProd;
    }

    public void setIdProd(int idProd) {
        IdProd = idProd;
    }

    public String getDescProd() {
        return DescProd;
    }

    public void setDescProd(String descProd) {
        DescProd = descProd;
    }

    public float getValorProd() {
        return ValorProd;
    }

    public void setValorProd(float valorProd) {
        ValorProd = valorProd;
    }

    public String getObservacao() {
        return Observacao;
    }

    public void setObservacao(String observacao) {
        Observacao = observacao;
    }

    public String getTipoProd() {
        return TipoProd;
    }

    public void setTipoProd(String tipoProd) {
        TipoProd = tipoProd;
    }

    public String getCategoriaProd() {
        return CategoriaProd;
    }

    public void setCategoriaProd(String categoriaProd) {
        CategoriaProd = categoriaProd;
    }
}
