package br.com.study.controllers;


import br.com.study.data.dto.v1.BookDto;
import br.com.study.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books/v1")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping(value = "/{id}")
    public BookDto findById(@PathVariable("id") Long id) {
        var book = service.findById(id);
        return book;
    }

    @GetMapping
    public List<BookDto> findAll() {
        return service.findAll();
    }

    @PostMapping
    public BookDto create(@RequestBody BookDto book) {
        return service.create(book);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

    @PutMapping(value = "/{id}")
    public BookDto update(@RequestBody BookDto book) {
        return service.update(book);
    }
}
