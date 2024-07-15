package hr.fer.tel.rassus.examples;

import java.util.List;

public class Ocitanje {

    private long id;
    private double temperature;
    private double pressure;
    private double humidity;
    private double coConcentration;
    private double no2Concentration;

    private double so2Concentration;

    public Ocitanje() {

    }

    public Ocitanje(long id, double temperature, double pressure, double humidity, double coConcentration, double no2Concentration, double so2Concentration) {
        this.id = 0;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.coConcentration = coConcentration;
        this.no2Concentration = no2Concentration;
        this.so2Concentration = so2Concentration;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getCoConcentration() {
        return coConcentration;
    }

    public void setCoConcentration(double coConcentration) {
        this.coConcentration = coConcentration;
    }

    public double getNo2Concentration() {
        return no2Concentration;
    }

    public void setNo2Concentration(double no2Concentration) {
        this.no2Concentration = no2Concentration;
    }

    public double getSo2Concentration() {
        return so2Concentration;
    }

    public void setSo2Concentration(double so2Concentration) {
        this.so2Concentration = so2Concentration;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Ocitanje{" +
                "id = " + id +
                ", temperature=" + temperature +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", coConcentration=" + coConcentration +
                ", no2Concentration=" + no2Concentration +
                ", so2Concentration=" + so2Concentration +
                '}';
    }


    public  Ocitanje getReading(List<Ocitanje> listOfReadings, long startTimeMillis, long currentTimeMillis1) {


        long activeSeconds1 = (currentTimeMillis1 - startTimeMillis) / 1000;
        int line = (int) ((activeSeconds1 % 100) +1);
        System.out.println(line);
        Ocitanje reading1 = listOfReadings.get(line);
        return reading1;

    }


}
