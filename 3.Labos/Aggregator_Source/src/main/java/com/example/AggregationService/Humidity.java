package com.example.AggregationService;


public class Humidity {





    private String name;


    private String unit;


    private int humidityValue;



    public Humidity(){};

    public Humidity(String name, String unit, int humidityValue) {
        this.name = name;
        this.unit = unit;
        this.humidityValue = humidityValue;
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

    public int getValue() {
        return humidityValue;
    }

    public void setValue(int value) {
        this.humidityValue = humidityValue;
    }

    @Override
    public String toString() {
        return "Humidity{" +

                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", humidityValue=" + humidityValue +
                '}';
    }
}
