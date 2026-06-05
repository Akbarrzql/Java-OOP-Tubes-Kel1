package com.tubes.pbo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    /*
     * =========================
     * PUBLIC PAGE
     * =========================
     */

    @GetMapping("/")
    public String home() {
        return "home/index";
    }

    /*
     * =========================
     * AUTH PAGE
     * =========================
     */

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @GetMapping("/register")
    public String register() {
        return "register/register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgotpassword/forgot-password";
    }

    /*
     * =========================
     * DASHBOARD
     * =========================
     */

    @GetMapping("/dashboard")
    public String dashboard() {
        return "traveler/dashboard/dashboard";
    }

    /*
     * =========================
     * ITINERARY
     * =========================
     */

    @GetMapping("/itinerary/list")
    public String itineraryList() {
        return "traveler/itinerary/list";
    }

    /*
     * =========================
     * EXPLORE
     * =========================
     */

    @GetMapping("/explore")
    public String explore() {
        return "traveler/explore/index";
    }

    /*
     * =========================
     * admin
     * =========================
     */

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard-admin";
    }

    @GetMapping("/admin/province")
    public String provinceManagement() {
        return "admin/province-management";
    }

    @GetMapping("/admin/province/add")
    public String addProvince() {
        return "admin/add-province";
    }

    @GetMapping("/admin/province/edit")
    public String editProvince() {
        return "admin/edit-province";
    }
}