package com.example.rest.services;

import com.example.rest.dto.SensorDTO;
import com.example.rest.models.Sensor;
import com.example.rest.repositories.SensorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Optional<Sensor> findByName(String name){
        return sensorRepository.findByName(name);
    }

    public void save(Sensor sensor){
        sensorRepository.save(sensor);
    }

}
