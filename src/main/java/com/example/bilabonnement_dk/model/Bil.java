package com.example.bilabonnement_dk.model;

import java.time.LocalDate;

public class Bil {
    private int bil_ID;
    private LocalDate indkoebsdato;
    private String vognnr;
    private String stelnr;
    private String udstyrsniveau;
    private double staalpris;
    private double regafg;
    private int co2udl;
    private BilType biltype;
    private String maerke;
    private String model;

    public Bil() {
    }

    public Bil(int bil_ID, LocalDate indkoebsdato, String vognnr, String stelnr, String udstyrsniveau, double staalpris, double regafg, int co2udl, BilType biltype, String maerke, String model) {
        this.bil_ID = bil_ID;
        this.indkoebsdato = indkoebsdato;
        this.vognnr = vognnr;
        this.stelnr = stelnr;
        this.udstyrsniveau = udstyrsniveau;
        this.staalpris = staalpris;
        this.regafg = regafg;
        this.co2udl = co2udl;
        this.biltype = biltype;
        this.maerke = maerke;
        this.model = model;
    }

    public int getBil_ID() {
        return bil_ID;
    }

    public void setBil_ID(int bil_ID) {
        this.bil_ID = bil_ID;
    }

    public LocalDate getIndkoebsdato() {
        return indkoebsdato;
    }

    public void setIndkoebsdato(LocalDate indkoebsdato) {
        this.indkoebsdato = indkoebsdato;
    }

    public String getVognnr() {
        return vognnr;
    }

    public void setVognnr(String vognnr) {
        this.vognnr = vognnr;
    }

    public String getStelnr() {
        return stelnr;
    }

    public void setStelnr(String stelnr) {
        this.stelnr = stelnr;
    }

    public String getUdstyrsniveau() {
        return udstyrsniveau;
    }

    public void setUdstyrsniveau(String udstyrsniveau) {
        this.udstyrsniveau = udstyrsniveau;
    }

    public double getStaalpris() {
        return staalpris;
    }

    public void setStaalpris(double staalpris) {
        this.staalpris = staalpris;
    }

    public double getRegafg() {
        return regafg;
    }

    public void setRegafg(double regafg) {
        this.regafg = regafg;
    }

    public int getCo2udl() {
        return co2udl;
    }

    public void setCo2udl(int co2udl) {
        this.co2udl = co2udl;
    }

    public BilType getBiltype() {
        return biltype;
    }

    public void setBiltype(BilType Biltype) {
        this.biltype = Biltype;
    }

    public String getMaerke() {
        return maerke;
    }

    public void setMaerke(String maerke) {
        this.maerke = maerke;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}