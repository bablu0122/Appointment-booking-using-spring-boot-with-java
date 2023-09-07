package com.Appointmentservice.api.controller;

import com.Appointmentservice.api.entity.Appointment;
import com.Appointmentservice.api.paload.Doctor;
import com.Appointmentservice.api.paload.Patient;
import com.Appointmentservice.api.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final RestTemplate restTemplate;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, RestTemplate restTemplate) {
        this.appointmentService = appointmentService;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        System.out.println("GetAllAppointments");
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return appointment != null
                ? ResponseEntity.ok(appointment)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment savedAppointment = null;
        try {
            // Get patient information using RestTemplate
            ResponseEntity<Patient> patientResponse = restTemplate.getForEntity(
                    "http://localhost:9091/patients/" + appointment.getPatientId(),
                    Patient.class);

            // Check if patient information was retrieved successfully
            if (patientResponse.getStatusCode() != HttpStatus.OK || patientResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Patient patient = patientResponse.getBody();

            // Get doctor information using RestTemplate
            ResponseEntity<Doctor> doctorResponse = restTemplate.getForEntity(
                    "http://localhost:9090/doctors/" + appointment.getDoctorId(),
                    Doctor.class);

            // Check if doctor information was retrieved successfully
            if (doctorResponse.getStatusCode() != HttpStatus.OK || doctorResponse.getBody() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Doctor doctor = doctorResponse.getBody();

            // Save the appointment
            savedAppointment = appointmentService.saveAppointment(appointment);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAppointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    // Additional methods if needed...
}
