package com.tcs.technicaltest.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountReportDTO {
    
    private String accountNumber;
    private BigDecimal initialBalance;
    private BigDecimal currentBalance;
    private List<MovementDTO> movements;
}
