package com.example.HumidityMicroService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumidityRepository extends JpaRepository<Humidity,Long> {

}
