package com.tubes.pbo.controller;

import com.tubes.pbo.model.Provinsi;
import com.tubes.pbo.repository.ProvinsiRepository;
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

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        List<Provinsi> semua = provinsiRepository.findAll();
        model.addAttribute("totalProvinsi", semua.size());
        List<Provinsi> recent = semua.stream()
                .sorted((a, b) -> b.getProvinsiId().compareTo(a.getProvinsiId()))
                .limit(5)
                .collect(Collectors.toList());
        model.addAttribute("recentProvinsi", recent);
        return "admin/dashboard-admin";
    }
}