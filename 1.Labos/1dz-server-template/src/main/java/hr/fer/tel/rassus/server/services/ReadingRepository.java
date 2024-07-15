package hr.fer.tel.rassus.server.services;

import hr.fer.tel.rassus.server.beans.Reading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, Long> {

    List<Reading> findBySensorId(String sensorId);



}
