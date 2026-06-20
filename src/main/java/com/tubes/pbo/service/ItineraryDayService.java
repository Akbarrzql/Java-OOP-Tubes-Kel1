    package com.tubes.pbo.service;

    import com.tubes.pbo.model.*;
    import com.tubes.pbo.repository.*;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.util.List;

    @Service
    public class ItineraryDayService {

        @Autowired
        private ItineraryRepository itineraryRepository;

        @Autowired
        private ItineraryDayRepository itineraryDayRepository;

        @Autowired
        private ItineraryDayTransportRepository transportRepository;

        @Autowired
        private ItineraryDayAccommodationRepository accommodationRepository;

        @Autowired
        private ItineraryDayDestinasiRepository destinasiRepository;

        
        // ===== ITINERARY DAY =====

        public List<ItineraryDay> getDaysByItineraryId(Integer itineraryId) {
            return itineraryDayRepository
                .findByItinerary_ItineraryIdOrderByHariKeAsc(itineraryId);
        }

        @Transactional
        public ItineraryDay addDay(Integer itineraryId, String judul, LocalDate tanggal) {
            Itinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElseThrow(() -> new RuntimeException("Itinerary tidak ditemukan"));

            List<ItineraryDay> existingDays = getDaysByItineraryId(itineraryId);
            int nextHariKe = existingDays.size() + 1;

            ItineraryDay day = new ItineraryDay();
            day.setItinerary(itinerary);
            day.setHariKe(nextHariKe);
            day.setJudul(judul);
            day.setTanggal(tanggal); 
            day.setBiayaHari(0.0);
            day.setCreatedAt(LocalDateTime.now());

            ItineraryDay saved = itineraryDayRepository.save(day);

            // Update total hari di itinerary
            itinerary.setTotalHari(nextHariKe);
            itineraryRepository.save(itinerary);

            return saved;
        }

        @Transactional
        public void deleteDay(Integer dayId) {
            itineraryDayRepository.deleteById(dayId);
        }

        // ===== TRANSPORT =====

        public List<ItineraryDayTransport> getTransportByDayId(Integer dayId) {
            return transportRepository.findByItineraryDay_DayIdOrderByUrutanAsc(dayId);
        }

        @Transactional
        public ItineraryDayTransport addTransport(Integer dayId, ItineraryDayTransport transport) {
            ItineraryDay day = itineraryDayRepository.findById(dayId)
                .orElseThrow(() -> new RuntimeException("Day tidak ditemukan"));

            List<ItineraryDayTransport> existing = getTransportByDayId(dayId);
            transport.setItineraryDay(day);
            transport.setUrutan(existing.size() + 1);
            transport.setCreatedAt(LocalDateTime.now());

            ItineraryDayTransport saved = transportRepository.save(transport);
            recalculateDayBiaya(dayId);
            return saved;
        }

        @Transactional
        public void deleteTransport(Integer transportId) {
            transportRepository.deleteById(transportId);
        }

        @Transactional
        public ItineraryDayTransport updateTransport(Integer transportId, ItineraryDayTransport updated) {
            ItineraryDayTransport existing = transportRepository.findById(transportId)
                .orElseThrow(() -> new RuntimeException("Data transport tidak ditemukan"));

            existing.setTransport(updated.getTransport());
            existing.setBiaya(updated.getBiaya());
            existing.setCatatan(updated.getCatatan());
            existing.setWaktuBerangkat(updated.getWaktuBerangkat());
            existing.setWaktuTiba(updated.getWaktuTiba());

            ItineraryDayTransport saved = transportRepository.save(existing);
            recalculateDayBiaya(existing.getItineraryDay().getDayId());
            return saved;
        }
        // ===== ACCOMMODATION =====

        public List<ItineraryDayAccommodation> getAccommodationByDayId(Integer dayId) {
            return accommodationRepository.findByItineraryDay_DayId(dayId);
        }

        @Transactional
        public ItineraryDayAccommodation addAccommodation(Integer dayId, ItineraryDayAccommodation acc) {
            ItineraryDay day = itineraryDayRepository.findById(dayId)
                .orElseThrow(() -> new RuntimeException("Day tidak ditemukan"));

            acc.setItineraryDay(day);
            acc.setCreatedAt(LocalDateTime.now());

            ItineraryDayAccommodation saved = accommodationRepository.save(acc);
            recalculateDayBiaya(dayId);
            return saved;
        }

        @Transactional
        public void deleteAccommodation(Integer accId) {
            accommodationRepository.deleteById(accId);
        }

        @Transactional
        public ItineraryDayAccommodation updateAccommodation(Integer accId, ItineraryDayAccommodation updated) {
            ItineraryDayAccommodation existing = accommodationRepository.findById(accId)
                .orElseThrow(() -> new RuntimeException("Data akomodasi tidak ditemukan"));

            existing.setAccommodation(updated.getAccommodation());
            existing.setMalam(updated.getMalam());
            existing.setBiaya(updated.getBiaya());
            existing.setCatatan(updated.getCatatan());
            existing.setCheckinTime(updated.getCheckinTime());
            existing.setCheckoutTime(updated.getCheckoutTime());

            ItineraryDayAccommodation saved = accommodationRepository.save(existing);
            recalculateDayBiaya(existing.getItineraryDay().getDayId());
            return saved;
        }

        // ===== DESTINASI =====

        @Transactional(readOnly = true)  
        public List<ItineraryDayDestinasi> getDestinasiByDayId(Integer dayId) {
            List<ItineraryDayDestinasi> list = destinasiRepository.findByItineraryDay_DayIdOrderByWaktuAsc(dayId);
            
    
            for (ItineraryDayDestinasi d : list) {
                if (d.getDestinasi() != null) {
                    // Trigger proxy initialization
                    d.getDestinasi().getNama();
                }
            }
            return list;
        }

        @Transactional
        public ItineraryDayDestinasi addDestinasi(Integer dayId, ItineraryDayDestinasi dest) {
            ItineraryDay day = itineraryDayRepository.findById(dayId)
                .orElseThrow(() -> new RuntimeException("Day tidak ditemukan"));

            List<ItineraryDayDestinasi> existing = getDestinasiByDayId(dayId);
            dest.setItineraryDay(day);
            dest.setUrutan(existing.size() + 1);
            dest.setCreatedAt(LocalDateTime.now());

            ItineraryDayDestinasi saved = destinasiRepository.save(dest);
            recalculateDayBiaya(dayId);
            return saved;
        }

        @Transactional
        public void deleteDestinasi(Integer destId) {
            destinasiRepository.deleteById(destId);
        }

        @Transactional
        public ItineraryDayDestinasi updateDestinasi(Integer destId, ItineraryDayDestinasi updated) {
            ItineraryDayDestinasi existing = destinasiRepository.findById(destId)
                .orElseThrow(() -> new RuntimeException("Data destinasi tidak ditemukan"));

            existing.setDestinasi(updated.getDestinasi());
            existing.setDurasiMenit(updated.getDurasiMenit());
            existing.setWaktu(updated.getWaktu());
            existing.setBiaya(updated.getBiaya());
            existing.setCatatan(updated.getCatatan());

            ItineraryDayDestinasi saved = destinasiRepository.save(existing);
            recalculateDayBiaya(existing.getItineraryDay().getDayId());
            return saved;
        }
        
        // ===== RECALCULATE BIAYA =====

        @Transactional
        public void recalculateDayBiaya(Integer dayId) {
            ItineraryDay day = itineraryDayRepository.findById(dayId)
                .orElseThrow(() -> new RuntimeException("Day tidak ditemukan"));

            double total = 0.0;

            total += getTransportByDayId(dayId).stream()
                .mapToDouble(t -> t.getBiaya() != null ? t.getBiaya() : 0.0)
                .sum();

            total += getAccommodationByDayId(dayId).stream()
                .mapToDouble(a -> a.getBiaya() != null ? a.getBiaya() : 0.0)
                .sum();

            total += getDestinasiByDayId(dayId).stream()
                .mapToDouble(d -> d.getBiaya() != null ? d.getBiaya() : 0.0)
                .sum();

            day.setBiayaHari(total);
            itineraryDayRepository.save(day);

            recalculateItineraryBiaya(day.getItinerary().getItineraryId());
        }

        @Transactional
        public void recalculateItineraryBiaya(Integer itineraryId) {
            Itinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElseThrow(() -> new RuntimeException("Itinerary tidak ditemukan"));

            double total = getDaysByItineraryId(itineraryId).stream()
                .mapToDouble(d -> d.getBiayaHari() != null ? d.getBiayaHari() : 0.0)
                .sum();

            itinerary.setTotalBiaya(total);
            itineraryRepository.save(itinerary);
        }
    }