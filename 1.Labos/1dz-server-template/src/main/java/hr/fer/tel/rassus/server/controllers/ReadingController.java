package hr.fer.tel.rassus.server.controllers;


import hr.fer.tel.rassus.server.beans.Reading;
import hr.fer.tel.rassus.server.services.ReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReadingController {

  // TODO 4.3  Spremanje očitanja pojedinog senzora

    @Autowired
    private final ReadingRepository readingRepository;

    public ReadingController(ReadingRepository readingRepository) {
        this.readingRepository = readingRepository;
    }


    @PostMapping("/RaspSus/Ocitanje/{sensorId}/ocitanja")
    public ResponseEntity<Reading> saveReading(@PathVariable String sensorId, @RequestBody Reading reading) {
        reading.setSensorId(sensorId);
        Reading saved = readingRepository.save(reading);
        if(saved != null) {
            return new ResponseEntity<>(
                    saved, HttpStatus.CREATED
            );
        } else {
            return new ResponseEntity<>(
                   new Reading() , HttpStatus.NO_CONTENT
            );
        }


    }

  // TODO 4.5  Popis očitanja pojedinog senzora

    @GetMapping("/RaspSus/Ocitanje/{sensorId}/svaOcitanja")
    public ResponseEntity<List<Reading>> getAllReadings(@PathVariable String sensorId) {

        List<Reading> lista = readingRepository.findBySensorId(sensorId);
        if(lista.isEmpty()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.NO_CONTENT
            );
        } else {
            return new ResponseEntity<>(
                    lista,
                    HttpStatus.OK
            );
        }


    }

}