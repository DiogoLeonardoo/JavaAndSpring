package br.com.study.controllers;


import br.com.study.data.dto.v1.BookDto;
import br.com.study.data.dto.v1.PersonDto;
import br.com.study.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Page<BookDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
        Page<BookDto> result = service.findAll(pageable);
        return ResponseEntity.ok(result);
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
