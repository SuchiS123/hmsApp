package com.hmsApp.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDto {

    private Long id;

    @NotBlank
    @Size(min = 3,max=100,message="Name must be between 3 and 100 characters.")
    private String cityName;


}
