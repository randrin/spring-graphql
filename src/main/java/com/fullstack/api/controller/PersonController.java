package com.fullstack.api.controller;

import com.fullstack.api.model.Person;
import com.fullstack.api.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/add")
    public String addPerson(@RequestBody List<Person> personList) {
        personRepository.saveAll(personList);
        return "Persons added successfuly: " +personList.size();
    }

    @GetMapping("/getPersons")
    public List<Person> getPersons() {
        List<Person> personList = (List<Person>) personRepository.findAll();
        return personList;
    }
}
