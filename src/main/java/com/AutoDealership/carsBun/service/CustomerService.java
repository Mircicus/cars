package com.AutoDealership.carsBun.service;


import com.AutoDealership.carsBun.entity.Customer;
import com.AutoDealership.carsBun.exception.CustomerNotFoundException;
import com.AutoDealership.carsBun.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepo customerRepository;

    @Autowired
    public CustomerService(CustomerRepo customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void saveCustomer(Customer customer) {

        customerRepository.save(customer);
    }

    public void updateCustomer(Long id, Customer customer) {
        Optional<Customer> existingCustomerOptional = customerRepository.findById(id);
        if (existingCustomerOptional.isPresent()) {
            Customer existingCustomer = existingCustomerOptional.get();
            existingCustomer.setName(customer.getName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setPhoneNumber(customer.getPhoneNumber());


            customerRepository.save(existingCustomer);
        } else {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found");
        }
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + id));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    // Methods with pagination
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Customer findCustomerWithLongestName() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .max(Comparator.comparingInt(customer -> customer.getName().length()))
                .orElseThrow(() -> new CustomerNotFoundException("No customers found"));
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }


}
