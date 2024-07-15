package com.example.TemperatureMicroService;


import com.example.TemperatureMicroService.Readings.Reading;
import com.example.TemperatureMicroService.Readings.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TemperatureController {

    @Autowired
    private TemperatureService service;
    @Autowired
    private ReadingService readingsService;
    @GetMapping("/temperature")
    public ResponseEntity<Temperature> getTemperature() {

        List<Reading> listOfReadings = readingsService.loadReadings();

        long currentTimeInMinutes = System.currentTimeMillis()/60000;
        int line = (int)(currentTimeInMinutes % 100) +1;


        Temperature temp = new Temperature("Temperature","C",listOfReadings.get(line).getTemperature());


        Temperature tempo = service.saveTemperature(temp);

        System.out.println("Saved Temperature :" +tempo);

        return new ResponseEntity<>(tempo, HttpStatus.OK);
    }
}
