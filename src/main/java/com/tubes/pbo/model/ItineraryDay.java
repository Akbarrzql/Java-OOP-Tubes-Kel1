package com.tubes.pbo.model;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "itinerary_day")
public class ItineraryDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "day_id")
    private Integer dayId;

    @Column(name = "itinerary_id", nullable = false)
    private Integer itineraryId;

    @Column(name = "hari_ke", nullable = false)
    private Integer hariKe;

    @Column(name = "catatan", columnDefinition = "TEXT")
    private String catatan;

    @Column(name = "biaya_hari", nullable = false)
    private Double biayaHari = 0.0;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    // Helper fields (transient - tidak disimpan ke database)
    @Transient
    private List<ItineraryDayDestinasi> destinasiList;

    @Transient
    private List<ItineraryDayAccommodation> accommodationList;

    @Transient
    private List<ItineraryDayTransport> transportList;

    // Constructors
    public ItineraryDay() {}

    public ItineraryDay(Integer itineraryId, Integer hariKe) {
        this.itineraryId = itineraryId;
        this.hariKe = hariKe;
        this.biayaHari = 0.0;
    }

    // Getters & Setters
    public Integer getDayId() {
        return dayId;
    }

    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }

    public Integer getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(Integer itineraryId) {
        this.itineraryId = itineraryId;
    }

    public Integer getHariKe() {
        return hariKe;
    }

    public void setHariKe(Integer hariKe) {
        this.hariKe = hariKe;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public Double getBiayaHari() {
        return biayaHari;
    }

    public void setBiayaHari(Double biayaHari) {
        this.biayaHari = biayaHari;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<ItineraryDayDestinasi> getDestinasiList() {
        return destinasiList;
    }

    public void setDestinasiList(List<ItineraryDayDestinasi> destinasiList) {
        this.destinasiList = destinasiList;
    }

    public List<ItineraryDayAccommodation> getAccommodationList() {
        return accommodationList;
    }

    public void setAccommodationList(List<ItineraryDayAccommodation> accommodationList) {
        this.accommodationList = accommodationList;
    }

    public List<ItineraryDayTransport> getTransportList() {
        return transportList;
    }

    public void setTransportList(List<ItineraryDayTransport> transportList) {
        this.transportList = transportList;
    }
}

