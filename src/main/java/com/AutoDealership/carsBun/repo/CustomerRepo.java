package com.AutoDealership.carsBun.repo;



import com.AutoDealership.carsBun.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {


    List<Customer> findByNameContainingIgnoreCase(String name);


    Page<Customer> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Pageable pageable);


    List<Customer> findByPhoneNumberContaining(String phoneNumber);



    Page<Customer> findAll(Pageable pageable);


    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);


    Page<Customer> findByEmailContainingIgnoreCase(String email, Pageable pageable);
}
