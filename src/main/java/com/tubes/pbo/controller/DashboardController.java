package com.tubes.pbo.controller;

import com.tubes.pbo.model.Provinsi;
import com.tubes.pbo.model.Destinasi;
import com.tubes.pbo.model.Accommodation;
import com.tubes.pbo.repository.ProvinsiRepository;
import com.tubes.pbo.repository.DestinasiRepository;
import com.tubes.pbo.repository.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired
    private ProvinsiRepository provinsiRepository;

    @Autowired
    private DestinasiRepository destinasiRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        // PROVINSI
        List<Provinsi> semuaProvinsi = provinsiRepository.findAll();
        model.addAttribute("totalProvinsi", semuaProvinsi.size());
        List<Provinsi> recentProvinsi = semuaProvinsi.stream()
                .sorted((a, b) -> b.getProvinsiId().compareTo(a.getProvinsiId()))
                .limit(5)
                .collect(Collectors.toList());
        model.addAttribute("recentProvinsi", recentProvinsi);

        // DESTINASI
        List<Destinasi> semuaDestinasi = destinasiRepository.findAll();
        model.addAttribute("totalDestinasi", semuaDestinasi.size());
        List<Destinasi> recentDestinasi = semuaDestinasi.stream()
                .sorted((a, b) -> b.getDestinasiId().compareTo(a.getDestinasiId()))
                .limit(5)
                .collect(Collectors.toList());
        model.addAttribute("recentDestinasi", recentDestinasi);

        // AKOMODASI
        List<Accommodation> semuaAkomodasi = accommodationRepository.findAll();
        model.addAttribute("totalAkomodasi", semuaAkomodasi.size());
        List<Accommodation> recentAkomodasi = semuaAkomodasi.stream()
                .sorted((a, b) -> b.getAccommodationId().compareTo(a.getAccommodationId()))
                .limit(5)
                .collect(Collectors.toList());
        model.addAttribute("recentAkomodasi", recentAkomodasi);

        return "admin/dashboard-admin";
    }
}