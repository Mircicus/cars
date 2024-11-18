package com.AutoDealership.carsBun.controller;



import com.AutoDealership.carsBun.entity.Dealership;
import com.AutoDealership.carsBun.service.DealershipService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/dealerships")
public class DealershipController {

    private final DealershipService dealershipService;

    public DealershipController(DealershipService dealershipService) {
        this.dealershipService = dealershipService;
    }

    @GetMapping("/dealerships")
    public String getAllDealerships(Model model) {
        List<Dealership> dealerships = dealershipService.findAllDealerships();
        model.addAttribute("dealerships", dealerships);
        return "dealerships";
    }


    @GetMapping("/add")
    public String showAddDealershipForm(Model model) {
        model.addAttribute("dealership", new Dealership());
        return "addDealership";
    }


    @PostMapping("/add")
    public String addDealership(@ModelAttribute Dealership dealership, RedirectAttributes redirectAttributes) {
        dealershipService.saveDealership(dealership);
        redirectAttributes.addFlashAttribute("successMessage", "Dealership added successfully!");
        return "redirect:/dealerships";
    }


    @GetMapping
    public String showAllDealerships(Model model,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Dealership> dealershipPage = dealershipService.getAllDealerships(pageable);

        model.addAttribute("dealerships", dealershipPage.getContent());
        model.addAttribute("dealershipPage", dealershipPage);
        model.addAttribute("currentPage", dealershipPage.getNumber());
        model.addAttribute("totalPages", dealershipPage.getTotalPages());
        model.addAttribute("pageSize", dealershipPage.getSize());

        return "dealerships";
    }


    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Dealership dealership = dealershipService.findById(id);
        if (dealership == null) {
            model.addAttribute("errorMessage", "Dealership not found.");
            return "redirect:/dealerships";
        }
        model.addAttribute("dealership", dealership);
        return "updateDealership";
    }


    @PostMapping("/update/{id}")
    public String updateDealership(@PathVariable Long id, @ModelAttribute Dealership dealership, RedirectAttributes redirectAttributes) {
        try {
            dealershipService.updateDealership(id, dealership);
            redirectAttributes.addFlashAttribute("successMessage", "Dealership updated successfully!");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Dealership not found.");
        }
        return "redirect:/dealerships";
    }


    @PostMapping("/delete/{id}")
    public String deleteDealership(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            dealershipService.deleteDealership(id);
            redirectAttributes.addFlashAttribute("successMessage", "Dealership deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting dealership.");
        }
        return "redirect:/dealerships";
    }

    @GetMapping("/grouped-by-location")
    public String getDealershipsGroupedByLocation(Model model) {
        Map<String, List<Dealership>> dealershipsByLocation = dealershipService.getDealershipsGroupedByLocation();
        model.addAttribute("dealershipsByLocation", dealershipsByLocation);
        return "groupedDealerships";
    }

}
