package com.samir.TCCApp.classes;

import java.util.List;

public class InsertProd {

    int idMesa = 0;
    int idCli;
    List<Product> products;
    String FormPag;
    String CodCupom;
    float QtdPontos;
    String CPF;

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getIdCli() {
        return idCli;
    }

    public void setIdCli(int idCli) {
        this.idCli = idCli;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getFormPag() {
        return FormPag;
    }

    public void setFormPag(String formPag) {
        FormPag = formPag;
    }

    public String getCodCupom() {
        return CodCupom;
    }

    public void setCodCupom(String codCupom) {
        CodCupom = codCupom;
    }

    public float getQtdPontos() {
        return QtdPontos;
    }

    public void setQtdPontos(float qtdPontos) {
        QtdPontos = qtdPontos;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
}
