package com.randyn1080.socialmediapepspringproject.service;

import com.randyn1080.socialmediapepspringproject.entity.Account;
import com.randyn1080.socialmediapepspringproject.exception.AuthenticationFailedException;
import com.randyn1080.socialmediapepspringproject.exception.DuplicateUsernameException;
import com.randyn1080.socialmediapepspringproject.exception.InvalidPasswordException;
import com.randyn1080.socialmediapepspringproject.exception.InvalidUsernameException;
import com.randyn1080.socialmediapepspringproject.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        // check if the username is blank
        if (username == null || username.isBlank()) {
            throw new InvalidUsernameException("Username cannot be blank");
        }
        // check if the password is at least 4 characters
        if (password.length() < 4) {
            throw new InvalidPasswordException("Password must be at least 4 characters");
        }
        // check if the username exists
        Account existingAccount = accountRepository.findByUsername(username);
        if (existingAccount != null) {
            throw new DuplicateUsernameException("Username already exists");
        }
        // save the account and return it with the generated ID
        return accountRepository.save(account);
    }

    public Account login(Account account) {
        // ensure fields are not null or empty
        String username = account.getUsername();
        String password = account.getPassword();
        if (username == null || username.isBlank()
            || password == null || password.isBlank()) {
            throw new AuthenticationFailedException("Username and password cannot be blank");
        }
        // check if username and password match an existing account
        Account existingAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (existingAccount == null) {
            throw new AuthenticationFailedException("Invalid username or password");
        }
        // if successful, return the account
        return existingAccount;
    }

}
