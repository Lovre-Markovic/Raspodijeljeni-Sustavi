package com.example.AggregationService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RefreshScope
@Configuration
@EnableAutoConfiguration
public class AgregationService {

    @Value("${temperature.service.name}")
    private String temperatureServiceUrl;



    @Value("${humidity.service.name}")
    private String humidityServiceUrl;


    @Value("${temperature.unit}")
    private String tempUnit;



    private final RestTemplate restTemplate;

    public AgregationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/aggregate")
    public ResponseEntity<List<Agregation>> aggregateReadings() {

        temperatureServiceUrl = "http://" + temperatureServiceUrl+ "/temperature";

        // Poziv mikroservisa za temperaturu
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(temperatureServiceUrl, String.class);

        Temperature temperature = mapJsonToTemperature(responseEntity.getBody());

        System.out.println(temperatureServiceUrl);

        if (temperature != null) {

            if(temperature.getUnit().equals(tempUnit)) {
                if(tempUnit.equals("K")) {
                    temperature.setValue(temperature.getValue() + 273.15);
                } else {
                    temperature.setValue(temperature.getValue()-273.15);
                }
            }
        } else {

            System.out.println("Failed to convert JSON to Temperature object");
        }

        humidityServiceUrl ="http://" + humidityServiceUrl+ "/humidity";

        // Poziv mikroservisa za vlagu
        ResponseEntity<String> responseEntity2 = restTemplate.getForEntity(humidityServiceUrl, String.class);

        Humidity humidity = mapJsonToHumidity(responseEntity2.getBody());

        if (humidity != null) {

            System.out.println(humidity.toString());
        } else {

            System.out.println("Failed to convert JSON to Temperature object");
        }





        // Check for null values


        // Agregacija odgovora
        List<Agregation> aggregatedReadings = new ArrayList<>();
        aggregatedReadings.add(new Agregation("Humidity", "%", humidity.getValue()));
        aggregatedReadings.add(new Agregation("Temperature", "C", temperature.getValue()));

        return new ResponseEntity<>(aggregatedReadings, HttpStatus.OK);
    }


    public static Temperature mapJsonToTemperature(String json) {
        try {
            // Parse JSON manually
            JSONObject jsonObject = new JSONObject(json);

            // Extract values from JSON
            String name = jsonObject.getString("name");
            String unit = jsonObject.getString("unit");
            double temperatureValue = jsonObject.getInt("value");

            // Create Temperature object
            return new Temperature(name, unit, temperatureValue);
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., log, throw a custom exception)
            e.printStackTrace();
            return null; // Return null or throw an exception based on your error handling strategy
        }
    }

    public static Humidity mapJsonToHumidity(String json) {
        try {
            // Parse JSON manually
            JSONObject jsonObject = new JSONObject(json);

            // Extract values from JSON
            String name = jsonObject.getString("name");
            String unit = jsonObject.getString("unit");
            int HumidityValue = jsonObject.getInt("value");

            // Create Temperature object
            return new Humidity(name, unit, HumidityValue);
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., log, throw a custom exception)
            e.printStackTrace();
            return null; // Return null or throw an exception based on your error handling strategy
        }
    }

}
