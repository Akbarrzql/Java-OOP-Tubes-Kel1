package com.tubes.pbo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class Transport {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transportId;
    private String jenis;    // Sesuai dengan "Jenis Kendaraan" di tabel
    private String provider; // Sesuai dengan "Nama Provider"
    private String jadwal;   // Sesuai dengan "Jadwal"
    private double harga;    // Sesuai dengan "Harga"
}
