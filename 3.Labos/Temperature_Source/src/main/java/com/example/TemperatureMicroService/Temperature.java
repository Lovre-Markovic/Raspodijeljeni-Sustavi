package com.example.TemperatureMicroService;


import jakarta.persistence.*;

@Entity
@Table(name = "temperature")
public class Temperature {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "unit")
    private String unit;
    @Column(name = "temperature_value")
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", temperatureValue=" + temperatureValue +
                '}';
    }
}