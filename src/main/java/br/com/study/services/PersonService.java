package br.com.study.services;

import br.com.study.controllers.PersonController;
import br.com.study.data.dto.v1.PersonDto;
import br.com.study.data.dto.v2.PersonDtoV2;
import br.com.study.exception.ResourceNotFoundException;
import static br.com.study.mapper.ObjectMapper.parseObject;

import br.com.study.mapper.custom.BookMapper;
import br.com.study.mapper.custom.PersonMapper;
import br.com.study.model.Person;
import br.com.study.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper converter;
    @Autowired
    private PageableArgumentResolver pageableArgumentResolver;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BookMapper bookMapper;


    public Page<PersonDto> findAll(Pageable pageable) {
        logger.info("Finding all People!");

        return repository.findAll(pageable)
                .map(person -> {
                    PersonDto dto = parseObject(person, PersonDto.class);
                    addHateoasLinks(dto);
                    return dto;
                });
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

    @Transactional
    public PersonDto disablePerson(Long id) {

        logger.info("Disabling one Person!");

        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.disablePerson(id);

        var entity = repository.findById(id).get();
        var dto = parseObject(entity, PersonDto.class);
        return dto;
    }

    public void delete(Long id) {

        logger.info("Deleting one Person!");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }

    public Page<PersonDto> findByName(String firstName, Pageable pageable) {
        logger.info("Finding people by name!");

        return repository.findPeopleByFirstName(firstName, pageable)
                .map(person -> {
                    PersonDto dto = parseObject(person, PersonDto.class);
                    addHateoasLinks(dto);
                    return dto;
                });
    }


    private void addHateoasLinks(PersonDto dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findAll(1,12,"asc")).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
    }
}