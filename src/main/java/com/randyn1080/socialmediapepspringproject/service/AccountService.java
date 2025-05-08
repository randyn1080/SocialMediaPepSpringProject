package com.randyn1080.socialmediapepspringproject.service;

import com.randyn1080.socialmediapepspringproject.entity.Account;
import com.randyn1080.socialmediapepspringproject.exception.AuthenticationFailedException;
import com.randyn1080.socialmediapepspringproject.exception.DuplicateUsernameException;
import com.randyn1080.socialmediapepspringproject.exception.InvalidPasswordException;
import com.randyn1080.socialmediapepspringproject.exception.InvalidUsernameException;
import com.randyn1080.socialmediapepspringproject.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class responsible for handling account-related operations such as registration and login.
 * It interacts with the AccountRepository to persist and retrieve Account data from the database.
 */
@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

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
        logger.info("AccountService initialized");
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

        logger.debug("Validating account details for registration: username={}", username);

        // check if the username is blank
        if (username == null || username.isBlank()) {
            logger.warn("Registration failed: Username is blank");
            throw new InvalidUsernameException("Username cannot be blank");
        }
        // check if the password is at least 4 characters
        if (password.length() < 4) {
            logger.warn("Registration failed: Password too short for username: {}", username);
            throw new InvalidPasswordException("Password must be at least 4 characters");
        }
        // check if the username exists
        Account existingAccount = accountRepository.findByUsername(username);
        if (existingAccount != null) {
            logger.warn("Registration failed: Username already exists: {}", username);
            throw new DuplicateUsernameException("Username already exists");
        }
        // save the account and return it with the generated ID
        logger.debug("Account validation successful, proceeding with registration");
        Account savedAccount = accountRepository.save(account);
        logger.info("Account registered successfully with ID: {}", savedAccount.getAccountId());
        return savedAccount;
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

        logger.debug("Processing login attempt for username: {}", username);

        if (username == null || username.isBlank()
            || password == null || password.isBlank()) {
            logger.warn("Login failed: Username or password is blank");
            throw new AuthenticationFailedException("Username and password cannot be blank");
        }
        // check if username and password match an existing account
        Account existingAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (existingAccount == null) {
            logger.warn("Login failed: Invalid credentials for username: {}", username);
            throw new AuthenticationFailedException("Invalid username or password");
        }
        // if successful, return the account
        logger.info("Login successful for user ID: {}", existingAccount.getAccountId());
        return existingAccount;
    }

}
