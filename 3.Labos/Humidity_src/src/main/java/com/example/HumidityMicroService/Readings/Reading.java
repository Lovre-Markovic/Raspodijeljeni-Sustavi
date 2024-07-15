package com.example.HumidityMicroService.Readings;

public class Reading {
    private int temperature;
    private int pressure;
    private int humidity;
    private int CO;
    private int NO2;
    private int SO2;

    public Reading(){};

    public Reading(int temperature, int pressure, int humidity, int CO, int NO2, int SO2) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.CO = CO;
        this.NO2 = NO2;
        this.SO2 = SO2;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getCO() {
        return CO;
    }

    public void setCO(int CO) {
        this.CO = CO;
    }

    public int getNO2() {
        return NO2;
    }

    public void setNO2(int NO2) {
        this.NO2 = NO2;
    }

    public int getSO2() {
        return SO2;
    }

    public void setSO2(int SO2) {
        this.SO2 = SO2;
    }

    @Override
    public String toString() {
        return "Readings{" +
                "temperature=" + temperature +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", CO=" + CO +
                ", NO2=" + NO2 +
                ", SO2=" + SO2 +
                '}';
    }
}
