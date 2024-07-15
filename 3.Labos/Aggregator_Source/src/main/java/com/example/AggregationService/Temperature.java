package com.example.AggregationService;


public class Temperature {




    private String name;


    private String unit;

    private double temperatureValue;


    public Temperature(){};

    public Temperature(String name, String unit, double temperatureValue) {
        this.name = name;
        this.unit = unit;
        this.temperatureValue = temperatureValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getValue() {
        return temperatureValue;
    }

    public void setValue(double value) {
        this.temperatureValue = temperatureValue;
    }

    @Override
    public String toString() {
        return "Temperature{" +

                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", temperatureValue=" + temperatureValue +
                '}';
    }
}
