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
@Table(name = "itinerary_day_transport")
public class ItineraryDayTransport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "day_id", nullable = false)
    private Integer dayId;

    @Column(name = "transport_id", nullable = false)
    private Integer transportId;

    @Column(name = "urutan", nullable = false)
    private Integer urutan;

    @Column(name = "biaya", nullable = false)
    private Double biaya = 0.0;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Transient
    private Transport transportObj;

    public ItineraryDayTransport() {}

    public ItineraryDayTransport(Integer dayId, Integer transportId, Integer urutan, Double biaya) {
        this.dayId = dayId;
        this.transportId = transportId;
        this.urutan = urutan;
        this.biaya = biaya;
    }

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

    public Integer getTransportId() {
        return transportId;
    }

    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    public Integer getUrutan() {
        return urutan;
    }

    public void setUrutan(Integer urutan) {
        this.urutan = urutan;
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

    public Transport getTransportObj() {
        return transportObj;
    }

    public void setTransportObj(Transport transportObj) {
        this.transportObj = transportObj;
    }
}

