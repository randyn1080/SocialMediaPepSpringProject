package com.randyn1080.socialmediapepspringproject.service;

import com.randyn1080.socialmediapepspringproject.entity.Account;
import com.randyn1080.socialmediapepspringproject.exception.AuthenticationFailedException;
import com.randyn1080.socialmediapepspringproject.exception.DuplicateUsernameException;
import com.randyn1080.socialmediapepspringproject.exception.InvalidPasswordException;
import com.randyn1080.socialmediapepspringproject.exception.InvalidUsernameException;
import com.randyn1080.socialmediapepspringproject.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling account-related operations such as registration and login.
 * It interacts with the AccountRepository to persist and retrieve Account data from the database.
 */
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    /**
     * Constructs a new AccountService with the given AccountRepository dependency.
     * The AccountRepository is used to perform data access operations for Account entities.
     *
     * @param accountRepository the AccountRepository instance to be used by this service.
     *                          Must not be null.
     */
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Registers a new user account.
     * The account registration validates:
     * - The username is not blank
     * - The password is at least 4 characters long
     * - The username is not already taken
     *
     * @param account the account information to register
     * @return the registered account with its generated ID
     * @throws InvalidUsernameException if the username is blank
     * @throws InvalidPasswordException if the password is less than 4 characters
     * @throws DuplicateUsernameException if the username already exists
     */
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

    /**
     * Authenticates a user by validating the provided credentials against existing accounts.
     *
     * Validation rules:
     * - Username and password cannot be blank
     * - Credentials must match an existing account in the system
     *
     * @param account the account credentials for login (username and password)
     * @return the authenticated account details including accountId if successful
     * @throws AuthenticationFailedException if the credentials are invalid or do not match any account
     */
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
