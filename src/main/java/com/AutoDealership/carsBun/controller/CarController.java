package com.AutoDealership.carsBun.controller;

import com.AutoDealership.carsBun.entity.Car;
import com.AutoDealership.carsBun.entity.Dealership;
import com.AutoDealership.carsBun.service.CarService;
import com.AutoDealership.carsBun.service.DealershipService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private final DealershipService dealershipService;

    public CarController(CarService carService, DealershipService dealershipService) {
        this.carService = carService;
        this.dealershipService = dealershipService;
    }


    @GetMapping("/add")
    public String showAddCarForm(Model model) {
        model.addAttribute("car", new Car());  // Creăm un obiect gol de tip Car pentru formular
        List<Dealership> dealerships = dealershipService.findAll();  // Preluăm lista de Dealership-uri
        model.addAttribute("dealerships", dealerships);  // Adăugăm lista de Dealership-uri la model
        return "addCar";  // Presupunem că pagina pentru adăugare este addCar.html
    }


    @PostMapping("/add")
    public String addCar(@ModelAttribute Car car, RedirectAttributes redirectAttributes) {
        carService.saveCar(car);  // Salvăm mașina
        redirectAttributes.addFlashAttribute("successMessage", "Car added successfully!");  // Mesaj de succes
        return "redirect:/cars";  // Redirecționăm înapoi la lista de mașini
    }


    @GetMapping
    public String showAllCars(@RequestParam(defaultValue = "0") int page, Model model) {
        // Creăm un Pageable pentru paginare, cu 5 elemente per pagină
        Pageable pageable = PageRequest.of(page, 5);  // Poți ajusta numărul de elemente per pagină (5, 10, etc.)
        Page<Car> carsPage = carService.getAllCars(pageable); // Obținem o pagină de mașini

        model.addAttribute("carPage", carsPage);  // Adăugăm pagina de mașini la model
        model.addAttribute("currentPage", page);  // Păstrăm pagina curentă pentru navigare
        model.addAttribute("totalPages", carsPage.getTotalPages());  // Adăugăm totalul de pagini
        return "cars";  // Presupunem că pagina pentru listare este cars.html
    }


    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Car car = carService.findById(id);
        if (car == null) {
            model.addAttribute("errorMessage", "Car not found.");
            return "redirect:/cars";
        }
        List<Dealership> dealerships = dealershipService.findAll();  // Preluăm lista de Dealership-uri
        model.addAttribute("car", car);
        model.addAttribute("dealerships", dealerships);
        return "updateCar";  // Presupunem că există o pagină updateCar.html
    }


    @PostMapping("/update/{id}")
    public String updateCar(@PathVariable Long id, @ModelAttribute Car car, RedirectAttributes redirectAttributes) {
        try {
            carService.updateCar(id, car);
            redirectAttributes.addFlashAttribute("successMessage", "Car updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating car.");
        }
        return "redirect:/cars";
    }


    @PostMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            carService.deleteCar(id);
            redirectAttributes.addFlashAttribute("successMessage", "Car deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting car.");
        }
        return "redirect:/cars";
    }
}
