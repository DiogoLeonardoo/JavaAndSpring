package br.com.study.services;

import br.com.study.controllers.BookController;
import br.com.study.data.dto.v1.BookDto;
import br.com.study.exception.ResourceNotFoundException;
import br.com.study.mapper.custom.BookMapper;
import br.com.study.model.Book;
import br.com.study.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

import static br.com.study.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(BookService.class.getName());

    @Autowired
    BookRepository repository;

    @Autowired
    BookMapper converter;
    @Autowired
    private BookRepository bookRepository;

    public Page<BookDto> findAll(Pageable pageable) {
        logger.info("Finding all books!");
        return bookRepository.findAll(pageable)
                .map(book -> parseObject(book, BookDto.class));

    }

    public BookDto findById(Long id) {

        logger.info("Finding one Book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
        var dto = parseObject(entity, BookDto.class);
        return dto;
    }

    public BookDto create(BookDto book) {

        logger.info("Create one Book!");
        var entity = converter.convertDtoToEntity(book);
        return converter.convertEntityToDto(repository.save(entity));
    }

    public BookDto update(BookDto book) {

        logger.info("Update one person");

        Book entity = repository.findById(book.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var dto = parseObject(repository.save(entity) , BookDto.class);
        return dto;
    }

    public void delete(Long id) {

        logger.info("Deleting one Book!");

        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }

    private void addHateoasLinks(BookDto dto) {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).findAll(1,12,"asc")).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("POST"));
    }
}
