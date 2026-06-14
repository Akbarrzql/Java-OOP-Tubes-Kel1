package com.tubes.pbo.controller;

import com.tubes.pbo.model.Provinsi;
import com.tubes.pbo.service.ProvinsiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/province")
public class ProvinsiController {

    @Autowired
    private ProvinsiService provinsiService;

    @GetMapping
    public String adminProvince(Model model) {
        model.addAttribute("provinsiList", provinsiService.findAll());
        model.addAttribute("totalProvince", provinsiService.countDistinctNama());
        model.addAttribute("totalDestination", provinsiService.findAll().size());
        model.addAttribute("mostPopular", provinsiService.getMostPopular());
        return "admin/province/province";
    }

    @PostMapping("/add")
    public String createProvinsi(@ModelAttribute Provinsi provinsi,
            RedirectAttributes redirectAttributes) {
        provinsiService.save(provinsi);
        redirectAttributes.addFlashAttribute("success", "Provinsi berhasil ditambahkan!");
        return "redirect:/admin/province";
    }

    @PostMapping("/edit/{id}")
    public String updateProvinsi(@PathVariable Integer id,
            @ModelAttribute Provinsi provinsi,
            RedirectAttributes redirectAttributes) {
        provinsiService.update(id, provinsi);
        redirectAttributes.addFlashAttribute("success", "Provinsi berhasil diupdate!");
        return "redirect:/admin/province";
    }

    @GetMapping("/delete/{id}")
    public String deleteProvinsi(@PathVariable Integer id,
            RedirectAttributes redirectAttributes) {
        provinsiService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Provinsi berhasil dihapus!");
        return "redirect:/admin/province";
    }
}