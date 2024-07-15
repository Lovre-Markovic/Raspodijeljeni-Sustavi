package com.example.HumidityMicroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HumidityService {

    @Autowired
    private HumidityRepository humidityRepository;

    public Humidity saveHumidity(Humidity temperature) {
        return humidityRepository.save(temperature);
    }
}

