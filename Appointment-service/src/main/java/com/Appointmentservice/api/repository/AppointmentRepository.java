package com.Appointmentservice.api.repository;

import com.Appointmentservice.api.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}

