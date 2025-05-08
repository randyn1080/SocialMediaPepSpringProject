package com.randyn1080.socialmediapepspringproject.controller;

import com.randyn1080.socialmediapepspringproject.entity.Account;
import com.randyn1080.socialmediapepspringproject.entity.Message;
import com.randyn1080.socialmediapepspringproject.service.AccountService;
import com.randyn1080.socialmediapepspringproject.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The SocialMediaController handles HTTP requests related to account and message operations.
 * It acts as a REST controller, exposing endpoints for user account management and message interactions.
 * These operations include account registration and login, as well as creating, retrieving,
 * updating, and deleting messages.
 *
 * The controller delegates business logic to the AccountService and MessageService
 * for processing requests and data operations.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    /**
     * Constructs a new SocialMediaController instance with dependencies on AccountService and MessageService.
     * These services are used to delegate operations related to accounts and messages, such as
     * user authentication, account registration, message creation, retrieval, updating, and deletion.
     *
     * @param accountService the service that handles account-related operations, including user registration and login
     * @param messageService the service that handles message-related operations, including creating, retrieving,
     *                       updating, and deleting messages
     */
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * Handles user registration requests.
     *
     * Endpoint: POST /register
     * Request Body: JSON representation of an Account (without accountId)
     *
     * @param newAccount the account information to register
     * @return ResponseEntity containing the registered Account with accountId
     *
     * Response Codes:
     * - 200 OK: Registration successful, returns the created Account with accountId
     * - 400 Bad Request: Registration failed (username blank or password too short)
     * - 409 Conflict: Username already exists
     */
    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account newAccount) {
        Account registeredAccount = accountService.registerAccount(newAccount);
        return ResponseEntity.ok(registeredAccount);
    }

    /**
     * Handles user login requests.
     *
     * Endpoint: POST /login
     * Request Body: JSON representation of an Account with username and password
     *
     * @param account the account credentials for login
     * @return ResponseEntity containing the authenticated Account with accountId
     *
     * Response Codes:
     * - 200 OK: Login successful, returns the Account with accountId
     * - 401 Unauthorized: Login failed (username or password incorrect)
     */
    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account loggedInAccount = accountService.login(account);
        return ResponseEntity.ok(loggedInAccount);
    }

    /**
     * Handles message creation requests.
     *
     * Endpoint: POST /messages
     * Request Body: JSON representation of a Message (without messageId)
     *
     * @param message the message to create
     * @return ResponseEntity containing the created Message with messageId
     *
     * Response Codes:
     * - 200 OK: Message created successfully, returns the created Message with messageId
     * - 400 Bad Request: Message creation failed (messageText blank or too long, or postedBy user doesn't exist)
     */
    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message newMessage = messageService.createMessage(message);
        return ResponseEntity.ok(newMessage);
    }

    /**
     * Retrieves all messages in the system.
     *
     * Endpoint: GET /messages
     *
     * @return ResponseEntity containing a JSON array of all messages (empty array if no messages exist)
     *
     * Response Codes:
     * - 200 OK: Returns a list of all messages
     */
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    /**
     * Retrieves a specific message by its ID.
     *
     * Endpoint: GET /messages/{messageId}
     * Path Parameter: messageId - The ID of the message to retrieve
     *
     * @param messageId the ID of the message to retrieve
     * @return ResponseEntity containing the requested message, or null if not found
     *
     * Response Codes:
     * - 200 OK: Returns the requested message as JSON, or null if the message doesn't exist
     */
    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.ok(message);
    }

    /**
     * Deletes a message by its ID.
     *
     * Endpoint: DELETE /messages/{messageId}
     * Path Parameter: messageId - The ID of the message to delete
     *
     * @param messageId the ID of the message to delete
     * @return ResponseEntity containing the number of rows deleted (1 if successful, null if not found)
     *
     * Response Codes:
     * - 200 OK: Returns the number of rows deleted if successful, or null if message didn't exist
     */
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId) {
        Integer rowsAffected = messageService.deleteMessageById(messageId);
        return ResponseEntity.ok(rowsAffected);
    }

    /**
     * Updates the text of an existing message.
     *
     * Endpoint: PATCH /messages/{messageId}
     * Path Parameter: messageId - The ID of the message to update
     * Request Body: JSON containing a messageText field
     *
     * @param messageId the ID of the message to update
     * @param message the message object containing the new text
     * @return ResponseEntity containing the number of rows updated (1 if successful)
     *
     * Response Codes:
     * - 200 OK: Message updated successfully, returns the number of rows updated (1)
     * - 400 Bad Request: Update failed (message doesn't exist, new text is blank or too long)
     */
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessageTextById(@PathVariable Integer messageId,
                                                         @RequestBody Message message) {
        String newMessageText = message.getMessageText();
        int rowsUpdated = messageService.updateMessageTextById(messageId, newMessageText);
        return ResponseEntity.ok(rowsUpdated);
    }

    /**
     * Retrieves all messages posted by a specific user.
     *
     * Endpoint: GET /accounts/{accountId}/messages
     * Path Parameter: accountId - The ID of the account whose messages to retrieve
     *
     * @param accountId the ID of the user account
     * @return ResponseEntity containing a JSON array of all messages posted by the specified user
     *          (empty array if the user has no messages or doesn't exist)
     *
     * Response Codes:
     * - 200 OK: Returns a list of messages posted by the specified user
     */
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable Integer accountId) {
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        return ResponseEntity.ok(messages);
    }
}
