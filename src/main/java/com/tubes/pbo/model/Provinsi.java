package com.tubes.pbo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "provinsi")
public class Provinsi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provinsi_id")
    private Integer provinsiId;

    @Column(name = "nama", nullable = false, length = 100)
    private String nama;

    @Column(name = "kota", length = 100)
    private String kota;

    @Column(name = "deskripsi", columnDefinition = "TEXT")
    private String deskripsi;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Integer getProvinsiId() { return provinsiId; }
    public void setProvinsiId(Integer provinsiId) { this.provinsiId = provinsiId; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getKota() { return kota; }
    public void setKota(String kota) { this.kota = kota; }
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}