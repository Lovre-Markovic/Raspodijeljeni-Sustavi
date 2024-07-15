package hr.fer.tel.rassus.server.controllers;


import hr.fer.tel.rassus.server.beans.Sensor;
import hr.fer.tel.rassus.server.services.SensorRepository;
import hr.fer.tel.rassus.server.services.SensorService;
import hr.fer.tel.rassus.server.services.impl.SensorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SensorsController {

    @Autowired
    private SensorServiceImpl sensorServiceImpl;

    @Autowired
    private SensorRepository sensorRepository;

    //  TODO 4.1  Registracija

    @PostMapping("/RaspSus/registracijaSenzora/")
    public Sensor registerSensor(@RequestBody Sensor sensor) {



        Sensor registeredSensor = sensorServiceImpl.register(sensor);
        return registeredSensor;

    }




    //  TODO 4.4  Popis senzora

    @GetMapping("/RaspSus/SviSenzori")
    public ResponseEntity<List<Sensor>> listOfAllSensors() {
        return new ResponseEntity<>(
                sensorRepository.findAll(),
                HttpStatus.OK
        );

    }

    //  TODO 4.2  Najbliži susjed


    @PostMapping("/RaspSus/NajbliziSusjed/")
    public ResponseEntity<Sensor> nearestNeighbour(@RequestBody Sensor sensor) {

        ResponseEntity<Sensor> nearestNeighbour = sensorServiceImpl.nearestNeighbour(sensor);
        return nearestNeighbour;
    }

}
