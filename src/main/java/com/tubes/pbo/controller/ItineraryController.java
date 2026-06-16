package com.tubes.pbo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tubes.pbo.model.Itinerary;
import com.tubes.pbo.model.ItineraryDay;
import com.tubes.pbo.model.ItineraryDayDestinasi;
import com.tubes.pbo.model.ItineraryDayAccommodation;
import com.tubes.pbo.model.ItineraryDayTransport;
import com.tubes.pbo.model.Destinasi;
import com.tubes.pbo.model.Accommodation;
import com.tubes.pbo.model.Transport;
import com.tubes.pbo.model.Provinsi;
import com.tubes.pbo.dto.GenerateItineraryRequest;
import com.tubes.pbo.service.ItineraryService;
import com.tubes.pbo.repository.ItineraryDayRepository;
import com.tubes.pbo.repository.ItineraryDayDestinasiRepository;
import com.tubes.pbo.repository.ItineraryDayAccommodationRepository;
import com.tubes.pbo.repository.ItineraryDayTransportRepository;
import com.tubes.pbo.repository.DestinasiRepository;
import com.tubes.pbo.repository.AccommodationRepository;
import com.tubes.pbo.repository.TransportRepository;
import com.tubes.pbo.repository.ProvinsiRepository;

@Controller
@RequestMapping("/itinerary")
public class ItineraryController {

    @Autowired
    private ItineraryService itineraryService;

    @Autowired
    private ItineraryDayRepository itineraryDayRepository;

    @Autowired
    private ItineraryDayDestinasiRepository itineraryDayDestinasiRepository;

    @Autowired
    private ItineraryDayAccommodationRepository itineraryDayAccommodationRepository;

    @Autowired
    private ItineraryDayTransportRepository itineraryDayTransportRepository;

    @Autowired
    private DestinasiRepository destinasiRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private ProvinsiRepository provinsiRepository;

    // Generate Itinerary dari form home
    @PostMapping("/generate")
    public String generateItinerary(
            @RequestParam(value = "destination", required = false) String destination,
            @RequestParam(value = "province", required = false) Integer provinceId,
            @RequestParam(value = "days", required = false, defaultValue = "3") Integer totalDays,
            @RequestParam(value = "travelers", required = false) Integer travelers,
            @RequestParam(value = "travelDate", required = false) String travelDate) {

        try {
            // Ambil dari session atau default ke traveler_id = 1
            Integer travelerId = 1;

            // Resolve province secara exact match agar tidak salah mengarah ke province lain
            if (provinceId == null && destination != null && !destination.isBlank()) {
                String keyword = destination.trim();
                provinceId = provinsiRepository.findAll().stream()
                        .filter(p -> p.getNama() != null && p.getNama().equalsIgnoreCase(keyword))
                        .map(Provinsi::getProvinsiId)
                        .findFirst()
                        .orElse(null);
            }

            if (provinceId == null) {
                return "redirect:/?error=Silakan pilih province dari dropdown autocomplete";
            }

            GenerateItineraryRequest request = new GenerateItineraryRequest();
            request.setProvinceId(provinceId);
            request.setTotalDays(totalDays);
            request.setNumberOfTravelers(travelers);
            request.setTravelDate(travelDate);

            // Ambil nama province untuk title
            Optional<Provinsi> provinsi = provinsiRepository.findById(provinceId);
            if (provinsi.isPresent()) {
                request.setTitle("Trip to " + provinsi.get().getNama());
            } else {
                request.setTitle("New Adventure");
            }


            Itinerary itinerary = itineraryService.generateItinerary(request, travelerId);

            return "redirect:/itinerary/" + itinerary.getItineraryId();
        } catch (Exception e) {
            return "redirect:/?error=" + e.getMessage();
        }
    }

