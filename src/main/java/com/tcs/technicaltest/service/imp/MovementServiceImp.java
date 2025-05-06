package com.tcs.technicaltest.service.imp;

import com.tcs.technicaltest.model.Movement;
import com.tcs.technicaltest.repository.MovementRepository;
import com.tcs.technicaltest.service.IMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovementServiceImp implements IMovementService {

    private final MovementRepository repository;

    @Override
    public Movement create(Movement movement) {
        return repository.save(movement);
    }

    @Override
    public Movement update(Long id, Movement movement) {
        return repository.findById(id)
            .map(existing -> {
                existing.setDate(movement.getDate());
                existing.setMovementType(movement.getMovementType());
                existing.setAmount(movement.getAmount());
                existing.setBalance(movement.getBalance());
                existing.setAccount(movement.getAccount());
                return repository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("Movement not found"));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Movement getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Movement not found"));
    }

    @Override
    public List<Movement> getAll() {
        return repository.findAll();
    }
}