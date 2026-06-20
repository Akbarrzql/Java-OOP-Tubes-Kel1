package com.tubes.pbo.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "itinerary_day")
public class ItineraryDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "day_id")
    private Integer dayId;

    @ManyToOne
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;

    @Column(name = "hari_ke")
    private Integer hariKe;

    private String judul;

    @Column(name = "biaya_hari")
    private Double biayaHari;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "tanggal")
    private LocalDate tanggal;

    @OneToMany(mappedBy = "itineraryDay", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItineraryDayTransport> transports;

    @OneToMany(mappedBy = "itineraryDay", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItineraryDayAccommodation> accommodations;

    @OneToMany(mappedBy = "itineraryDay", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItineraryDayDestinasi> destinasiList;

    public ItineraryDay() {}
    //constructor
    public ItineraryDay(Integer itineraryId, Integer hariKe) {
        Itinerary itin = new Itinerary();
        itin.setItineraryId(itineraryId);
        this.itinerary = itin;
        this.hariKe = hariKe;
    }
    // Getter & Setter
    public Integer getDayId() { return dayId; }
    public void setDayId(Integer dayId) { this.dayId = dayId; }

    public Itinerary getItinerary() { return itinerary; }
    public void setItinerary(Itinerary itinerary) { this.itinerary = itinerary; }

    public Integer getHariKe() { return hariKe; }
    public void setHariKe(Integer hariKe) { this.hariKe = hariKe; }

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public Double getBiayaHari() { return biayaHari; }
    public void setBiayaHari(Double biayaHari) { this.biayaHari = biayaHari; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDate getTanggal() { return tanggal; }
    public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }
    
    public List<ItineraryDayTransport> getTransports() { return transports; }
    public void setTransports(List<ItineraryDayTransport> transports) { this.transports = transports; }

    public List<ItineraryDayAccommodation> getAccommodations() { return accommodations; }
    public void setAccommodations(List<ItineraryDayAccommodation> accommodations) { this.accommodations = accommodations; }

    public List<ItineraryDayDestinasi> getDestinasiList() { return destinasiList; }
    public void setDestinasiList(List<ItineraryDayDestinasi> destinasiList) { this.destinasiList = destinasiList; }
    public void setCatatan(String catatan) { this.judul = catatan; }

    public void setAccommodationList(List<ItineraryDayAccommodation> list) { this.accommodations = list; }
    public void setTransportList(List<ItineraryDayTransport> list) { this.transports = list; }
}