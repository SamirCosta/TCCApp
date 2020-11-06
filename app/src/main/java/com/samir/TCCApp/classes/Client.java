package com.samir.TCCApp.classes;

public class Client {
    public int idCli;
    public int CPF;
    public Addressess addressess;
    public String nameCli;
    public String emailCli;
    public long celCli;
    public String comp;
    public int numEdif;
    public User user;
    public float qtdPonto;
    public String imagem;

    public int getIdCli() {
        return idCli;
    }

    public void setIdCli(int idCli) {
        idCli = idCli;
    }

    public int getCPF() {
        return CPF;
    }

    public void setCPF(int CPF) {
        this.CPF = CPF;
    }

    public Addressess getAddressess() {
        return addressess;
    }

    public void setAddressess(Addressess addressess) {
        this.addressess = addressess;
    }

    public String getNameCli() {
        return nameCli;
    }

    public void setNameCli(String nameCli) {
        this.nameCli = nameCli;
    }

    public String getEmailCli() {
        return emailCli;
    }

    public void setEmailCli(String emailCli) {
        this.emailCli = emailCli;
    }

    public long getCelCli() {
        return celCli;
    }

    public void setCelCli(long celCli) {
        this.celCli = celCli;
    }

    public String getComp() {
        return comp;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }

    public int getNumEdif() {
        return numEdif;
    }

    public void setNumEdif(int numEdif) {
        this.numEdif = numEdif;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getQtdPonto() {
        return qtdPonto;
    }

    public void setQtdPonto(float qtdPonto) {
        this.qtdPonto = qtdPonto;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
