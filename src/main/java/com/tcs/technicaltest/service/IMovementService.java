package com.tcs.technicaltest.service;

import com.tcs.technicaltest.model.Movement;
import java.util.List;

public interface IMovementService {

    Movement create(Movement movement);

    Movement update(Long id, Movement movement);

    void delete(Long id);

    Movement getById(Long id);
    
    List<Movement> getAll();
}
