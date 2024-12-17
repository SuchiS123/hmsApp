package com.hmsApp.service;


import com.hmsApp.entity.City;
import com.hmsApp.entity.User;
import com.hmsApp.payload.CityDto;
import com.hmsApp.payload.UserDto;
import com.hmsApp.repository.CityRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private CityRepository cityRepository;
    private ModelMapper modelMapper;
    public CityService(ModelMapper modelMapper, CityRepository cityRepository) {
        this.modelMapper = modelMapper;
        this.cityRepository = cityRepository;
    }

    public CityDto addCityToDb(CityDto cityDto) {

        City city = mapToEntity(cityDto);
        City saveCity = cityRepository.save(city);

        CityDto cityDto1 = mapToDto(saveCity);
        return cityDto1;


    }



    public City mapToEntity(CityDto cityDto)
    {
       City city=modelMapper.map(cityDto, City.class);
        return city;
    }

    public CityDto mapToDto(City city)
    {
        CityDto cityDto=modelMapper.map(city, CityDto.class);
        return cityDto;
    }

}
