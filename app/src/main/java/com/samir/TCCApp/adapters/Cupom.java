package com.samir.TCCApp.adapters;

import java.util.Date;

public class Cupom {

    String CodCupom;
    float Desconto;
    int NumUtiliza;
    int TotalDispo;
    String DataInicio;
    String DataTerm;
    String Descri;
    float ValorMin;

    public String getCodCupom() {
        return CodCupom;
    }

    public void setCodCupom(String codCupom) {
        CodCupom = codCupom;
    }

    public float getDesconto() {
        return Desconto;
    }

    public void setDesconto(float desconto) {
        Desconto = desconto;
    }

    public int getNumUtiliza() {
        return NumUtiliza;
    }

    public void setNumUtiliza(int numUtiliza) {
        NumUtiliza = numUtiliza;
    }

    public int getTotalDispo() {
        return TotalDispo;
    }

    public void setTotalDispo(int totalDispo) {
        TotalDispo = totalDispo;
    }

    public String getDataInicio() {
        return DataInicio;
    }

    public void setDataInicio(String dataInicio) {
        DataInicio = dataInicio;
    }

    public String getDataTerm() {
        return DataTerm;
    }

    public void setDataTerm(String dataTerm) {
        DataTerm = dataTerm;
    }

    public String getDescri() {
        return Descri;
    }

    public void setDescri(String descri) {
        Descri = descri;
    }

    public float getValorMin() {
        return ValorMin;
    }

    public void setValorMin(float valorMin) {
        ValorMin = valorMin;
    }
}
