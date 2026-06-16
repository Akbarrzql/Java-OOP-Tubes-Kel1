package com.tubes.pbo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tubes.pbo.model.Accommodation;
import com.tubes.pbo.service.AccommodationService;

@Controller
@RequestMapping("/admin/accommodation")
public class AccommodationController {

    @Autowired
    private AccommodationService accommodationService;

    @GetMapping("")
    public String listAccommodation(Model model) {
        model.addAttribute("listAccommodation", accommodationService.getAllAccommodation());
        return "admin/accommodation/accommodation";
    }

    @PostMapping("/save")
    public String saveAccommodation(@ModelAttribute Accommodation accommodation,
                                    RedirectAttributes redirectAttributes) {
        try {
            if (accommodation.getProvinsiId() == null) {
                accommodation.setProvinsiId(1);
            }
            if (accommodation.getRating() == null) {
                accommodation.setRating(0.0);
            }
            if (accommodation.getHargaPerMalam() == null) {
                accommodation.setHargaPerMalam(0.0);
            }
            accommodationService.saveAccommodation(accommodation);
            redirectAttributes.addFlashAttribute("success", "Akomodasi berhasil ditambahkan!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Gagal menambahkan akomodasi: " + e.getMessage());
        }
        return "redirect:/admin/accommodation";
    }

    @PostMapping("/update")
    public String updateAccommodation(@ModelAttribute Accommodation accommodation,
                                      RedirectAttributes redirectAttributes) {
        try {
            if (accommodation.getProvinsiId() == null) {
                Optional<Accommodation> existing = accommodationService.getAccommodationById(accommodation.getAccommodationId());
                existing.ifPresent(a -> accommodation.setProvinsiId(a.getProvinsiId()));
            }
            accommodationService.updateAccommodation(accommodation);
            redirectAttributes.addFlashAttribute("success", "Akomodasi berhasil diupdate!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Gagal mengupdate akomodasi: " + e.getMessage());
        }
        return "redirect:/admin/accommodation";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccommodation(@PathVariable("id") Integer id,
                                      RedirectAttributes redirectAttributes) {
        try {
            accommodationService.deleteAccommodation(id);
            redirectAttributes.addFlashAttribute("success", "Akomodasi berhasil dihapus!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Gagal menghapus akomodasi: " + e.getMessage());
        }
        return "redirect:/admin/accommodation";
    }
}