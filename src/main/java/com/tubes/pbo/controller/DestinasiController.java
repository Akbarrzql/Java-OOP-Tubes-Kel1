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

import com.tubes.pbo.model.Destinasi;
import com.tubes.pbo.service.DestinasiService;

@Controller
@RequestMapping("/admin/destination")
public class DestinasiController {

    @Autowired
    private DestinasiService destinasiService;

    @GetMapping("")
    public String listDestinasi(Model model) {
        model.addAttribute("listDestinasi", destinasiService.getAllDestinasi());
        return "admin/destination/destination";
    }

    @PostMapping("/save")
    public String saveDestinasi(@ModelAttribute Destinasi destinasi,
            RedirectAttributes redirectAttributes) {
        try {
            if (destinasi.getProvinsiId() == null) {
                destinasi.setProvinsiId(1);
            }
            if (destinasi.getRating() == null) {
                destinasi.setRating(0.0);
            }
            if (destinasi.getHarga() == null) {
                destinasi.setHarga(0.0);
            }
            destinasiService.saveDestinasi(destinasi);
            redirectAttributes.addFlashAttribute("success", "Destinasi berhasil ditambahkan!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Gagal menambahkan destinasi: " + e.getMessage());
        }
        return "redirect:/admin/destination";
    }

    @PostMapping("/update")
    public String updateDestinasi(@ModelAttribute Destinasi destinasi,
            RedirectAttributes redirectAttributes) {
        try {
            if (destinasi.getProvinsiId() == null) {
                Optional<Destinasi> existing = destinasiService.getDestinasiById(destinasi.getDestinasiId());
                existing.ifPresent(d -> destinasi.setProvinsiId(d.getProvinsiId()));
            }
            destinasiService.updateDestinasi(destinasi);
            redirectAttributes.addFlashAttribute("success", "Destinasi berhasil diupdate!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Gagal mengupdate destinasi: " + e.getMessage());
        }
        return "redirect:/admin/destination";
    }

    @GetMapping("/delete/{id}")
    public String deleteDestinasi(@PathVariable("id") Integer id,
            RedirectAttributes redirectAttributes) {
        try {
            destinasiService.deleteDestinasi(id);
            redirectAttributes.addFlashAttribute("success", "Destinasi berhasil dihapus!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Gagal menghapus destinasi: " + e.getMessage());
        }
        return "redirect:/admin/destination";
    }
}