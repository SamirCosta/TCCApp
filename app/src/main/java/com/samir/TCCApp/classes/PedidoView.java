package com.samir.TCCApp.classes;

import java.io.Serializable;
import java.util.List;

public class PedidoView implements Serializable {

    public String NomeCompleto;
    public String CPF;
    public String idComanda;
    public List<Product> produtos;
    public String prodStatus;
    public String dataPed;
    public String CEP;
    public String Logra;
    public String Bairro;
    public String Cidade;
    public String Comp;
    public int NumEdif;

    public String getNomeCompleto() {
        return NomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        NomeCompleto = nomeCompleto;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(String idComanda) {
        this.idComanda = idComanda;
    }

    public List<Product> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Product> produtos) {
        this.produtos = produtos;
    }

    public String getProdStatus() {
        return prodStatus;
    }

    public void setProdStatus(String prodStatus) {
        this.prodStatus = prodStatus;
    }

    public String getDataPed() {
        return dataPed;
    }

    public void setDataPed(String dataPed) {
        this.dataPed = dataPed;
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
        Logra = logra;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        Bairro = bairro;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        Cidade = cidade;
    }

    public String getComp() {
        return Comp;
    }

    public void setComp(String comp) {
        Comp = comp;
    }

    public int getNumEdif() {
        return NumEdif;
    }

    public void setNumEdif(int numEdif) {
        NumEdif = numEdif;
    }
}
