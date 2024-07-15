package com.example.TemperatureMicroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemperatureService {

    @Autowired
    private TemperatureRepository repository;

    public Temperature saveTemperature(Temperature temperature) {
        return repository.save(temperature);
    }
}
