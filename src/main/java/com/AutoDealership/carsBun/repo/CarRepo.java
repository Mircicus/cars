package com.AutoDealership.carsBun.repo;

import com.AutoDealership.carsBun.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepo extends JpaRepository<Car, Long> {
}
