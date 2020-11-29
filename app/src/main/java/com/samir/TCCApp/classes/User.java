package com.samir.TCCApp.classes;

import java.io.Serializable;

public class User implements Serializable {

    public int IdUsuario;
    public String UsuarioText;
    public String Senha;
    public String TipoAcesso;

    public int getIdUsu() {
        return IdUsuario;
    }

    public void setIdUsu(int idUsu) {
        this.IdUsuario = idUsu;
    }

    public String getUserName() {
        return UsuarioText;
    }

    public void setUserName(String userName) {
        this.UsuarioText = userName;
    }

    public String getPassword() {
        return Senha;
    }

    public void setPassword(String password) {
        this.Senha = password;
    }

    public String getAcessType() {
        return TipoAcesso;
    }

    public void setAcessType(String acessType) {
        this.TipoAcesso = acessType;
    }
}
