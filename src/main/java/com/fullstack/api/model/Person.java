package com.fullstack.api.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String mobile;
    private String email;
    private String[] address;
}
