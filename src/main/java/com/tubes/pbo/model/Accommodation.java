package com.tubes.pbo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accommodation")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_id")
    private Integer accommodationId;

    @Column(name = "provinsi_id", nullable = false)
    private Integer provinsiId;

    @Column(name = "nama", nullable = false)
    private String nama;

    @Column(name = "lokasi")
    private String lokasi;

    @Column(name = "harga_per_malam")
    private Double hargaPerMalam;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Accommodation() {
    }

    // Getters & Setters
    public Integer getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Integer accommodationId) {
        this.accommodationId = accommodationId;
    }

    public Integer getProvinsiId() {
        return provinsiId;
    }

    public void setProvinsiId(Integer provinsiId) {
        this.provinsiId = provinsiId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public Double getHargaPerMalam() {
        return hargaPerMalam;
    }

    public void setHargaPerMalam(Double hargaPerMalam) {
        this.hargaPerMalam = hargaPerMalam;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
