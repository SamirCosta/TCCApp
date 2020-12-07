package com.samir.TCCApp.classes;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Product implements Serializable {

//            NomeProd;
    private int IdProd;
    private String NomeProd;
    private String Imagem;
    private int QtdProd = 1;
    private String DescProd;
    private float ValorProd;
    private String Observacao;
    private String TipoProd;
    private String CategoriaProd;

    public Product(){

    }

    public String getImagem() {
        return Imagem;
    }

    public void setImagem(String imagem) {
        Imagem = imagem;
    }

    public void setName(String name) {
        this.NomeProd = name;
    }

    public int getQtd() {
        return QtdProd;
    }

    public void setQtd(int qtd) {
        this.QtdProd = qtd;
    }

    public String getName() {
        return NomeProd;
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
