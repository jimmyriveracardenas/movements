package com.tcs.technicaltest.service.imp;

import com.tcs.technicaltest.model.Account;
import com.tcs.technicaltest.repository.AccountRepository;
import com.tcs.technicaltest.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImp implements IAccountService {

    private final AccountRepository repository;

    @Override
    public Account create(Account account) {
        return repository.save(account);
    }

    @Override
    public Account update(String number, Account account) {
        return repository.findById(number)
            .map(existing -> {
                existing.setAccountType(account.getAccountType());
                existing.setInitialBalance(account.getInitialBalance());
                existing.setStatus(account.getStatus());
                existing.setClient(account.getClient());
                return repository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Override
    public void delete(String number) {
        repository.deleteById(number);
    }

    @Override
    public Account getByNumber(String number) {
        return repository.findById(number)
            .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Override
    public List<Account> getAll() {
        return repository.findAll();
    }
}