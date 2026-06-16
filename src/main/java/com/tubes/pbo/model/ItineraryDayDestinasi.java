package com.tubes.pbo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "itinerary_day_destinasi")
public class ItineraryDayDestinasi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "day_id", nullable = false)
    private Integer dayId;

    @Column(name = "destinasi_id", nullable = false)
    private Integer destinasiId;

    @Column(name = "urutan", nullable = false)
    private Integer urutan;

    @Column(name = "durasi_menit")
    private Integer durasiMenit;

    @Column(name = "biaya", nullable = false)
    private Double biaya = 0.0;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Transient
    private Destinasi destinasiObj;

    // Constructors
    public ItineraryDayDestinasi() {}

    public ItineraryDayDestinasi(Integer dayId, Integer destinasiId, Integer urutan, Double biaya) {
        this.dayId = dayId;
        this.destinasiId = destinasiId;
        this.urutan = urutan;
        this.biaya = biaya;
    }

    // Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDayId() {
        return dayId;
    }

    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }

    public Integer getDestinasiId() {
        return destinasiId;
    }

    public void setDestinasiId(Integer destinasiId) {
        this.destinasiId = destinasiId;
    }

    public Integer getUrutan() {
        return urutan;
    }

    public void setUrutan(Integer urutan) {
        this.urutan = urutan;
    }

    public Integer getDurasiMenit() {
        return durasiMenit;
    }

    public void setDurasiMenit(Integer durasiMenit) {
        this.durasiMenit = durasiMenit;
    }

    public Double getBiaya() {
        return biaya;
    }

    public void setBiaya(Double biaya) {
        this.biaya = biaya;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Destinasi getDestinasiObj() {
        return destinasiObj;
    }

    public void setDestinasiObj(Destinasi destinasiObj) {
        this.destinasiObj = destinasiObj;
    }
}

