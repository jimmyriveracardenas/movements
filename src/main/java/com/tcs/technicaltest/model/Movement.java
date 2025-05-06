package com.tcs.technicaltest.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movimiento")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimiento_id")
    private Long movementId;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime date;

    @Column(name = "tipo_movimiento", nullable = false, length = 50)
    private String movementType;

    @Column(name = "valor", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "saldo", nullable = false, precision = 12, scale = 2)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "numero_cuenta", nullable = false)
    @JsonBackReference("account-movements")
    private Account account;
}

