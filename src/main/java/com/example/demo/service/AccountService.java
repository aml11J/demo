package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Account;
import com.example.demo.model.CurrencyConversion;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CurrencyService currencyService;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(Long id, Account account) {
        Account existingAccount = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        existingAccount.setName(account.getName());
        existingAccount.setUserid(account.getUserid());
        existingAccount.setBalance(account.getBalance());
        return accountRepository.save(existingAccount);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    }

    public List<Account> getAccounts(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Account> accountPage = accountRepository.findAll(pageable);
        return accountPage.getContent();
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public double getBalanceById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return account.getBalance();
    }

    public double getBalanceFromMyrInNewCurrency(String targetCurrency, Long accountId) throws Exception {
        CurrencyConversion currencyConversion = currencyService.getMyrCurrencyRates();
        Map<String, Double> rates = currencyConversion.getRates();

        if (rates == null || !rates.containsKey(targetCurrency.toLowerCase())) {
            throw new IllegalArgumentException("Currency not supported");
        }

        double conversionRate = rates.get(targetCurrency.toLowerCase());
        double balance = this.getBalanceById(accountId);

        return balance * conversionRate;
    }

}
