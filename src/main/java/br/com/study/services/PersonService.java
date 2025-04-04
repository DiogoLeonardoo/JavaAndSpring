package br.com.study.services;

import br.com.study.controllers.PersonController;
import br.com.study.data.dto.v1.PersonDto;
import br.com.study.data.dto.v2.PersonDtoV2;
import br.com.study.exception.ResourceNotFoundException;
import static br.com.study.mapper.ObjectMapper.parseListObjects;
import static br.com.study.mapper.ObjectMapper.parseObject;

import br.com.study.mapper.custom.PersonMapper;
import br.com.study.model.Person;
import br.com.study.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper converter;


    public List<PersonDto> findAll() {

        logger.info("Finding all People!");

        var persons = parseListObjects(repository.findAll(), PersonDto.class);
        persons.forEach(this::addHateoasLinks);
        return persons;
    }

    public PersonDto findById(Long id) {
        logger.info("Finding one Person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var dto = parseObject(entity, PersonDto.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDto create(PersonDto person) {

        logger.info("Creating one Person!");
        var entity = parseObject(person, Person.class);

        var dto = parseObject(repository.save(entity), PersonDto.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDtoV2 createV2(PersonDtoV2 person) {

        logger.info("Creating one Person!");
        var entity = converter.convertDtoToEntity(person);

        return converter.convertEntityToDto(repository.save(entity));
    }

    public PersonDto update(PersonDto person) {

        logger.info("Updating one Person!");
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(repository.save(entity), PersonDto.class);
        addHateoasLinks(dto);
        return dto;
    }


    public void delete(Long id) {

        logger.info("Deleting one Person!");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }

    private void addHateoasLinks(PersonDto dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
    }
}