package com.example.HumidityMicroService;


import jakarta.persistence.*;

@Entity
@Table(name = "humidity")
public class Humidity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "unit")
    private String unit;

    @Column(name = "humidity_value")
    private int humidityValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Humidity() {
    }

    ;

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
                "id=" + id +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", humidityValue=" + humidityValue +
                '}';
    }
}