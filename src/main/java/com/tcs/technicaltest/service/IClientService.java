package com.tcs.technicaltest.service;

import com.tcs.technicaltest.model.Client;
import java.util.List;

public interface IClientService {
    
    Client create(Client client);

    Client update(Long id, Client client);

    void delete(Long id);

    Client getById(Long id);
    
    List<Client> getAll();
}