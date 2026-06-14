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

import com.tubes.pbo.model.Itinerary;
import com.tubes.pbo.service.ItineraryService;

@Controller
@RequestMapping("/admin/itinerary")
public class ItineraryController {

    @Autowired
    private ItineraryService itineraryService;

    // READ ALL - Tampilkan halaman list itinerary
    @GetMapping("")
    public String listItinerary(Model model) {
        model.addAttribute("listItinerary", itineraryService.getAllItinerary());
        return "admin/itinerary/itinerary";
    }

    // CREATE - Simpan itinerary baru
    @PostMapping("/save")
    public String saveItinerary(@ModelAttribute Itinerary itinerary) {
        if (itinerary.getTravelerId() == null) {
            itinerary.setTravelerId(1);
        }
        if (itinerary.getTotalHari() == null) {
            itinerary.setTotalHari(1);
        }
        if (itinerary.getTotalBiaya() == null) {
            itinerary.setTotalBiaya(0.0);
        }
        if (itinerary.getStatus() == null || itinerary.getStatus().isEmpty()) {
            itinerary.setStatus("DRAFT");
        }
        itineraryService.saveItinerary(itinerary);
        return "redirect:/admin/itinerary";
    }

    // UPDATE - Update itinerary
    @PostMapping("/update")
    public String updateItinerary(@ModelAttribute Itinerary itinerary) {
        if (itinerary.getTravelerId() == null) {
            Optional<Itinerary> existing = itineraryService.getItineraryById(itinerary.getItineraryId());
            existing.ifPresent(i -> itinerary.setTravelerId(i.getTravelerId()));
        }
        itineraryService.updateItinerary(itinerary);
        return "redirect:/admin/itinerary";
    }

    // DELETE - Hapus itinerary
    @GetMapping("/delete/{id}")
    public String deleteItinerary(@PathVariable("id") Integer id) {
        itineraryService.deleteItinerary(id);
        return "redirect:/admin/itinerary";
    }
}