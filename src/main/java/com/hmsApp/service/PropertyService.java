package com.hmsApp.service;

import com.hmsApp.Exception.ResourceNotFound;
import com.hmsApp.entity.City;
import com.hmsApp.entity.Country;
import com.hmsApp.entity.Property;
import com.hmsApp.payload.PropertyDto;
import com.hmsApp.repository.CityRepository;
import com.hmsApp.repository.CountryRepository;
import com.hmsApp.repository.PropertyRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {
    private ModelMapper modelMapper;
    private CountryRepository countryRepository;
    private PropertyRepository propertyRepository;
    private CityRepository cityRepository;

    public  PropertyService(ModelMapper modelMapper, PropertyRepository propertyRepository,
                            CountryRepository countryRepository,CityRepository cityRepository)
    {
        this.modelMapper = modelMapper;
        this.propertyRepository = propertyRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }
    public PropertyDto addPropertyToDb(PropertyDto propertyDto, String country,String city) {
        Property property = mapToEntity(propertyDto);
        Country country1 = countryRepository.findByCountryName(country).
                orElseThrow(() -> new ResourceNotFound("Country not found: " + country));
        City city1 = cityRepository.findByCityName(city).
                orElseThrow(() -> new ResourceNotFound("City not found: " + city));
        property.setCity(city1);
        property.setCountry(country1);

        Property saveProperty = propertyRepository.save(property);
        PropertyDto propertyDto1 = mapToDto(saveProperty);
        return propertyDto1;

    }

    public Property mapToEntity(PropertyDto propertyDto) {
        return modelMapper.map(propertyDto, Property.class);
    }

    public PropertyDto mapToDto(Property property)
    {
        return modelMapper.map(property, PropertyDto.class);
    }

    public void deleteProperty(String name) {
        Property property = propertyRepository.findByName(name)
               .orElseThrow(() -> new ResourceNotFound("Property not found: " + name));
        propertyRepository.delete(property);

    }

    public List<PropertyDto> getAllProperties(String searchParameters) {

        List<Property> properties = propertyRepository.searchProperty(searchParameters);
//        return Collections.singletonList(mapToDto((Property) properties));

        List<PropertyDto> propertiesDto = properties
                .stream()
                .map(p->mapToDto(p)).collect(Collectors.toList());
        return propertiesDto;


    }
}
