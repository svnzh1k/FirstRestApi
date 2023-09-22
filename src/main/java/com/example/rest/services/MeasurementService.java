package com.example.rest.services;

import com.example.rest.models.Measurement;
import com.example.rest.repositories.MeasurementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;


    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public void save(Measurement measurement) {
        measurement.setTime(LocalDateTime.now());
        measurementRepository.save(measurement);
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    public int getRainyDaysCount() {
        return measurementRepository.countAllByRainingTrue();
    }
}
