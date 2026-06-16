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
@Table(name = "itinerary_day_accommodation")
public class ItineraryDayAccommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "day_id", nullable = false)
    private Integer dayId;

    @Column(name = "accommodation_id", nullable = false)
    private Integer accommodationId;

    @Column(name = "malam", nullable = false)
    private Integer malam = 1;

    @Column(name = "biaya", nullable = false)
    private Double biaya = 0.0;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Transient
    private Accommodation accommodationObj;

    // Constructors
    public ItineraryDayAccommodation() {}

    public ItineraryDayAccommodation(Integer dayId, Integer accommodationId, Integer malam, Double biaya) {
        this.dayId = dayId;
        this.accommodationId = accommodationId;
        this.malam = malam;
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

    public Integer getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Integer accommodationId) {
        this.accommodationId = accommodationId;
    }


    public Integer getMalam() {
        return malam;
    }

    public void setMalam(Integer malam) {
        this.malam = malam;
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

    public Accommodation getAccommodationObj() {
        return accommodationObj;
    }

    public void setAccommodationObj(Accommodation accommodationObj) {
        this.accommodationObj = accommodationObj;
    }
}

