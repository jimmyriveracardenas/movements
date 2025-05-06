package com.tcs.technicaltest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "persona")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "persona_id")
    private Long personId;

    @Column(name= "nombre", nullable = false, length = 100)
    private String name;

    @Column(name= "genero", length = 1)
    private String gender;

    @Column(name="edad")
    private Integer age;

    @Column(name="identificacion", nullable = false, unique = true, length = 50)
    private String identification;

    @Column(name= "direccion", columnDefinition = "TEXT")
    private String address;

    @Column(name= "telefono", length = 20)
    private String phone;

    @OneToOne(mappedBy = "person")
    @JsonBackReference("person-client")
    private Client client;
    
}