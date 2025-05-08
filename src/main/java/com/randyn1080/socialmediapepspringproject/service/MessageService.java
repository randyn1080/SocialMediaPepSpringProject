package com.randyn1080.socialmediapepspringproject.service;

import com.randyn1080.socialmediapepspringproject.exception.InvalidMessageException;
import com.randyn1080.socialmediapepspringproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.randyn1080.socialmediapepspringproject.entity.*;

import java.util.List;

/**
 * Service class for managing messages and their operations.
 * Provides methods for creating, retrieving, updating, and deleting messages,
 * as well as fetching messages by specific criteria.
 */
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    /**
     * Constructs a new instance of the MessageService class, initializing
     * it with the required repository dependencies to manage messages and accounts.
     *
     * @param messageRepository the repository used for message data access
     * @param accountRepository the repository used for account data access
     */
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Creates a new message.
     * The message creation validates:
     * - The message text is not blank
     * - The message text is not over 255 characters
     * - The user account exists
     *
     * @param message the message to create
     * @return the created message with its generated ID
     * @throws InvalidMessageException if the message text is invalid or the user doesn't exist
     */
    public Message createMessage(Message message) {
        String messageText = message.getMessageText();
        // check if the text is valid
        if (messageText == null || messageText.isBlank()) {
            throw new InvalidMessageException("Message text cannot be blank");
        }

        // check for length
        if (messageText.length() > 255) {
            throw new InvalidMessageException("Message text must be less than 255 characters");
        }

        // check if the account exists
        boolean accountExists = accountRepository.existsById(message.getPostedBy());

        if (!accountExists) {
            throw new InvalidMessageException("Account does not exist");
        }

        // save the message and return it with the generated ID
        return messageRepository.save(message);
    }

    /**
     * Retrieves all messages.
     * @return a list of all messages in the database, or an empty list if no messages exist.
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Retrieves a message by its unique identifier.
     * If a message with the provided ID exists, it returns the message.
     * If no message is found, it returns null.
     *
     * @param messageId the unique identifier of the message to retrieve
     * @return the message with the specified ID, or null if no such message exists
     */
    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    /**
     * Deletes a message by its ID.
     * If the specified message ID exists, it deletes the corresponding message
     * and returns 1. If the message ID does not exist, it returns null.
     *
     * @param messageId the ID of the message to be deleted. Must be a valid positive integer.
     * @return 1 if the message was successfully deleted, or null if the message does not exist.
     */
    public Integer deleteMessageById(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return null;
    }

    /**
     * Updates the text of a message.
     * The update validates:
     * - The message exists
     * - The new message text is not blank
     * - The new message text is not over 255 characters
     *
     * @param messageId the ID of the message to update
     * @param newMessageText the new text for the message
     * @return the number of rows updated (1 if successful)
     * @throws InvalidMessageException if the message doesn't exist or the new text is invalid
     */
    public Integer updateMessageTextById(Integer messageId, String newMessageText) {
        // find the message
        Message existingMessage = messageRepository.findById(messageId).orElse(null);

        // check to see if message ID exists
        if (existingMessage == null) {
            throw new InvalidMessageException("Message with ID: " + messageId + " does not exist");
        }

        // check to see if the message text is valid
        if (newMessageText == null || newMessageText.isBlank()) {
            throw new InvalidMessageException("Message text cannot be blank");
        }
        if (newMessageText.length() > 255) {
            throw new InvalidMessageException("Message text must be less than 255 characters");
        }

        // update message text
        existingMessage.setMessageText(newMessageText);

        // save the message
        messageRepository.save(existingMessage);

        return 1;
    }

    /**
     * Retrieves all messages posted by a specific account.
     * @param accountId the ID of the user account whose messages are to be retrieved
     * @return a list of messages posted by the specified account, or an empty list if no messages exist.
     */
    public List<Message> getMessagesByAccountId(Integer accountId) {
        return messageRepository.findMessagesByPostedBy(accountId);
    }
}
