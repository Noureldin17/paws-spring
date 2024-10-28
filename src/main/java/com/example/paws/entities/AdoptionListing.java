package com.example.paws.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adoption_listings")
public class AdoptionListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listingId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String petType;

    private String breed;

    private Integer age;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime listedDate;

    @Enumerated(EnumType.STRING)
    private ListingStatus status;

    @OneToMany(mappedBy = "adoptionListing", cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToMany(mappedBy = "adoptionListing", cascade = CascadeType.ALL)
    private List<AdoptionRequest> adoptionRequests;

    public enum ListingStatus {
        AVAILABLE, PENDING, ADOPTED
    }
}
