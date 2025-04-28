package br.com.study.data.dto.v1;

import br.com.study.serializer.GenderSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@XmlRootElement
@JsonPropertyOrder({"id","address","firstName" , "lastName"})
@JsonFilter("PersonFilter")
public class PersonDto extends RepresentationModel<PersonDto> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String phoneNumber;

    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date birthDay;
    private String address;

    @JsonSerialize(using = GenderSerializer.class)
    private String gender;
    private Boolean enabled;

    public PersonDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonDto personDto = (PersonDto) o;
        return Objects.equals(id, personDto.id) && Objects.equals(firstName, personDto.firstName) && Objects.equals(lastName, personDto.lastName) && Objects.equals(phoneNumber, personDto.phoneNumber) && Objects.equals(birthDay, personDto.birthDay) && Objects.equals(address, personDto.address) && Objects.equals(gender, personDto.gender) && Objects.equals(enabled, personDto.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, firstName, lastName, phoneNumber, birthDay, address, gender, enabled);
    }
}
