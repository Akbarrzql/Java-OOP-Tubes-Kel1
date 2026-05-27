package com.tubes.pbo.controller;

import com.tubes.pbo.model.Transport;
import com.tubes.pbo.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/transport")
public class TransportController {

    @Autowired
    private TransportService transportService;

    // ==========================================
    // 1. SISI ADMIN (Membaca dari Database Asli)
    // ==========================================
    @GetMapping
    public String viewAdminPage(Model model) {
        model.addAttribute("listTransport", transportService.getAllTransport());
        model.addAttribute("newTransport", new Transport()); 
        return "transport/admin"; 
    }

    @PostMapping("/save")
    public String saveTransport(@ModelAttribute("newTransport") Transport transport) {
        transportService.saveTransport(transport);
        return "redirect:/admin/transport"; 
    }

    @GetMapping("/edit/{id}")
    @ResponseBody
    public Transport getTransportForEdit(@PathVariable("id") int id) {
        return transportService.getTransportById(id); 
    }

    @GetMapping("/delete/{id}")
    public String deleteTransport(@PathVariable("id") int id) {
        transportService.deleteTransportById(id);
        return "redirect:/admin/transport";
    }

    // =========================================================
    // 2. SISI TRAVELER (Sudah Disuntik Data Dummy Biar Kelihatan)
    // =========================================================
    @GetMapping("/traveler")
    public String viewTravelerPage(Model model) {
        // Membuat List dummy agar tampilan langsung berisi saat dites
        List<Transport> dummyTravelerData = new ArrayList<>();

        Transport t1 = new Transport();
        t1.setTransportId(101);
        t1.setJenis("Flight");
        t1.setProvider("Garuda Indonesia");
        t1.setJadwal("08:00 - 10:00");
        t1.setHarga(1500000);

        Transport t2 = new Transport();
        t2.setTransportId(102);
        t2.setJenis("Train");
        t2.setProvider("KAI Executive");
        t2.setJadwal("13:00 - 18:30");
        t2.setHarga(650000);

        Transport t3 = new Transport();
        t3.setTransportId(103);
        t3.setJenis("Bus");
        t3.setProvider("Sinar Jaya Suite");
        t3.setJadwal("20:00 - 05:00");
        t3.setHarga(450000);

        // Memasukkan semua data dummy ke list
        dummyTravelerData.add(t1);
        dummyTravelerData.add(t2);
        dummyTravelerData.add(t3);

        // Lempar data dummy ini ke HTML Traveler
        model.addAttribute("listTransport", dummyTravelerData);
        
        return "transport/traveler"; // Mengarah ke templates/transport/traveler.html
    }
}