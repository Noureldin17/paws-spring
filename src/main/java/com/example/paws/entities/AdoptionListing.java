package com.example.paws.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @Column(name = "pet_name", nullable = false)
    private String petName;

    @Column(name = "pet_type", nullable = false)  // New column
    private String petType;

    @Column(name = "breed")
    private String breed;

    @OneToMany(mappedBy = "adoptionListing", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdoptionRequest> adoptionRequests = new ArrayList<>();

}
