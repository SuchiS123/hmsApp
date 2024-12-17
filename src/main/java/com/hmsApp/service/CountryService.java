package com.hmsApp.service;

import com.hmsApp.entity.City;
import com.hmsApp.entity.Country;
import com.hmsApp.payload.CityDto;
import com.hmsApp.payload.CountryDto;
import com.hmsApp.repository.CityRepository;
import com.hmsApp.repository.CountryRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
    private ModelMapper modelMapper;
    private CountryRepository countryRepository;

    public CountryService(ModelMapper modelMapper, CountryRepository countryRepository) {
            this.modelMapper = modelMapper;
            this.countryRepository = countryRepository;
        }

        public CountryDto addCountryToDb(CountryDto countryDto){

            Country country = mapToEntity(countryDto);
            Country saveCountry = countryRepository.save(country);

            CountryDto countryDto1 = mapToDto(saveCountry);
            return countryDto1;


        }



        public Country mapToEntity(CountryDto countryDto)
        {
            Country country=modelMapper.map(countryDto,Country.class);
            return country;
        }

        public CountryDto mapToDto(Country country)
        {
            CountryDto countryDto=modelMapper.map(country, CountryDto.class);
            return countryDto;
        }
    }

