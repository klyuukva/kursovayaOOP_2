package com.example.service2.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@RestController
public class CargoShipController {
    private final Gson gson = new Gson();
    private static final String SCHEDULE_JSON_DEFAULT_PATH = "schedule/schedule.json";

    CargoShipController() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8010/schedules/generate";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String schedule = responseEntity.getBody();

        try (FileWriter fileWriter = new FileWriter(SCHEDULE_JSON_DEFAULT_PATH)) {
            assert schedule != null;
            fileWriter.write(schedule);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/schedules/get")
    public ResponseEntity<String> getJSON() {
        return getJSON(SCHEDULE_JSON_DEFAULT_PATH);
    }

    @GetMapping("/schedules/get/custom_path")
    public ResponseEntity<String> getJSON(@RequestParam(value = "fileName", defaultValue = SCHEDULE_JSON_DEFAULT_PATH)
                                                  String fileName) {
        try(FileReader fileReader = new FileReader(fileName)) {
            JsonElement jsonElement = gson.fromJson(fileReader, JsonElement.class);
            return new ResponseEntity<>(gson.toJson(jsonElement), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT, "File not found");
    }

    @PostMapping(value = "/schedules/result", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> saveResults(@RequestBody String result){
        try(FileWriter fileWriter = new FileWriter("schedule/result.json")) {
            assert result != null;
            fileWriter.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
