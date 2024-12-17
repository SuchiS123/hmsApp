package com.hmsApp.controller;

import com.hmsApp.payload.CityDto;
import com.hmsApp.service.CityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/city")
public class CityController {

    private CityService cityService;
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/add/city")
    public ResponseEntity<?> addCity(@Valid @RequestBody CityDto cityDto, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        CityDto cityDto1 = cityService.addCityToDb(cityDto);
        return new ResponseEntity<>(cityDto1, HttpStatus.OK);
    }


}
