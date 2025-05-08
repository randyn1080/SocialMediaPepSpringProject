package com.randyn1080.socialmediapepspringproject.repository;

import com.randyn1080.socialmediapepspringproject.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The AccountRepository interface facilitates interactions with the database for Account entities.
 * It extends JpaRepository to provide default CRUD operations and offers custom query methods.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    /**
     * Finds an Account by its username.
     * @param username The username of the Account to find. Must not be blank.
     * @return The Account with the specified username, or null if no such Account exists.
     */
    Account findByUsername(String username);

    /**
     * Finds an Account by its username and password.
     *
     * @param username The username of the Account to find. Must not be blank.
     * @param password The password of the Account to find. Must not be blank.
     * @return The Account with the specified username and password, or null if no such Account exists.
     */
    Account findByUsernameAndPassword(String username, String password);
}
