package com.tcs.technicaltest.repository;

import com.tcs.technicaltest.model.Movement;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByAccount_AccountNumberAndDateBetween(String accountNumber, LocalDateTime start, LocalDateTime end);
    
}