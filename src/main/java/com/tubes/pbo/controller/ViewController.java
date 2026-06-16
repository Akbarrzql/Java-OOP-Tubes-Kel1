package com.tubes.pbo.controller;

import com.tubes.pbo.repository.ProvinsiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class ViewController {

    @Autowired
    private ProvinsiRepository provinsiRepository;

    /*
     * =========================
     * PUBLIC PAGE
     * =========================
     */

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("provinsiList", provinsiRepository.findAll());
        return "home/index";
    }

    /*
     * =========================
     * AUTH PAGE
     * =========================
     */

    @GetMapping("/login")
    public String login(HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if ("ADMIN".equals(role)) {
            return "redirect:/admin/dashboard";
        }
        if ("TRAVELER".equals(role)) {
            return "redirect:/dashboard";
        }
        return "login/login";
    }

    @GetMapping("/register")
    public String register(HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if ("ADMIN".equals(role)) {
            return "redirect:/admin/dashboard";
        }
        if ("TRAVELER".equals(role)) {
            return "redirect:/dashboard";
        }
        return "register/register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgotpassword/forgot-password";
    }

    /*
     * =========================
     * TRAVELER PAGE
     * =========================
     */

    @GetMapping("/explore")
    public String explore() {
        return "traveler/explore/index";
    }


    // @GetMapping("/profile")
    // public String profile() {
    //     return "traveler/profile/profile";
    // }

    /*
     * =========================
     * ADMIN PAGE
     * =========================
     */

    // @GetMapping("/admin/dashboard")
    // public String adminDashboard() {
    //     return "admin/dashboard-admin";
    // }

    // @GetMapping("/admin/transport")
    // public String adminTransport() {
    //     return "admin/transport/transport";
    // }

    @GetMapping("/admin/profile")
    public String adminProfile() {
        return "admin/profile/profile";
    }

    @GetMapping("/admin/settings")
    public String adminSettings() {
        return "admin/settings/settings";
    }

    // @GetMapping("/admin/destination")
    // public String adminDestination() {
    //     return "admin/destination/destination";
    // }

    // @GetMapping("/admin/accommodation")
    // public String adminAccommodation() {
    //     return "admin/accommodation/accommodation";
    // }    
//    @GetMapping("/admin/itinerary")
//     public String adminItinerary() {
//         return "admin/itinerary/itinerary";
//     }
}