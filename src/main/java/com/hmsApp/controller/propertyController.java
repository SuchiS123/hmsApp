package com.hmsApp.controller;

import com.hmsApp.entity.Country;
import com.hmsApp.entity.Property;
import com.hmsApp.payload.PropertyDto;
import com.hmsApp.repository.CountryRepository;
import com.hmsApp.repository.PropertyRepository;
import com.hmsApp.service.PropertyService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class propertyController {
    private PropertyService propertyService;
    private PropertyRepository propertyRepository;


    public propertyController(PropertyService propertyService,
                              PropertyRepository propertyRepository)
    {
        this.propertyService = propertyService;
        this.propertyRepository = propertyRepository;

    }

    @PostMapping("/addProperty/{country}/{city}")
    public ResponseEntity<?> addProperty(@Valid @RequestBody PropertyDto propertyDto,
                                         @PathVariable String country,@PathVariable String city ,BindingResult result)
    {
        if(result.hasErrors())
        {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


        PropertyDto propertyDto1 = propertyService.addPropertyToDb(propertyDto,country,city);
        return new ResponseEntity<>(propertyDto1, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteProperty/{name}")
    public ResponseEntity<String> deleteProperty(@PathVariable String name)
    {
        propertyService.deleteProperty(name);
        return new ResponseEntity<>("Deleted Property",HttpStatus.OK);
    }

    @DeleteMapping("/deletePropertyByCity/{cityId}")
    public ResponseEntity<?> deletePropertyByCityId(@PathVariable Long cityId) {
        List<Property> properties = propertyRepository.findByCityId(cityId);
        if (!properties.isEmpty()) {
            propertyRepository.deleteAll(properties);  // Delete all properties for the city
            return new ResponseEntity<>("Properties deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No properties found for this city", HttpStatus.NOT_FOUND);
        }
}

//http://localhost:8080/api/v1/property/{searchParam}

    @GetMapping("/{searchParamCity}")
    public ResponseEntity<List<PropertyDto>> getAllProperties(@PathVariable String searchParamCity) {
        List<PropertyDto> propertyDtos = propertyService.getAllProperties(searchParamCity);
        return new ResponseEntity<>(propertyDtos, HttpStatus.OK);
    }

    @PutMapping("/updateProperty/{id}/{country}/{city}")
    public ResponseEntity<PropertyDto> updateProperty(@PathVariable Long id,
                                                      @RequestBody PropertyDto propertyDto,
                                                      @PathVariable String country,
                                                      @PathVariable String city)
    {
        PropertyDto savedPropertyDto = propertyService.updateProperty(id, propertyDto, country,city);
        return new ResponseEntity<>(savedPropertyDto, HttpStatus.OK);


    }
}

