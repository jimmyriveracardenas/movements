package com.tcs.technicaltest.service.imp;

import com.tcs.technicaltest.exception.CustomResponseStatusException;
import com.tcs.technicaltest.model.Account;
import com.tcs.technicaltest.model.Movement;
import com.tcs.technicaltest.repository.AccountRepository;
import com.tcs.technicaltest.repository.MovementRepository;
import com.tcs.technicaltest.service.IMovementService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovementServiceImp implements IMovementService {


    private final MovementRepository movementRepo;
    private final AccountRepository accountRepo;


     @Override
    public Movement create(Movement movement) {
        Account account = accountRepo.findById(
                movement.getAccount().getAccountNumber()
        ).orElseThrow(() -> new CustomResponseStatusException(
                "ACCOUNT_NOT_FOUND", HttpStatus.NOT_FOUND,
                "Cuenta no encontrada"
        ));

        BigDecimal currentBalance = account.getInitialBalance();
        BigDecimal updatedBalance = currentBalance.add(movement.getAmount());

        if (updatedBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new CustomResponseStatusException(
                    "INSUFFICIENT_FUNDS", HttpStatus.BAD_REQUEST,
                    "Saldo no disponible"
            );
        }

        movement.setBalance(updatedBalance);
        movement.setDate(LocalDateTime.now());
        movement.setAccount(account);
        Movement saved = movementRepo.save(movement);

        account.setInitialBalance(updatedBalance);
        accountRepo.save(account);

        return saved;
    }

    @Override
    public Movement update(Long id, Movement movement) {
        return movementRepo.findById(id)
                .map(existing -> {
                    existing.setMovementType(movement.getMovementType());
                    BigDecimal delta = movement.getAmount().subtract(existing.getAmount());
                    BigDecimal newBalance = existing.getBalance().add(delta);

                    if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                        throw new CustomResponseStatusException(
                                "INSUFFICIENT_FUNDS", HttpStatus.BAD_REQUEST,
                                "Saldo no disponible"
                        );
                    }

                    existing.setAmount(movement.getAmount());
                    existing.setBalance(newBalance);
                    existing.setAccount(movement.getAccount());
                    return movementRepo.save(existing);
                })
                .orElseThrow(() -> new CustomResponseStatusException(
                        "MOVEMENT_NOT_FOUND", HttpStatus.NOT_FOUND,
                        "Movimiento no encontrado"
                ));
    }

    @Override
    public void delete(Long id) {
        movementRepo.deleteById(id);
    }

    @Override
    public Movement getById(Long id) {
        return movementRepo.findById(id)
                .orElseThrow(() -> new CustomResponseStatusException(
                        "MOVEMENT_NOT_FOUND", HttpStatus.NOT_FOUND,
                        "Movimiento no encontrado"
                ));
    }

    @Override
    public List<Movement> getAll() {
        return movementRepo.findAll();
    }


    // private final MovementRepository repository;

    // @Override
    // public Movement create(Movement movement) {
    //     return repository.save(movement);
    // }

    // @Override
    // public Movement update(Long id, Movement movement) {
    //     return repository.findById(id)
    //         .map(existing -> {
    //             existing.setDate(movement.getDate());
    //             existing.setMovementType(movement.getMovementType());
    //             existing.setAmount(movement.getAmount());
    //             existing.setBalance(movement.getBalance());
    //             existing.setAccount(movement.getAccount());
    //             return repository.save(existing);
    //         })
    //         .orElseThrow(() -> new RuntimeException("Movement not found"));
    // }

    // @Override
    // public void delete(Long id) {
    //     repository.deleteById(id);
    // }

    // @Override
    // public Movement getById(Long id) {
    //     return repository.findById(id)
    //         .orElseThrow(() -> new RuntimeException("Movement not found"));
    // }

    // @Override
    // public List<Movement> getAll() {
    //     return repository.findAll();
    // }
}