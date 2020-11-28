package com.samir.TCCApp.classes;

public class Client {
    public int IdCli;
    public String CPF;
    public String NomeCli;
    public String EmailCli;
    public long CelCli;
    public String Comp;
    public int NumEdif;
    public float QtdPontos;
    public String Imagem;
    public Addressess Endereco;
    public User User;

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public int getIdCli() {
        return IdCli;
    }

    public void setIdCli(int IdCli) {
        this.IdCli = IdCli;
    }

    public Addressess getAddressess() {
        return Endereco;
    }

    public void setAddressess(Addressess addressess) {
        this.Endereco = addressess;
    }

    public String getNameCli() {
        return NomeCli;
    }

    public void setNameCli(String nameCli) {
        this.NomeCli = nameCli;
    }

    public String getEmailCli() {
        return EmailCli;
    }

    public void setEmailCli(String emailCli) {
        this.EmailCli = emailCli;
    }

    public long getCelCli() {
        return CelCli;
    }

    public void setCelCli(long celCli) {
        this.CelCli = celCli;
    }

    public String getComp() {
        return Comp;
    }

    public void setComp(String comp) {
        this.Comp = comp;
    }

    public int getNumEdif() {
        return NumEdif;
    }

    public void setNumEdif(int numEdif) {
        this.NumEdif = numEdif;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        this.User = user;
    }

    public float getQtdPonto() {
        return QtdPontos;
    }

    public void setQtdPonto(float qtdPonto) {
        this.QtdPontos = qtdPonto;
    }

    public String getImagem() {
        return Imagem;
    }

    public void setImagem(String imagem) {
        this.Imagem = imagem;
    }
}
