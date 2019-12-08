package com.example.limetestapp.data.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Valute {
    public Valute(String id, String numCode, String charCode, Integer nominal,
                  String name, Double currentValue, Double previousValue) {
        this.id = id;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.currentValue = currentValue;
        this.previousValue = previousValue;
    }

    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("NumCode")
    @Expose
    private String numCode;
    @SerializedName("CharCode")
    @Expose
    private String charCode;
    @SerializedName("Nominal")
    @Expose
    private Integer nominal;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Value")
    @Expose
    private Double currentValue;
    @SerializedName("Previous")
    @Expose
    private Double previousValue;

    public String getId() {
        return id;
    }

    public String getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public Integer getNominal() {
        return nominal;
    }

    public String getName() {
        return name;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public Double getPreviousValue() {
        return previousValue;
    }

    public void setiD(String id) {
        this.id = id;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public void setPreviousValue(Double previousValue) {
        this.previousValue = previousValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Valute valute = (Valute) o;
        return Objects.equals(id, valute.id) &&
                Objects.equals(numCode, valute.numCode) &&
                Objects.equals(charCode, valute.charCode) &&
                Objects.equals(nominal, valute.nominal) &&
                Objects.equals(name, valute.name) &&
                Objects.equals(currentValue, valute.currentValue) &&
                Objects.equals(previousValue, valute.previousValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numCode, charCode, nominal, name, currentValue, previousValue);
    }
}
