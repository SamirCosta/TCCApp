package com.samir.TCCApp.classes;

public class User {

    public int idUsu;
    public String userName;
    public String password;
    public String acessType;

    public int getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(int idUsu) {
        this.idUsu = idUsu;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAcessType() {
        return acessType;
    }

    public void setAcessType(String acessType) {
        this.acessType = acessType;
    }
}
