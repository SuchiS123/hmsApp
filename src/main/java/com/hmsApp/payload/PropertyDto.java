package com.hmsApp.payload;

import com.hmsApp.entity.City;
import com.hmsApp.entity.Country;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyDto {

    private Long id;

    @NotBlank
    @Size(min = 3,max=100,message="Name must be between 3 and 100 characters.")
    private String name;

    @NotNull(message = "Number of guests cannot be null.")
    @Min(value = 1, message = "There must be at least 1 guest.")
    @Max(value = 50, message = "Number of guests cannot exceed 50.")
    private Integer noOfGuest;

    @NotNull(message = "Number of bedrooms cannot be null.")
    @Min(value = 1, message = "There must be at least 1 bedroom.")
    @Max(value = 20, message = "Number of bedrooms cannot exceed 20.")
    private Integer noOfBedrooms;

    @NotNull(message = "Number of bathrooms cannot be null.")
    @Min(value = 1, message = "There must be at least 1 bathroom.")
    @Max(value = 20, message = "Number of bathrooms cannot exceed 20.")
    private Integer noOfBathrooms;

    private City city;
    private Country country;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNoOfGuest() {
        return noOfGuest;
    }

    public void setNoOfGuest(Integer noOfGuest) {
        this.noOfGuest = noOfGuest;
    }

    public Integer getNoOfBedrooms() {
        return noOfBedrooms;
    }

    public void setNoOfBedrooms(Integer noOfBedrooms) {
        this.noOfBedrooms = noOfBedrooms;
    }

    public Integer getNoOfBathrooms() {
        return noOfBathrooms;
    }

    public void setNoOfBathrooms(Integer noOfBathrooms) {
        this.noOfBathrooms = noOfBathrooms;
    }
}
