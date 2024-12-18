package com.hmsApp.repository;

import com.hmsApp.entity.Property;
import com.hmsApp.entity.Reviews;
import com.hmsApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
   List<Reviews> findByUser(User user);
   Reviews findByPropertyAndUser(Property property,User user);
}