package com.tcs.technicaltest.repository;

import com.tcs.technicaltest.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    
}
