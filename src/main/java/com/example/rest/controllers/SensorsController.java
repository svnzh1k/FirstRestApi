package com.example.rest.controllers;

import com.example.rest.dto.SensorDTO;
import com.example.rest.models.Sensor;
import com.example.rest.services.SensorService;
import com.example.rest.util.SensorNotCreatedException;
import com.example.rest.util.UniqueSensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorService sensorService;
    private final ModelMapper mapper;
    private final UniqueSensorValidator uniqueSensorValidator;

    @Autowired
    public SensorsController(SensorService sensorService, ModelMapper mapper, UniqueSensorValidator uniqueSensorValidator) {
        this.sensorService = sensorService;
        this.mapper = mapper;
        this.uniqueSensorValidator = uniqueSensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult){
        Sensor sensor = mapper.map(sensorDTO, Sensor.class);
        uniqueSensorValidator.validate(sensor, bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errorList = bindingResult.getFieldErrors();
            for(FieldError error : errorList){
                errorMessage.append(error.getField()).append(":").append(error.getDefaultMessage()).append(";");
            }
            throw new SensorNotCreatedException(errorMessage.toString());
        }
        sensorService.save(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }



    @ExceptionHandler
    private ResponseEntity <String> handleRegistrationException(SensorNotCreatedException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

}
