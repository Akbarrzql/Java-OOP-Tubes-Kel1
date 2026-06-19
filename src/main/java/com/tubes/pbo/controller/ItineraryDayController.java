    package com.tubes.pbo.controller;

    import com.tubes.pbo.model.*;
    import com.tubes.pbo.repository.*;
    import com.tubes.pbo.service.ItineraryDayService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;
    import jakarta.servlet.http.HttpSession;
    import java.time.LocalDate;
    import java.util.List;

    @Controller
    @RequestMapping("/itinerary")
    public class ItineraryDayController {

        @Autowired
        private ItineraryDayService itineraryDayService;

        @Autowired
        private ItineraryRepository itineraryRepository;

        @Autowired
        private TransportRepository transportRepository;

        @Autowired
        private AccommodationRepository accommodationRepository;

        @Autowired
        private DestinasiRepository destinasiRepository;

        @Autowired
        private UserRepository userRepository;

        

        // ===== GET: Halaman Detail Itinerary =====
        @GetMapping("/{id}")
        public String detailItinerary(@PathVariable Integer id, Model model, HttpSession session) {
            Itinerary itinerary = itineraryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerary tidak ditemukan"));

            List<ItineraryDay> days = itineraryDayService.getDaysByItineraryId(id);

            // Hitung ringkasan biaya
            double totalAkomodasi = days.stream()
                .flatMap(d -> d.getAccommodations().stream())
                .mapToDouble(a -> a.getBiaya() != null ? a.getBiaya() : 0.0)
                .sum();

            double totalTransport = days.stream()
                .flatMap(d -> d.getTransports().stream())
                .mapToDouble(t -> t.getBiaya() != null ? t.getBiaya() : 0.0)
                .sum();

            double totalAktivitas = days.stream()
                .flatMap(d -> d.getDestinasiList().stream())
                .mapToDouble(dest -> dest.getBiaya() != null ? dest.getBiaya() : 0.0)
                .sum();

            // Data untuk dropdown tambah aktivitas
            List<Transport> allTransport = transportRepository.findAll();
            List<Accommodation> allAccommodation = accommodationRepository.findAll();
            List<Destinasi> allDestinasi = destinasiRepository.findAll();

            model.addAttribute("itinerary", itinerary);
            model.addAttribute("days", days);
            model.addAttribute("totalAkomodasi", totalAkomodasi);
            model.addAttribute("totalTransport", totalTransport);
            model.addAttribute("totalAktivitas", totalAktivitas);
            model.addAttribute("allTransport", allTransport);
            model.addAttribute("allAccommodation", allAccommodation);
            model.addAttribute("allDestinasi", allDestinasi);

            Integer userId = (Integer) session.getAttribute("userId");
            if (userId != null) {
                User user = userRepository.findById(userId).orElse(null);
                if (user != null) {
                    model.addAttribute("user", user);
                    model.addAttribute("avatarInitial", user.getName() != null && !user.getName().isEmpty() 
                        ? user.getName().substring(0, 1).toUpperCase() 
                        : "U");
                }
        }
        
            return "traveler/itinerary/detail";
        }

        // ===== POST: Tambah Hari =====
        @PostMapping("/{id}/day/add")
        public String addDay(@PathVariable Integer id,
                            @RequestParam String judul,
                            @RequestParam(required = false) String tanggal,  // <-- TAMBAH INI
                            RedirectAttributes ra) {
            try {
                java.time.LocalDate tgl = null;
                if (tanggal != null && !tanggal.isEmpty()) {
                    tgl = java.time.LocalDate.parse(tanggal);
                }
                itineraryDayService.addDay(id, judul, tgl);  // <-- UPDATE INI
                ra.addFlashAttribute("success", "Hari berhasil ditambahkan!");
            } catch (Exception e) {
                ra.addFlashAttribute("error", "Gagal menambahkan hari: " + e.getMessage());
            }
            return "redirect:/itinerary/" + id;
        }

        // ===== POST: Hapus Hari =====
        @PostMapping("/{id}/day/{dayId}/delete")
        public String deleteDay(@PathVariable Integer id,
                                @PathVariable Integer dayId,
                                RedirectAttributes ra) {
            try {
                itineraryDayService.deleteDay(dayId);
                ra.addFlashAttribute("success", "Hari berhasil dihapus!");
            } catch (Exception e) {
                ra.addFlashAttribute("error", "Gagal menghapus hari: " + e.getMessage());
            }
            return "redirect:/itinerary/" + id;
        }

        // ===== POST: Tambah Transport =====
        @PostMapping("/{id}/day/{dayId}/transport/add")
        public String addTransport(@PathVariable Integer id,
                                @PathVariable Integer dayId,
                                @RequestParam Integer transportId,
                                @RequestParam(required = false) String waktuBerangkat,
                                @RequestParam(required = false) String waktuTiba,
                                @RequestParam Double biaya,
                                @RequestParam(required = false) String catatan,
                                RedirectAttributes ra) {
            try {
                Transport transport = transportRepository.findById(transportId)
                    .orElseThrow(() -> new RuntimeException("Transport tidak ditemukan"));

                ItineraryDayTransport idt = new ItineraryDayTransport();
                idt.setTransport(transport);
                idt.setBiaya(biaya);
                idt.setCatatan(catatan);

                if (waktuBerangkat != null && !waktuBerangkat.isEmpty()) {
                    idt.setWaktuBerangkat(java.time.LocalDateTime.parse(waktuBerangkat));
                }
                if (waktuTiba != null && !waktuTiba.isEmpty()) {
                    idt.setWaktuTiba(java.time.LocalDateTime.parse(waktuTiba));
                }

                itineraryDayService.addTransport(dayId, idt);
                ra.addFlashAttribute("success", "Transport berhasil ditambahkan!");
            } catch (Exception e) {
                ra.addFlashAttribute("error", "Gagal menambahkan transport: " + e.getMessage());
            }
            return "redirect:/itinerary/" + id;
        }

        // ===== POST: Edit Transport =====
    @PostMapping("/{id}/day/{dayId}/transport/{transportEntryId}/edit")
    public String editTransport(@PathVariable Integer id,
                            @PathVariable Integer dayId,
                            @PathVariable Integer transportEntryId,
                            @RequestParam Integer transportId,
                            @RequestParam(required = false) String waktuBerangkat,
                            @RequestParam(required = false) String waktuTiba,
                            @RequestParam Double biaya,
                            @RequestParam(required = false) String catatan,
                            RedirectAttributes ra) {
        try {
            Transport transport = transportRepository.findById(transportId)
                .orElseThrow(() -> new RuntimeException("Transport tidak ditemukan"));

            ItineraryDayTransport idt = new ItineraryDayTransport();
            idt.setTransport(transport);
            idt.setBiaya(biaya);
            idt.setCatatan(catatan);

            if (waktuBerangkat != null && !waktuBerangkat.isEmpty()) {
                idt.setWaktuBerangkat(java.time.LocalDateTime.parse(waktuBerangkat));
            }
            if (waktuTiba != null && !waktuTiba.isEmpty()) {
                idt.setWaktuTiba(java.time.LocalDateTime.parse(waktuTiba));
            }

            itineraryDayService.updateTransport(transportEntryId, idt);
            ra.addFlashAttribute("success", "Transport berhasil diupdate!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Gagal mengupdate transport: " + e.getMessage());
        }
        return "redirect:/itinerary/" + id;
    }

        // ===== POST: Hapus Transport =====
        @PostMapping("/{id}/day/{dayId}/transport/{transportId}/delete")
        public String deleteTransport(@PathVariable Integer id,
                                    @PathVariable Integer dayId,
                                    @PathVariable Integer transportId,
                                    RedirectAttributes ra) {
            try {
                itineraryDayService.deleteTransport(transportId);
                ra.addFlashAttribute("success", "Transport berhasil dihapus!");
            } catch (Exception e) {
                ra.addFlashAttribute("error", "Gagal menghapus transport: " + e.getMessage());
            }
            return "redirect:/itinerary/" + id;
        }


        // ===== POST: Tambah Accommodation =====
        @PostMapping("/{id}/day/{dayId}/accommodation/add")
        public String addAccommodation(@PathVariable Integer id,
                                    @PathVariable Integer dayId,
                                    @RequestParam Integer accommodationId,
                                    @RequestParam(required = false) String checkinTime,
                                    @RequestParam(required = false) String checkoutTime,
                                    @RequestParam Integer malam,
                                    @RequestParam Double biaya,
                                    @RequestParam(required = false) String catatan,
                                    RedirectAttributes ra) {
            try {
                Accommodation accommodation = accommodationRepository.findById(accommodationId)
                    .orElseThrow(() -> new RuntimeException("Accommodation tidak ditemukan"));

                ItineraryDayAccommodation ida = new ItineraryDayAccommodation();
                ida.setAccommodation(accommodation);
                ida.setMalam(malam);
                ida.setBiaya(biaya);
                ida.setCatatan(catatan);

                if (checkinTime != null && !checkinTime.isEmpty()) {
                    ida.setCheckinTime(java.time.LocalDateTime.parse(checkinTime));
                }
                if (checkoutTime != null && !checkoutTime.isEmpty()) {
                    ida.setCheckoutTime(java.time.LocalDateTime.parse(checkoutTime));
                }

                itineraryDayService.addAccommodation(dayId, ida);
                ra.addFlashAttribute("success", "Akomodasi berhasil ditambahkan!");
            } catch (Exception e) {
                ra.addFlashAttribute("error", "Gagal menambahkan akomodasi: " + e.getMessage());
            }
            return "redirect:/itinerary/" + id;
        }
        // ===== POST: Edit Accommodation =====
        @PostMapping("/{id}/day/{dayId}/accommodation/{accEntryId}/edit")
        public String editAccommodation(@PathVariable Integer id,
                                    @PathVariable Integer dayId,
                                    @PathVariable Integer accEntryId,
                                    @RequestParam Integer accommodationId,
                                    @RequestParam(required = false) String checkinTime,
                                    @RequestParam(required = false) String checkoutTime,
                                    @RequestParam(required = false) Integer malam,
                                    @RequestParam Double biaya,
                                    @RequestParam(required = false) String catatan,
                                    RedirectAttributes ra) {
            try {
                Accommodation accommodation = accommodationRepository.findById(accommodationId)
                    .orElseThrow(() -> new RuntimeException("Accommodation tidak ditemukan"));

                ItineraryDayAccommodation ida = new ItineraryDayAccommodation();
                ida.setAccommodation(accommodation);
                ida.setMalam(malam);
                ida.setBiaya(biaya);
                ida.setCatatan(catatan);

                if (checkinTime != null && !checkinTime.isEmpty()) {
                    ida.setCheckinTime(java.time.LocalDateTime.parse(checkinTime));
                }
                if (checkoutTime != null && !checkoutTime.isEmpty()) {
                    ida.setCheckoutTime(java.time.LocalDateTime.parse(checkoutTime));
                }

                itineraryDayService.updateAccommodation(accEntryId, ida);
                ra.addFlashAttribute("success", "Akomodasi berhasil diupdate!");
            } catch (Exception e) {
                ra.addFlashAttribute("error", "Gagal mengupdate akomodasi: " + e.getMessage());
            }
            return "redirect:/itinerary/" + id;
        }
        // ===== POST: Hapus Accommodation =====
        @PostMapping("/{id}/day/{dayId}/accommodation/{accId}/delete")
        public String deleteAccommodation(@PathVariable Integer id,
                                        @PathVariable Integer dayId,
                                        @PathVariable Integer accId,
                                        RedirectAttributes ra) {
            try {
                itineraryDayService.deleteAccommodation(accId);
                ra.addFlashAttribute("success", "Akomodasi berhasil dihapus!");
            } catch (Exception e) {
                ra.addFlashAttribute("error", "Gagal menghapus akomodasi: " + e.getMessage());
            }
            return "redirect:/itinerary/" + id;
        }


        // ===== POST: Tambah Destinasi =====
        @PostMapping("/{id}/day/{dayId}/destinasi/add")
        public String addDestinasi(@PathVariable Integer id,
                                @PathVariable Integer dayId,
                                @RequestParam Integer destinasiId,
                                @RequestParam(required = false) Integer durasiMenit,
                                @RequestParam Double biaya,
                                @RequestParam(required = false) String catatan,
                                @RequestParam(required = false) String waktu,
                                RedirectAttributes ra) {
            try {
                Destinasi destinasi = destinasiRepository.findById(destinasiId)
                    .orElseThrow(() -> new RuntimeException("Destinasi tidak ditemukan"));
                
                ItineraryDayDestinasi idd = new ItineraryDayDestinasi();
                idd.setDestinasi(destinasi);
                idd.setDurasiMenit(durasiMenit);
                idd.setBiaya(biaya);
                idd.setCatatan(catatan);

                if (waktu != null && !waktu.isEmpty()) {
                idd.setWaktu(java.time.LocalTime.parse(waktu));
                }

                itineraryDayService.addDestinasi(dayId, idd);
                ra.addFlashAttribute("success", "Destinasi berhasil ditambahkan!");
            } catch (Exception e) {
                ra.addFlashAttribute("error", "Gagal menambahkan destinasi: " + e.getMessage());
            }
            return "redirect:/itinerary/" + id;
        }

        // ===== POST: Edit Destinasi =====
        @PostMapping("/{id}/day/{dayId}/destinasi/{destEntryId}/edit")
        public String updateDestinasi(@PathVariable Integer id,
                                @PathVariable Integer dayId,
                                @PathVariable Integer destEntryId,
                                @RequestParam Integer destinasiId,
                                @RequestParam(required = false) Integer durasiMenit,
                                @RequestParam Double biaya,
                                @RequestParam(required = false) String catatan,
                                @RequestParam(required = false) String waktu,
                                RedirectAttributes ra) {
            try {
                Destinasi destinasi = destinasiRepository.findById(destinasiId)
                    .orElseThrow(() -> new RuntimeException("Destinasi tidak ditemukan"));

                ItineraryDayDestinasi idd = new ItineraryDayDestinasi();
                idd.setDestinasi(destinasi);
                idd.setDurasiMenit(durasiMenit);
                idd.setBiaya(biaya);
                idd.setCatatan(catatan);

                if (waktu != null && !waktu.isEmpty()) {
                idd.setWaktu(java.time.LocalTime.parse(waktu));
            }
                itineraryDayService.updateDestinasi(destEntryId, idd);
                ra.addFlashAttribute("success", "Destinasi berhasil diupdate!");
            } catch (Exception e) {
                ra.addFlashAttribute("error", "Gagal mengupdate destinasi: " + e.getMessage());
            }
            return "redirect:/itinerary/" + id;
        }
        // ===== POST: Hapus Destinasi =====
        @PostMapping("/{id}/day/{dayId}/destinasi/{destId}/delete")
        public String deleteDestinasi(@PathVariable Integer id,
                                    @PathVariable Integer dayId,
                                    @PathVariable Integer destId,
                                    RedirectAttributes ra) {
            try {
                itineraryDayService.deleteDestinasi(destId);
                ra.addFlashAttribute("success", "Destinasi berhasil dihapus!");
            } catch (Exception e) {
                ra.addFlashAttribute("error", "Gagal menghapus destinasi: " + e.getMessage());
            }
            return "redirect:/itinerary/" + id;
        }
    }