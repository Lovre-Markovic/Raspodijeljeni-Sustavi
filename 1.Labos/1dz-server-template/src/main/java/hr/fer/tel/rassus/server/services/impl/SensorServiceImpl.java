package hr.fer.tel.rassus.server.services.impl;

import hr.fer.tel.rassus.server.beans.Sensor;
import hr.fer.tel.rassus.server.services.SensorRepository;
import hr.fer.tel.rassus.server.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SensorServiceImpl implements SensorService {
    @Autowired
    private SensorRepository sensorRepository;


    @Override
    public Sensor register(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Override
    public ResponseEntity<Sensor> nearestNeighbour(Sensor sensor) {
        List<Sensor> listOfAllSensors = sensorRepository.findAll();


        Sensor nearestNeighbor = null;
        double minDistance = Double.MAX_VALUE;

        for (Sensor otherSensor : listOfAllSensors) {
            if (!(otherSensor.getLatitude() == (sensor.getLatitude()) && (otherSensor.getLongitude() == sensor.getLongitude()))) {
                double distance = calculateDistance(sensor, otherSensor);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestNeighbor = otherSensor;
                }
            }
        }

        if (nearestNeighbor != null) {
            return ResponseEntity.ok(nearestNeighbor);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    // Metoda za izračun udaljenosti između dvaju senzora pomoću Haversineove formule
    private double calculateDistance(Sensor sensor1, Sensor sensor2) {
        double R = 6371; // Radijus Zemlje u kilometrima
        double dlon = Math.toRadians(sensor2.getLongitude() - sensor1.getLongitude());
        double dlat = Math.toRadians(sensor2.getLatitude() - sensor1.getLatitude());
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(Math.toRadians(sensor1.getLatitude())) * Math.cos(Math.toRadians(sensor2.getLatitude())) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }


}



