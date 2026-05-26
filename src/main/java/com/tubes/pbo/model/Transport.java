package com.tubes.pbo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "transport")
@Data // Mengotomatiskan Getter, Setter, toString via Lombok
public class Transport implements Manageable<Transport> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transportId;
    
    private String jenis;     // Contoh: Flight, Train, Bus
    private String provider;  // Contoh: Garuda Indonesia, KAI Executive
    private String jadwal;    // Contoh: 08:00 - 10:00
    private double harga;     // Nilai nominal harga tiket

  
    @Override
    public void add(Transport data) {
        // Logika spesifik OOP lokal jika diperlukan sebelum persistence
    }

    @Override
    public List<Transport> view() {
        return null; // Pengambilan data utama akan di-handle oleh Service Layer & Repository
    }

    @Override
    public void edit(int id, Transport data) {
        // Logika sinkronisasi enkapsulasi data lokal sebelum diperbarui
    }

    @Override
    public void delete(int id) {
        // Logika penghapusan objek lokal
    }
}