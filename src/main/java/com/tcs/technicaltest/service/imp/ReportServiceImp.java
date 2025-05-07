package com.tcs.technicaltest.service.imp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tcs.technicaltest.dto.AccountReportDTO;
import com.tcs.technicaltest.dto.AccountStatementDTO;
import com.tcs.technicaltest.dto.MovementDTO;
import com.tcs.technicaltest.exception.CustomResponseStatusException;
import com.tcs.technicaltest.model.Account;
import com.tcs.technicaltest.model.Client;
import com.tcs.technicaltest.model.Movement;
import com.tcs.technicaltest.repository.AccountRepository;
import com.tcs.technicaltest.repository.ClientRepository;
import com.tcs.technicaltest.repository.MovementRepository;
import com.tcs.technicaltest.service.IReportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImp implements IReportService {

    private final ClientRepository clientRepo;
    private final AccountRepository accountRepo;
    private final MovementRepository movementRepo;

    @Override
    public AccountStatementDTO generateReport(Long clientId, LocalDateTime start, LocalDateTime end) {
        Client client = clientRepo.findById(clientId)
            .orElseThrow(() -> new CustomResponseStatusException(
                "CLIENT_NOT_FOUND", HttpStatus.NOT_FOUND, "Cliente no encontrado"
            ));

        List<Account> accounts = accountRepo.findByClient_ClientId(clientId);
        List<AccountReportDTO> accountReports = accounts.stream().map(acc -> {
            List<Movement> movs = movementRepo.findByAccount_AccountNumberAndDateBetween(
                acc.getAccountNumber(), start, end
            );
            List<MovementDTO> movDTOs = movs.stream().map(m -> MovementDTO.builder()
                .movementId(m.getMovementId())
                .date(m.getDate())
                .movementType(m.getMovementType())
                .amount(m.getAmount())
                .balance(m.getBalance())
                .build()
            ).toList();

            return AccountReportDTO.builder()
                .accountNumber(acc.getAccountNumber())
                .initialBalance(acc.getInitialBalance())
                .currentBalance(acc.getInitialBalance())
                .movements(movDTOs)
                .build();
        }).toList();

        return AccountStatementDTO.builder()
            .clientId(clientId)
            .accounts(accountReports)
            .build();
    }
}