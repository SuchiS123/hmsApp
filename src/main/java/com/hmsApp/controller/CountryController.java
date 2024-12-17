package com.hmsApp.controller;

import com.hmsApp.payload.CityDto;
import com.hmsApp.payload.CountryDto;
import com.hmsApp.service.CityService;
import com.hmsApp.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {


    private CountryService countryService;
    public CountryController (CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/add/country")
    public ResponseEntity<?> addCity(@Valid @RequestBody CountryDto countryDto, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        CountryDto countryDto1 = countryService.addCountryToDb(countryDto);
        return new ResponseEntity<>(countryDto1, HttpStatus.OK);
    }
}