    // Detail Itinerary - baca dari database
    @GetMapping("/{id}")
    public String detailItinerary(@PathVariable("id") Integer id, Model model) {
        Optional<Itinerary> itinerary = itineraryService.getItineraryById(id);

        if (itinerary.isEmpty()) {
            return "redirect:/dashboard?error=Itinerary not found";
        }

        Itinerary itin = itinerary.get();
        List<ItineraryDay> itineraryDays = itineraryDayRepository.findByItineraryId(id);

        model.addAttribute("itinerary", itin);
        model.addAttribute("itineraryDays", itineraryDays);

        // Untuk setiap hari, ambil destinasi, accommodation, transport
        for (ItineraryDay day : itineraryDays) {
            List<ItineraryDayDestinasi> destinasiList = itineraryDayDestinasiRepository.findByDayId(day.getDayId());
            List<ItineraryDayAccommodation> accommodationList = itineraryDayAccommodationRepository.findByDayId(day.getDayId());
            List<ItineraryDayTransport> transportList = itineraryDayTransportRepository.findByDayId(day.getDayId());

            // Mapping destinasi dengan nama dan detail
            for (ItineraryDayDestinasi idd : destinasiList) {
                Optional<Destinasi> dest = destinasiRepository.findById(idd.getDestinasiId());
                dest.ifPresent(idd::setDestinasiObj);
            }

            // Mapping accommodation dengan nama dan detail
            for (ItineraryDayAccommodation ida : accommodationList) {
                Optional<Accommodation> acc = accommodationRepository.findById(ida.getAccommodationId());
                acc.ifPresent(ida::setAccommodationObj);
            }

            // Mapping transport dengan nama dan detail
            for (ItineraryDayTransport idt : transportList) {
                Optional<Transport> trans = transportRepository.findById(idt.getTransportId());
                trans.ifPresent(idt::setTransportObj);
            }

            day.setDestinasiList(destinasiList);
            day.setAccommodationList(accommodationList);
            day.setTransportList(transportList);
        }

        return "traveler/itinerary/detail-itinerary";
    }

    // LIST Itinerary traveler
    @GetMapping("/list")
    public String listItinerary(Model model) {
        // Get user from session
        Integer travelerId = 1; // Placeholder - ambil dari session nanti
        List<Itinerary> itineraries = itineraryService.getItineraryByTravelerId(travelerId);
        model.addAttribute("itineraries", itineraries);
        return "traveler/itinerary/list";
    }

    // DELETE Itinerary
    @GetMapping("/delete/{id}")
    public String deleteItinerary(@PathVariable("id") Integer id) {
        itineraryService.deleteItinerary(id);
        return "redirect:/itinerary/list";
    }

    // Admin endpoints
    @GetMapping("/admin")
    public String listItineraryAdmin(Model model) {
        model.addAttribute("listItinerary", itineraryService.getAllItinerary());
        return "admin/dashboard-admin";
    }

    @PostMapping("/save")
    public String saveItinerary(@ModelAttribute Itinerary itinerary) {
        if (itinerary.getTravelerId() == null) {
            itinerary.setTravelerId(1);
        }
        if (itinerary.getTotalHari() == null) {
            itinerary.setTotalHari(1);
        }
        if (itinerary.getTotalBiaya() == null) {
            itinerary.setTotalBiaya(0.0);
        }
        if (itinerary.getStatus() == null || itinerary.getStatus().isEmpty()) {
            itinerary.setStatus("Itinerary");
        }
        itineraryService.saveItinerary(itinerary);
        return "redirect:/itinerary/admin";
    }

    @PostMapping("/update")
    public String updateItinerary(@ModelAttribute Itinerary itinerary) {
        if (itinerary.getTravelerId() == null) {
            Optional<Itinerary> existing = itineraryService.getItineraryById(itinerary.getItineraryId());
            existing.ifPresent(i -> itinerary.setTravelerId(i.getTravelerId()));
        }
        itineraryService.updateItinerary(itinerary);
        return "redirect:/itinerary/admin";
    }
}