package com.AutoDealership.carsBun.repo;



import com.AutoDealership.carsBun.entity.Dealership;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DealershipRepo extends JpaRepository<Dealership, Long> {  // Change Integer to Long

    @Query("SELECT DISTINCT d.location FROM Dealership d WHERE d.location IS NOT NULL")
    List<String> findDistinctLocations();

    List<Dealership> findByNameContainingIgnoreCase(String name);

    List<Dealership> findAllByOrderByLocationAsc();

    Page<Dealership> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description, Pageable pageable);

    Page<Dealership> findByLocationOrderByLocationAsc(String location, Pageable pageable);

    List<Dealership> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);

    List<Dealership> findByLocationOrderByLocationAsc(String location);

    Page<Dealership> findAll(Pageable pageable);

    Page<Dealership> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Dealership> findByLocation(String location, Pageable pageable);
}
