package hr.fer.tel.rassus.server.services;

import hr.fer.tel.rassus.server.beans.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    @Override
    List<Sensor> findAll();
}
