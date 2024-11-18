package com.AutoDealership.carsBun.service;

import com.AutoDealership.carsBun.entity.Dealership;
import com.AutoDealership.carsBun.exception.DealershipNotFoundException;
import com.AutoDealership.carsBun.repo.DealershipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DealershipService {

    private final DealershipRepo dealershipRepo;

    private static final String[] VEHICLE_TYPES = {"SUV", "Sedan", "Truck", "Electric", "Hybrid"};

    @Autowired
    public DealershipService(DealershipRepo dealershipRepo) {
        this.dealershipRepo = dealershipRepo;
    }

    public void saveDealership(Dealership dealership) {
        dealershipRepo.save(dealership);
    }

    public void updateDealership(Long id, Dealership updatedDealership) {
        Dealership dealership = dealershipRepo.findById(id)
                .orElseThrow(() -> new DealershipNotFoundException("Dealership with ID " + id + " not found"));
        dealership.setName(updatedDealership.getName());
        dealership.setLocation(updatedDealership.getLocation());
        dealership.setDescription(updatedDealership.getDescription());
        dealership.setWebsite(updatedDealership.getWebsite());
        dealershipRepo.save(dealership);
    }

    public Dealership findById(Long id) {
        return dealershipRepo.findById(id)
                .orElseThrow(() -> new DealershipNotFoundException("Dealership not found with id " + id));
    }

    @Transactional
    public void deleteDealership(Long id) {

        Dealership dealership = dealershipRepo.findById(id)
                .orElseThrow(() -> new DealershipNotFoundException("Dealership with ID " + id + " not found"));


        dealership.getCustomers().forEach(customer -> customer.getDealerships().remove(dealership));
        dealership.getCustomers().clear();


        dealershipRepo.delete(dealership);
    }



    public Map<String, List<Dealership>> getDealershipsGroupedByLocation() {
        List<Dealership> dealerships = dealershipRepo.findAll();
        return dealerships.stream()
                .collect(Collectors.groupingBy(Dealership::getLocation));
    }

    public Page<Dealership> getAllDealerships(Pageable pageable) {
        return dealershipRepo.findAll(pageable);
    }

    public Page<Dealership> searchDealershipsByName(String name, Pageable pageable) {
        return dealershipRepo.findByNameContainingIgnoreCase(name, pageable);
    }

    public List<Dealership> findAllDealerships() {
        return dealershipRepo.findAll();
    }

    public List<Dealership> findAll() {
        return dealershipRepo.findAll();
    }
}
