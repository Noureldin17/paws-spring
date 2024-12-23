package com.example.paws.dao;

import com.example.paws.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.orders LEFT JOIN FETCH u.adoptionRequests LEFT JOIN FETCH u.adoptionListings WHERE u.email = :email")
    Optional<User> findUserWithOrdersAndRequestsByEmail(@Param("email") String email);
}
