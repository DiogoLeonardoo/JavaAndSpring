package br.com.study.mapper.custom;

import br.com.study.data.dto.v1.BookDto;
import br.com.study.data.dto.v2.PersonDtoV2;
import br.com.study.model.Book;
import br.com.study.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BookMapper {

    public BookDto convertEntityToDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setAuthor(book.getAuthor());
        dto.setLaunchDate(book.getLaunchDate());
        dto.setPrice(book.getPrice());
        dto.setTitle(book.getTitle());

        return dto;
    }

    public Book convertDtoToEntity(BookDto book) {

        Book entity = new Book();
        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        return entity;
    }

}
