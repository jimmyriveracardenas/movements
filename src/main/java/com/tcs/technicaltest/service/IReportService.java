package com.tcs.technicaltest.service;

import java.time.LocalDateTime;
import com.tcs.technicaltest.dto.AccountStatementDTO;

public interface IReportService {
    
    AccountStatementDTO generateReport(Long clientId, LocalDateTime start, LocalDateTime end);
}