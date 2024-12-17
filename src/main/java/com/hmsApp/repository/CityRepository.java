package com.hmsApp.repository;

import com.hmsApp.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, String> {

    Optional<City> findByCityName(String cityName);
}