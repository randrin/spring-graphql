package com.fullstack.api.service;

import com.fullstack.api.exception.PersonServiceException;
import com.fullstack.api.model.Person;
import com.fullstack.api.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public String addPersons(List<Person> persons) {
        personRepository.save(persons);
        return "Persons added successfuly: " + persons.size();
    }

    public List<Person> getPersons() {
        List<Person> personList = (List<Person>) personRepository.findAll();
        return personList;
    }

    public String getPersonByName(String name) throws PersonServiceException {
        Person person = null;
        try {
            person = personRepository.findByName(name);
            return "Person found : " + person.getName() + " - " + person.getEmail();
        } catch (Exception e) {
            throw new PersonServiceException("Person not found with name: " +name);
        }
    }
}
