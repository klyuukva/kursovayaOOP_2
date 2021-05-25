package com.example.service1.controller;

import com.example.service1.model.Schedule;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CargoShipController {
    private final int COUNT_OF_SHIPS = 30;
    private final Gson gson = new Gson();
    private String schedule;

    @GetMapping("/schedules/generate")
    public ResponseEntity<String> generateSchedule() {
        return new ResponseEntity<>(schedule = gson.toJson(new Schedule(COUNT_OF_SHIPS)), HttpStatus.OK);
    }

    @GetMapping("/schedules")
    public ResponseEntity<String> getSchedule() {
        return new ResponseEntity<String>(schedule, HttpStatus.OK);
    }


}
