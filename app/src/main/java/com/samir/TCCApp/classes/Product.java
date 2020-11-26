package com.samir.TCCApp.classes;

import java.io.Serializable;

public class Product implements Serializable {

//            NomeProd;
    private int IdProd;
    private String NomeProd;
    private int image;
    private int qtd = 1;
    private String DescProd;
    private float ValorProd;
    private String Observacao;
    private String TipoProd;
    private String CategoriaProd;

    public Product(String name, int image) {
        this.NomeProd = name;
        this.image = image;
    }

    public Product(){

    }

    public void setName(String name) {
        this.NomeProd = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public String getName() {
        return NomeProd;
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
