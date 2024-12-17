package com.hmsApp.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryDto {
    private Long id;

    @NotBlank
    @Size(min = 3,max=100,message="Name must be between 3 and 100 characters.")
    private String countryName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters.") String getCountryName() {
        return countryName;
    }

    public void setCountryName(@NotBlank @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters.") String countryName) {
        this.countryName = countryName;
    }
}
