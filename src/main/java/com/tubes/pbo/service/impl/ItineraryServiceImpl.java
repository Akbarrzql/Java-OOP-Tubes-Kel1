package com.tubes.pbo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.pbo.model.Itinerary;
import com.tubes.pbo.model.Destinasi;
import com.tubes.pbo.model.Accommodation;
import com.tubes.pbo.model.Transport;
import com.tubes.pbo.model.ItineraryDay;
import com.tubes.pbo.model.ItineraryDayDestinasi;
import com.tubes.pbo.model.ItineraryDayAccommodation;
import com.tubes.pbo.model.ItineraryDayTransport;
import com.tubes.pbo.dto.GenerateItineraryRequest;
import com.tubes.pbo.repository.ItineraryRepository;
import com.tubes.pbo.repository.DestinasiRepository;
import com.tubes.pbo.repository.AccommodationRepository;
import com.tubes.pbo.repository.TransportRepository;
import com.tubes.pbo.repository.ItineraryDayRepository;
import com.tubes.pbo.repository.ItineraryDayDestinasiRepository;
import com.tubes.pbo.repository.ItineraryDayAccommodationRepository;
import com.tubes.pbo.repository.ItineraryDayTransportRepository;
import com.tubes.pbo.service.ItineraryService;

@Service
public class ItineraryServiceImpl implements ItineraryService {

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private DestinasiRepository destinasiRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private ItineraryDayRepository itineraryDayRepository;

    @Autowired
    private ItineraryDayDestinasiRepository itineraryDayDestinasiRepository;

    @Autowired
    private ItineraryDayAccommodationRepository itineraryDayAccommodationRepository;

    @Autowired
    private ItineraryDayTransportRepository itineraryDayTransportRepository;

    @Override
    public List<Itinerary> getAllItinerary() {
        return itineraryRepository.findAll();
    }

    @Override
    public Optional<Itinerary> getItineraryById(Integer id) {
        return itineraryRepository.findById(id);
    }

    @Override
    public Itinerary saveItinerary(Itinerary itinerary) {
        return itineraryRepository.save(itinerary);
    }

    @Override
    public Itinerary updateItinerary(Itinerary itinerary) {
        return itineraryRepository.save(itinerary);
    }

    @Override
    public void deleteItinerary(Integer id) {
        itineraryRepository.deleteById(id);
    }

    @Override
    public List<Itinerary> getItineraryByTravelerId(Integer travelerId) {
        return itineraryRepository.findByTravelerId(travelerId);
    }

    @Override
    public Itinerary generateItinerary(GenerateItineraryRequest request, Integer travelerId) {
        Random random = new Random();

        // 1. Ambil semua destinasi dari provinsi
        List<Destinasi> destinasiList = destinasiRepository.findByProvinsiId(request.getProvinceId());
        List<Accommodation> accommodationList = accommodationRepository.findByProvinsiId(request.getProvinceId());
        List<Transport> transportList = transportRepository.findByProvinsiId(request.getProvinceId());

        // Validasi data minimal
        if (destinasiList.isEmpty() || accommodationList.isEmpty() || transportList.isEmpty()) {
            throw new IllegalArgumentException("Province tidak memiliki destinasi, akomodasi, atau transport yang cukup");
        }

        // 2. Buat itinerary utama
        Itinerary itinerary = new Itinerary();
        itinerary.setTravelerId(travelerId);
        itinerary.setTitle(request.getTitle() != null ? request.getTitle() : "Trip to Province");
        itinerary.setTotalHari(request.getTotalDays());
        itinerary.setStatus("ITINERARY");

        if (request.getTravelDate() != null && !request.getTravelDate().isBlank()) {
            itinerary.setCreatedAt(itinerary.getCreatedAt());
        }

        Itinerary savedItinerary = itineraryRepository.save(itinerary);

        // 3. Generate detail per hari
        double totalBiaya = 0.0;
        int destinasiIndex = 0;

        for (int day = 1; day <= request.getTotalDays(); day++) {
            // Buat itinerary_day
            ItineraryDay itineraryDay = new ItineraryDay(savedItinerary.getItineraryId(), day);
            itineraryDay.setCatatan("Day " + day + " - Explore and Adventure");
            ItineraryDay savedDay = itineraryDayRepository.save(itineraryDay);

            double dayBiaya = 0.0;

            // Tambahkan 1-2 destinasi per hari (round-robin)
            int destinasiPerHari = destinasiList.size() >= request.getTotalDays() ? 1 : 2;
            for (int d = 0; d < destinasiPerHari && destinasiIndex < destinasiList.size(); d++) {
                Destinasi dest = destinasiList.get(destinasiIndex % destinasiList.size());
                double destCost = dest.getHarga() != null ? dest.getHarga() : 0.0;

                ItineraryDayDestinasi dayDestinasi = new ItineraryDayDestinasi(
                    savedDay.getDayId(),
                    dest.getDestinasiId(),
                    d + 1,
                    destCost
                );
                dayDestinasi.setDurasiMenit(120); // Default 2 jam
                itineraryDayDestinasiRepository.save(dayDestinasi);
                dayBiaya += destCost;
                destinasiIndex++;
            }

            // Tambahkan accommodation (satu per hari, random dari list)
            if (!accommodationList.isEmpty()) {
                Accommodation acc = accommodationList.get(random.nextInt(accommodationList.size()));
                double accCost = acc.getHargaPerMalam() != null ? acc.getHargaPerMalam() : 0.0;

                ItineraryDayAccommodation dayAccommodation = new ItineraryDayAccommodation(
                    savedDay.getDayId(),
                    acc.getAccommodationId(),
                    1,
                    accCost
                );
                itineraryDayAccommodationRepository.save(dayAccommodation);
                dayBiaya += accCost;
            }

            // Tambahkan transport (satu per hari, random dari list)
            if (!transportList.isEmpty()) {
                Transport trans = transportList.get(random.nextInt(transportList.size()));
                double transCost = trans.getHarga() != null ? trans.getHarga() : 0.0;

                ItineraryDayTransport dayTransport = new ItineraryDayTransport(
                    savedDay.getDayId(),
                    trans.getTransportId(),
                    1,
                    transCost
                );
                itineraryDayTransportRepository.save(dayTransport);
                dayBiaya += transCost;
            }

            // Update biaya hari
            itineraryDay.setBiayaHari(dayBiaya);
            itineraryDayRepository.save(itineraryDay);
            totalBiaya += dayBiaya;
        }

        // 4. Update total biaya itinerary
        savedItinerary.setTotalBiaya(totalBiaya);
        itineraryRepository.save(savedItinerary);

        return savedItinerary;
    }
}