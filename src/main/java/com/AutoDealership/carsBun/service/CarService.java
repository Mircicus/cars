package com.AutoDealership.carsBun.service;

import com.AutoDealership.carsBun.entity.Car;
import com.AutoDealership.carsBun.exception.CarNotFoundException;
import com.AutoDealership.carsBun.repo.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepo carRepo;

    @Autowired
    public CarService(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    public Page<Car> getAllCars(Pageable pageable) {
        return carRepo.findAll(pageable);
    }

    public Car saveCar(Car car) {
        return carRepo.save(car);
    }

    public Car findById(Long id) {
        return carRepo.findById(id).orElseThrow(() -> new CarNotFoundException("Car not found"));
    }

    public void deleteCar(Long id){
        carRepo.deleteById(id);
    }

    public void updateCar(Long id, Car updatedCar) {
        Optional<Car> existingCarOptional = carRepo.findById(id);
        if (existingCarOptional.isPresent()) {
            Car existingCar = existingCarOptional.get();

            existingCar.setModel(updatedCar.getModel());
            existingCar.setMake(updatedCar.getMake());
            existingCar.setYear(updatedCar.getYear());
            existingCar.setDealership(updatedCar.getDealership());

            carRepo.save(existingCar);
        } else {
            throw new IllegalArgumentException("Car with ID " + id + " not found.");
        }
    }

    public List<Car> getAllCars() {
        return carRepo.findAll();
    }
}
