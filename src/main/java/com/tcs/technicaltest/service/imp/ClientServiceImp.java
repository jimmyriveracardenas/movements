package com.tcs.technicaltest.service.imp;

import com.tcs.technicaltest.model.Client;
import com.tcs.technicaltest.repository.ClientRepository;
import com.tcs.technicaltest.service.IClientService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImp implements IClientService {

    @Autowired
    public ClientRepository repository;

    @Override
    public Client create(Client client) {
        return repository.save(client);
    }

    @Override
    public Client update(Long id, Client client) {
        return repository.findById(id)
            .map(existing -> {
                existing.setPassword(client.getPassword());
                existing.setStatus(client.getStatus());
                existing.setPerson(client.getPerson());
                return repository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Client getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @Override
    public List<Client> getAll() {
        return repository.findAll();
    }
}