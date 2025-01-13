package com.hmsApp.repository;

import com.hmsApp.entity.Property;
import com.hmsApp.entity.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {


    List<PropertyImage> findByProperty(Property propertyId);
}