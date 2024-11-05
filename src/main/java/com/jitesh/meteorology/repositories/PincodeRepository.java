package com.jitesh.meteorology.repositories;

import com.jitesh.meteorology.entities.Pincode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PincodeRepository extends JpaRepository<Pincode, Long> {
    Optional<Pincode> findByPincode(String pincode);
}
