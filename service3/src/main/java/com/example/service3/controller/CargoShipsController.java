package com.example.service3.controller;

import com.example.service3.model.Port;
import com.example.service3.model.Schedule;
import com.example.service3.model.Statistic;
import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class CargoShipsController {
    private final int[] CRANES_COUNT = {1, 1, 1};
    private final Gson gson = new Gson();
    private final String statistic;
    private RestTemplate restTemplate;

    CargoShipsController() {
        restTemplate = new RestTemplate();
        String url = "http://localhost:8011/schedules/get";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        Schedule schedule = gson.fromJson(responseEntity.getBody(), Schedule.class);
        statistic = gson.toJson(Port.dataProcessing(schedule, CRANES_COUNT));
    }

    @GetMapping("/schedules/statistic")
    public ResponseEntity<String> getStatistic() {
        return new ResponseEntity<>(gson.toJson(statistic), HttpStatus.OK);
    }

    @PostMapping(value = "schedules/sent/statistic", produces = "application/json")
    public ResponseEntity<String> sentStatistic() {
        restTemplate = new RestTemplate();
        String results = statistic;
        String url = "http://localhost:8011/schedules/result";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(results, httpHeaders);
        restTemplate.postForEntity(url, request, String.class);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
