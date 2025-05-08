package com.randyn1080.socialmediapepspringproject.repository;

import com.randyn1080.socialmediapepspringproject.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);
}
