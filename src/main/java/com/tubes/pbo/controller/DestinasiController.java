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

import com.tubes.pbo.model.Destinasi;
import com.tubes.pbo.service.DestinasiService;

@Controller
@RequestMapping("/admin/destinasi")
public class DestinasiController {

    @Autowired
    private DestinasiService destinasiService;

    // READ ALL - Tampilkan halaman list destinasi
    @GetMapping("")
    public String listDestinasi(Model model) {
        model.addAttribute("listDestinasi", destinasiService.getAllDestinasi());
        return "admin/destinasi/destinasi";
    }

    // CREATE - Simpan destinasi baru
    @PostMapping("/save")
    public String saveDestinasi(@ModelAttribute Destinasi destinasi) {
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
        return "redirect:/admin/destinasi";
    }

    // UPDATE - Update destinasi
    @PostMapping("/update")
    public String updateDestinasi(@ModelAttribute Destinasi destinasi) {
        if (destinasi.getProvinsiId() == null) {
            Optional<Destinasi> existing = destinasiService.getDestinasiById(destinasi.getDestinasiId());
            existing.ifPresent(d -> destinasi.setProvinsiId(d.getProvinsiId()));
        }
        destinasiService.updateDestinasi(destinasi);
        return "redirect:/admin/destinasi";
    }

    // DELETE - Hapus destinasi
    @GetMapping("/delete/{id}")
    public String deleteDestinasi(@PathVariable("id") Integer id) {
        destinasiService.deleteDestinasi(id);
        return "redirect:/admin/destinasi";
    }
}