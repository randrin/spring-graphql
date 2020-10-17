package com.fullstack.api.controller;

import com.fullstack.api.exception.PersonServiceException;
import com.fullstack.api.model.Person;
import com.fullstack.api.repository.PersonRepository;
import com.fullstack.api.service.PersonService;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Value("classpath:person.graphqls")
    private Resource schemaResource;

    private GraphQL graphQL;

    @PostConstruct
    public void loadSchema() throws IOException {
        File schemaFile = schemaResource.getFile();
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring runtimeWiring = buildRuntimeWiring();
        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        DataFetcher<List<Person>> dataFetcher1 = data -> {
            return (List<Person>) personRepository.findAll();
        };

        DataFetcher<Person> dataFetcher2 = data -> {
            return personRepository.findByEmail(data.getArgument("email"));
        };

        return RuntimeWiring.newRuntimeWiring().type("Query", typeWriting ->
                typeWriting.dataFetcher("getAllPerson", dataFetcher1).dataFetcher("findPerson", dataFetcher2)).build();
    }

    @PostMapping("/addPersons")
    public String addPersons(@RequestBody List<Person> personList) {
        return personService.addPersons(personList);
    }

    @GetMapping("/getPersons")
    public List<Person> getPersons() {
        return personService.getPersons();
    }

    @GetMapping("/getPersonByName/{name}")
    public String getPersonByName(@PathVariable String name) throws PersonServiceException {
        return personService.getPersonByName(name);
    }

    @PostMapping("/getAllPersons")
    public ResponseEntity<Object> getAllPersons(@RequestBody String query) {
        ExecutionResult executionResult = graphQL.execute(query);
        return new ResponseEntity<Object>(executionResult, HttpStatus.OK);
    }

    @PostMapping("/getPersonByEmail")
    public ResponseEntity<Object> getPersonByEmail(@RequestBody String query) {
        ExecutionResult executionResult = graphQL.execute(query);
        return new ResponseEntity<Object>(executionResult, HttpStatus.OK);
    }
}
