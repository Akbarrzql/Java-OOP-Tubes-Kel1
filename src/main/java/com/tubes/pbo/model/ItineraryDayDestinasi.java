package com.tubes.pbo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "itinerary_day_destinasi")
public class ItineraryDayDestinasi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "day_id")
    private ItineraryDay itineraryDay;

    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "destinasi_id")
    private Destinasi destinasi;

    private Integer urutan;

    @Column(name = "durasi_menit")
    private Integer durasiMenit;

    private Double biaya;
    private String catatan;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "waktu")
    private LocalTime waktu;

    public ItineraryDayDestinasi() {}

    // Constructor 
    public ItineraryDayDestinasi(Integer dayId, Integer destinasiId, Integer urutan, Double biaya) {
        ItineraryDay day = new ItineraryDay();
        day.setDayId(dayId);
        this.itineraryDay = day;
        
        Destinasi dest = new Destinasi();
        dest.setDestinasiId(destinasiId);
        this.destinasi = dest;
        
        this.urutan = urutan;
        this.biaya = biaya;
    }
    // Getter & Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public ItineraryDay getItineraryDay() { return itineraryDay; }
    public void setItineraryDay(ItineraryDay itineraryDay) { this.itineraryDay = itineraryDay; }

    public Destinasi getDestinasi() { return destinasi; }
    public void setDestinasi(Destinasi destinasi) { this.destinasi = destinasi; }

    public Integer getUrutan() { return urutan; }
    public void setUrutan(Integer urutan) { this.urutan = urutan; }

    public Integer getDurasiMenit() { return durasiMenit; }
    public void setDurasiMenit(Integer durasiMenit) { this.durasiMenit = durasiMenit; }
    
    public LocalTime getWaktu() { return waktu; }
    public void setWaktu(LocalTime waktu) { this.waktu = waktu; }

    public Double getBiaya() { return biaya; }
    public void setBiaya(Double biaya) { this.biaya = biaya; }

    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Integer getDestinasiId() {
        return destinasi != null ? destinasi.getDestinasiId() : null;
    }

    public void setDestinasiObj(Destinasi destinasi) {
        this.destinasi = destinasi;
    }
}