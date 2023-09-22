package com.example.rest.dto;

import com.example.rest.models.Sensor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public class MeasurementDTO {

    @NotNull(message = "please select the sensor's name")
    private Sensor sensor;

    @NotNull(message = "value can't be null")
    @Min(value = -100, message = "value should be greater than -100")
    @Max(value = 100, message = "value should be less than 100")
    private Double value;

    @NotNull(message = "raining field expects a boolean value")
    private boolean raining;

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" +
                "sensor=" + sensor +
                ", value=" + value +
                ", raining=" + raining +
                '}';
    }
}
