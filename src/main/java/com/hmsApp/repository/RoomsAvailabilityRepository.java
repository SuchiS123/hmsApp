package com.hmsApp.repository;

import com.hmsApp.entity.RoomsAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomsAvailabilityRepository extends JpaRepository<RoomsAvailability, Long> {

    @Query("SELECT r from RoomsAvailability r where r.room_type = :room_type " +
            "AND r.date BETWEEN :fromDate AND :toDate " +
            "AND r.property.id = :propertyId")
    List<RoomsAvailability> findAvailableRooms(@Param("fromDate") LocalDate fromDate,
                                               @Param("toDate") LocalDate toDate,
                                               @Param("room_type") String roomType,
                                               @Param("propertyId") long propertyId);
}