package com.Appointmentservice.api.paload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    private Long id;
    private String name;
    private int age;
    private String email;

    // Other fields, getters, setters
}

