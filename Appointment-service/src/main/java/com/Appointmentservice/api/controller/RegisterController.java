package com.Appointmentservice.api.controller;

import com.Appointmentservice.api.paload.Doctor;
import com.Appointmentservice.api.paload.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final RestTemplate restTemplate;

    @Autowired
    public RegisterController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/patient")
    public ResponseEntity<String> registerPatient(@RequestBody Patient patient) {
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:9091/patients", patient, String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return ResponseEntity.ok("Patient registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/doctor")
    public ResponseEntity<String> registerDoctor(@RequestBody Doctor doctor) {
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:9090/doctors", doctor, String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return ResponseEntity.ok("Doctor registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

