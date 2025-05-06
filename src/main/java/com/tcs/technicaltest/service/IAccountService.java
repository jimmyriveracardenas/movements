package com.tcs.technicaltest.service;

import java.util.List;
import com.tcs.technicaltest.model.Account;

public interface IAccountService {

    Account create(Account account);

    Account update(String number, Account account);

    void delete(String number);

    Account getByNumber(String number);
    
    List<Account> getAll();

}
