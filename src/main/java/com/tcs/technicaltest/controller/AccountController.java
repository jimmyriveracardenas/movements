package com.tcs.technicaltest.controller;

import com.tcs.technicaltest.model.Account;
import com.tcs.technicaltest.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService service;

    @GetMapping
    public ResponseEntity<List<Account>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{number}")
    public ResponseEntity<Account> getByNumber(@PathVariable String number) {
        return ResponseEntity.ok(service.getByNumber(number));
    }

    @PostMapping
    public ResponseEntity<Account> create(@RequestBody Account account) {
        Account created = service.create(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{number}")
    public ResponseEntity<Account> update(@PathVariable String number, @RequestBody Account account) {
        return ResponseEntity.ok(service.update(number, account));
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<Void> delete(@PathVariable String number) {
        service.delete(number);
        return ResponseEntity.noContent().build();
    }
}