package br.com.study.controllers;

import br.com.study.data.dto.v1.PersonDto;
import br.com.study.data.dto.v2.PersonDtoV2;
import br.com.study.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PersonDto findById(@PathVariable("id") Long id) {
         var person = personService.findById(id);
         person.setBirthDay(new Date());
         person.setPhoneNumber("");
         person.setPhoneNumber(null);
         person.setSensitiveData("Foo bar");
         return person;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<PersonDto> findAll() {
        return personService.findAll();
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )

    public PersonDto create(@RequestBody PersonDto person) {
        return personService.create(person);
    }

    @PostMapping(value = "/v2",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )

    public PersonDtoV2 create(@RequestBody PersonDtoV2 person) {
        return personService.createV2(person);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        personService.delete(id);
    }

    @PutMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public PersonDto update(@RequestBody PersonDto person) {
        return personService.update(person);
    }
}
