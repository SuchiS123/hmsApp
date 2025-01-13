package com.hmsApp.controller;

import com.hmsApp.entity.Country;
import com.hmsApp.entity.Property;
import com.hmsApp.entity.PropertyImage;
import com.hmsApp.payload.PropertyDto;
import com.hmsApp.repository.CountryRepository;
import com.hmsApp.repository.PropertyImageRepository;
import com.hmsApp.repository.PropertyRepository;
import com.hmsApp.service.BucketService;
import com.hmsApp.service.PropertyService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class propertyController {
    private PropertyService propertyService;
    private PropertyRepository propertyRepository;
    private BucketService bucketService;
    private PropertyImageRepository propertyImageRepository;


    public propertyController(PropertyService propertyService,
                              PropertyRepository propertyRepository,
                              BucketService bucketService,
                              PropertyImageRepository propertyImageRepository)
    {
        this.propertyService = propertyService;
        this.propertyRepository = propertyRepository;
        this.bucketService = bucketService;
        this.propertyImageRepository = propertyImageRepository;

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

    // add property photo to mysql database

    @PostMapping("/upload/photos/{bucketName}/{propertyId}")
    public String uploadPropertyPhotos(@RequestParam MultipartFile file,
                                       @PathVariable String bucketName,
                                       @PathVariable long propertyId) throws IllegalAccessException {
        String imageUrl=bucketService.uploadFile(file,bucketName);
        PropertyImage propertyImage= new PropertyImage();
        propertyImage.setUrl(imageUrl);
        Property property = propertyRepository.findById(propertyId).orElseThrow
                (() -> new RuntimeException("Could not find property "));
        propertyImage.setProperty(property);
        propertyImageRepository.save(propertyImage);
        return "Image is Uploaded";
    }

    @GetMapping("/get/property/images")
    public List<PropertyImage> getPropertyImages(@RequestParam long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(() ->
                new RuntimeException(" Property not found for property " + propertyId));
        List<PropertyImage> byProperty = propertyImageRepository.findByProperty(property);

        return  byProperty;
    }
}

