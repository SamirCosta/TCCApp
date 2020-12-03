package com.samir.TCCApp.classes;

import java.io.Serializable;

public class Addressess implements Serializable {

    String address;
    String CEP;
    String Logra;
    String Bairro;
    String Cidade;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getLogra() {
        return Logra;
    }

    public void setLogra(String logra) {
        this.Logra = logra;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        this.Bairro = bairro;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        this.Cidade = cidade;
    }
}
