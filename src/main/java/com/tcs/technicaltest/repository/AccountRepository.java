package com.tcs.technicaltest.repository;

import com.tcs.technicaltest.model.Account;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    
    List<Account> findByClient_ClientId(Long clientId);

}