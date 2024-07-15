package hr.fer.tel.rassus.server.services;

import hr.fer.tel.rassus.server.beans.Sensor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface SensorService {

    Sensor register(Sensor sensor);

    ResponseEntity<Sensor> nearestNeighbour(Sensor sensor);
}
