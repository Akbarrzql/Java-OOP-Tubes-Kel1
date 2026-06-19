package com.tubes.pbo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "itinerary_day_accommodation")
public class ItineraryDayAccommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "day_id")
    private ItineraryDay itineraryDay;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @Column(name = "checkin_time")
    private LocalDateTime checkinTime;

    @Column(name = "checkout_time")
    private LocalDateTime checkoutTime;

    private Integer malam;
    private Double biaya;
    private String catatan;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ItineraryDayAccommodation() {}
    
    public ItineraryDayAccommodation(Integer dayId, Integer accommodationId, Integer malam, Double biaya) {
        ItineraryDay day = new ItineraryDay();
        day.setDayId(dayId);
        this.itineraryDay = day;
        
        Accommodation acc = new Accommodation();
        acc.setAccommodationId(accommodationId);
        this.accommodation = acc;
        
        this.malam = malam;
        this.biaya = biaya;
    }
    // Getter & Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public ItineraryDay getItineraryDay() { return itineraryDay; }
    public void setItineraryDay(ItineraryDay itineraryDay) { this.itineraryDay = itineraryDay; }

    public Accommodation getAccommodation() { return accommodation; }
    public void setAccommodation(Accommodation accommodation) { this.accommodation = accommodation; }

    public LocalDateTime getCheckinTime() { return checkinTime; }
    public void setCheckinTime(LocalDateTime checkinTime) { this.checkinTime = checkinTime; }

    public LocalDateTime getCheckoutTime() { return checkoutTime; }
    public void setCheckoutTime(LocalDateTime checkoutTime) { this.checkoutTime = checkoutTime; }

    public Integer getMalam() { return malam; }
    public void setMalam(Integer malam) { this.malam = malam; }

    public Double getBiaya() { return biaya; }
    public void setBiaya(Double biaya) { this.biaya = biaya; }

    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Integer getAccommodationId() {
    return accommodation != null ? accommodation.getAccommodationId() : null;
}

    public void setAccommodationObj(Accommodation accommodation) {
        this.accommodation = accommodation;
}
}