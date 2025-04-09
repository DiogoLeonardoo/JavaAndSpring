package br.com.study.services;

import br.com.study.data.dto.v1.BookDto;
import br.com.study.exception.ResourceNotFoundException;
import br.com.study.mapper.custom.BookMapper;
import br.com.study.mapper.custom.PersonMapper;
import br.com.study.model.Book;
import br.com.study.model.Person;
import br.com.study.repository.BookRepository;
import br.com.study.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static br.com.study.mapper.ObjectMapper.parseListObjects;
import static br.com.study.mapper.ObjectMapper.parseObject;

@Service
public class BookService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(BookService.class.getName());

    @Autowired
    BookRepository repository;

    @Autowired
    BookMapper converter;
    @Autowired
    private PersonRepository personRepository;

    public List<BookDto> findAll() {

        logger.info("Finding all books!");

        var books = parseListObjects(repository.findAll() , BookDto.class);
        return books;
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
}
