package com.tubes.pbo.controller;

import com.tubes.pbo.model.Transport;
import com.tubes.pbo.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/transport")
public class TransportController {

    @Autowired
    private TransportService transportService;

    // READ ALL - Tampilkan halaman list transport
    @GetMapping("")
    public String listTransport(Model model) {
        model.addAttribute("listTransport", transportService.getAllTransport());
        return "admin/transport/transport";
    }

    // CREATE - Simpan transport baru
    @PostMapping("/save")
    public String saveTransport(@ModelAttribute Transport transport) {
        // Set default provinsi_id = 1 jika tidak ada
        if (transport.getProvinsiId() == null) {
            transport.setProvinsiId(1);
        }
        transportService.saveTransport(transport);
        return "redirect:/admin/transport";
    }

    // UPDATE - Update transport
    @PostMapping("/update")
    public String updateTransport(@ModelAttribute Transport transport) {
        // Pastikan provinsi_id tidak null
        if (transport.getProvinsiId() == null) {
            Optional<Transport> existing = transportService.getTransportById(transport.getTransportId());
            existing.ifPresent(t -> transport.setProvinsiId(t.getProvinsiId()));
        }
        transportService.updateTransport(transport);
        return "redirect:/admin/transport";
    }

    // DELETE - Hapus transport
    @GetMapping("/delete/{id}")
    public String deleteTransport(@PathVariable("id") Integer id) {
        transportService.deleteTransport(id);
        return "redirect:/admin/transport";
    }
}