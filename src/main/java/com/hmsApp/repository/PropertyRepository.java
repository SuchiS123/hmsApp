package com.hmsApp.repository;

import com.hmsApp.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    Optional<Property> findByName(String name);

    List<Property> findByCityId(Long cityId);
//    jpql query to search  for properties based on city and country
    @Query("select p from Property p JOIN p.city c JOIN p.country co where c.cityName=:searchParam or co.countryName=:searchParam")
    List<Property> searchProperty(
            @Param("searchParam") String cityFromLink
    );
}
