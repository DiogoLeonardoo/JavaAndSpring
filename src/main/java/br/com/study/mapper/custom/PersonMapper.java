package br.com.study.mapper.custom;

import br.com.study.data.dto.v1.PersonDto;
import br.com.study.data.dto.v2.PersonDtoV2;
import br.com.study.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public PersonDtoV2 convertEntityToDto(Person person) {
        PersonDtoV2 dto = new PersonDtoV2();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setGender(person.getGender());
        dto.setBirthday(new Date());
        dto.setAddress(person.getAddress());

        return dto;
    }

    public Person convertDtoToEntity(PersonDtoV2 person) {

        Person entity = new Person();
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setGender(person.getGender());
        entity.setAddress(person.getAddress());

        return entity;
    }
}
