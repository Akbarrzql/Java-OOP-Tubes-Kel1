package com.tubes.pbo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "itinerary_day_transport")
public class ItineraryDayTransport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "day_id")
    private ItineraryDay itineraryDay;

    @ManyToOne
    @JoinColumn(name = "transport_id")
    private Transport transport;

    private Integer urutan;

    @Column(name = "waktu_berangkat")
    private LocalDateTime waktuBerangkat;

    @Column(name = "waktu_tiba")
    private LocalDateTime waktuTiba;

    private Double biaya;
    private String catatan;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ItineraryDayTransport() {}
    
    public ItineraryDayTransport(Integer dayId, Integer transportId, Integer urutan, Double biaya) {
        ItineraryDay day = new ItineraryDay();
        day.setDayId(dayId);
        this.itineraryDay = day;
        
        Transport trans = new Transport();
        trans.setTransportId(transportId);
        this.transport = trans;
        
        this.urutan = urutan;
        this.biaya = biaya;
    }
    // Getter & Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public ItineraryDay getItineraryDay() { return itineraryDay; }
    public void setItineraryDay(ItineraryDay itineraryDay) { this.itineraryDay = itineraryDay; }

    public Transport getTransport() { return transport; }
    public void setTransport(Transport transport) { this.transport = transport; }

    public Integer getUrutan() { return urutan; }
    public void setUrutan(Integer urutan) { this.urutan = urutan; }

    public LocalDateTime getWaktuBerangkat() { return waktuBerangkat; }
    public void setWaktuBerangkat(LocalDateTime waktuBerangkat) { this.waktuBerangkat = waktuBerangkat; }

    public LocalDateTime getWaktuTiba() { return waktuTiba; }
    public void setWaktuTiba(LocalDateTime waktuTiba) { this.waktuTiba = waktuTiba; }

    public Double getBiaya() { return biaya; }
    public void setBiaya(Double biaya) { this.biaya = biaya; }

    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Integer getTransportId() {
    return transport != null ? transport.getTransportId() : null;
}

    public void setTransportObj(Transport transport) {
        this.transport = transport;
}
}