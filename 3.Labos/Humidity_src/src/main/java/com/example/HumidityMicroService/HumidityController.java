package com.example.HumidityMicroService;


import com.example.HumidityMicroService.Readings.Reading;
import com.example.HumidityMicroService.Readings.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HumidityController {

    @Autowired
    private  HumidityService service;
    @Autowired
    private ReadingService readingsService;

    @GetMapping("/humidity")
    public ResponseEntity<Humidity> getHumidity() {
        List<Reading> listOfReadings = readingsService.loadReadings();

        //System.out.println(listOfReadings);

        long currentTimeInMinutes = System.currentTimeMillis()/60000;
        int line = (int)(currentTimeInMinutes % 100) +1;

        //System.out.println(line);


        Humidity humidity = new Humidity("Humidity","%",listOfReadings.get(line).getHumidity());


        Humidity hum = service.saveHumidity(humidity);

        System.out.println("Saved Humidity: " + hum);

        return new ResponseEntity<>(hum, HttpStatus.OK);

    }


}
