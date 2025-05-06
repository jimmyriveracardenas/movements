package com.tcs.technicaltest.repository;

import com.tcs.technicaltest.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    
}