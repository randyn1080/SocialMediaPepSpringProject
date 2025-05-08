package com.randyn1080.socialmediapepspringproject.repository;

import com.randyn1080.socialmediapepspringproject.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MessageRepository is a Spring Data JPA repository interface for managing Message entities.
 * It provides default CRUD operations and supports custom queries for retrieving messages.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    /**
     * Finds and retrieves a list of Message entities that were posted by a specific account.
     *
     * @param accountId the ID of the account that posted the messages
     * @return a list of Message objects posted by the specified account
     */
    List<Message> findMessagesByPostedBy(Integer accountId);
}
