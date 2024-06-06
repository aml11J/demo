package com.example.demo.controller;

import java.util.List;

import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private static final Logger log = LogManager.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;

    @PostMapping
    @Transactional
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        log.info("createAccount request {}",account);
        Account createdAccount = accountService.createAccount(account);
        ResponseEntity<Account> response = ResponseEntity.ok(createdAccount);
        log.info("createAccount response {}",response);
        return response;
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) throws Exception {
        log.info("updateAccount request ={} id ={}",account,id);
        Account updatedAccount = accountService.updateAccount(id, account);
        ResponseEntity<Account> response = ResponseEntity.ok(updatedAccount);
        log.info("updateAccount response ={} ",response);
        return response;
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Account> getAccount(@PathVariable Long id) throws Exception {
        log.info("getAccount request id ={}",id);
        Account account = accountService.getAccountById(id);
        ResponseEntity<Account> response = ResponseEntity.ok(account);
        log.info("getAccount response {}",response);
        return response;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<Account>> getAccounts(@RequestParam(defaultValue = "0") int page) {
        log.info("getAccounts request page ={}",page);
        List<Account> accounts = accountService.getAccounts(page);
        ResponseEntity<List<Account>> response = ResponseEntity.ok(accounts);
        log.info("getAccounts response ={}",response);
        return response;
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        log.info("deleteAccount request id ={}",id);
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/balance")
    public ResponseEntity<?> getBalanceInNewCurrency(@PathVariable Long id, @RequestParam String targetCurrency) throws Exception {
        log.info("getBalanceInNewCurrency target currency ={} id ={}",targetCurrency,id);
        try {
            double balance = accountService.getBalanceFromMyrInNewCurrency(targetCurrency, id);
            ResponseEntity<Double> response = ResponseEntity.ok(balance);
            log.info("getBalanceInNewCurrency response ={} ",response);
            return response;
        } catch (IllegalArgumentException e) {
            log.error("getBalanceInNewCurrency", e);
            return ResponseEntity.badRequest().body("Invalid target currency or account ID.");
        }
    }
}
