package com.AutoDealership.carsBun.entity;

import com.AutoDealership.carsBun.entity.Car;
import com.AutoDealership.carsBun.entity.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dealerships")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dealership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dealershipId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "website")
    private String website;

    @OneToMany(mappedBy = "dealership", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Car> cars = new ArrayList<>();

    @ManyToMany(mappedBy = "dealerships", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Customer> customers = new ArrayList<>();

}
