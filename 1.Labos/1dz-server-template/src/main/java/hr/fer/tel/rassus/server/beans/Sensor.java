package hr.fer.tel.rassus.server.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    private double longitude;
    private double latitude;
    private String ip;
    private int port;

    public Sensor() {
    }

    @JsonCreator
    public Sensor(

            @JsonProperty("longitude") double longitude,
            @JsonProperty("latitude") double latitude,
            @JsonProperty("ip") String ip,
            @JsonProperty("port") int port
    ) {

        this.longitude = longitude;
        this.latitude = latitude;
        this.ip = ip;
        this.port = port;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
