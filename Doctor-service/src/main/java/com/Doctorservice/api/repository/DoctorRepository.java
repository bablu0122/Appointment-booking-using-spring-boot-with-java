package com.Doctorservice.api.repository;

import com.Doctorservice.api.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}

