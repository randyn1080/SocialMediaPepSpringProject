package com.randyn1080.socialmediapepspringproject.service;

import com.randyn1080.socialmediapepspringproject.entity.Account;
import com.randyn1080.socialmediapepspringproject.exception.DuplicateUsernameException;
import com.randyn1080.socialmediapepspringproject.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) {
        // check if username is blank
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            return null;
        }
        // check if password is at least 4 characters
        if (account.getUsername().length() < 4) {
            return null;
        }
        // check if username exists
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount != null) {
            throw new DuplicateUsernameException("Username already exists");
        }
        // save the account and return it with the generated ID
        return accountRepository.save(account);
    }

}
