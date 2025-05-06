package com.tcs.technicaltest.repository;

import com.tcs.technicaltest.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<Movement, Long> {
    
}