package com.example.rest.controllers;

import com.example.rest.dto.MeasurementDTO;
import com.example.rest.models.Measurement;
import com.example.rest.models.Sensor;
import com.example.rest.services.MeasurementService;
import com.example.rest.services.SensorService;
import com.example.rest.util.MeasurementNotAddedException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final ModelMapper mapper;
    private final MeasurementService measurementService;
    private final SensorService sensorService;

    @Autowired
    public MeasurementsController(ModelMapper mapper, MeasurementService measurementService, SensorService sensorService) {
        this.mapper = mapper;
        this.measurementService = measurementService;
        this.sensorService = sensorService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult){
        Optional<Sensor> optionalSensor = sensorService.findByName(measurementDTO.getSensor().getName());
        if(optionalSensor.isEmpty()){
            throw new MeasurementNotAddedException("There is no sensor with the name you typed");
        }
        else if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder errorMessage = new StringBuilder();
            for(FieldError error : errors){
                errorMessage.append(error.getField()).append(":").append(error.getDefaultMessage()).append(";\n");
            }
            throw new MeasurementNotAddedException(errorMessage.toString());
        }
        Sensor sensor = optionalSensor.get();
        measurementDTO.setSensor(sensor);
        measurementService.save(mapper.map(measurementDTO, Measurement.class));
        return ResponseEntity.ok("vse normalno");
    }

    @ExceptionHandler
    private ResponseEntity<String> handleMeasurementNotAddedException(MeasurementNotAddedException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping()
    public List<MeasurementDTO> getMeasurements(){
        System.out.println("getter being invoked");
        List<Measurement> measurementList= measurementService.findAll();
        List <MeasurementDTO> measurementDTOList = new ArrayList<>();
        for (Measurement measurement : measurementList){
            measurementDTOList.add(mapper.map(measurement, MeasurementDTO.class));
            System.out.println(measurement);
        }
        System.out.println(measurementDTOList);
        return measurementDTOList;
    }


    @GetMapping("/rainyDaysCount")
    public int rainyDaysCount(){
        return measurementService.getRainyDaysCount();
    }


}
