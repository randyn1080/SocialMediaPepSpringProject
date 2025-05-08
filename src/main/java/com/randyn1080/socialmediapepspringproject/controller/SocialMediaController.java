package com.randyn1080.socialmediapepspringproject.controller;

import com.randyn1080.socialmediapepspringproject.entity.Account;
import com.randyn1080.socialmediapepspringproject.entity.Message;
import com.randyn1080.socialmediapepspringproject.service.AccountService;
import com.randyn1080.socialmediapepspringproject.service.MessageService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account newAccount) {
        Account registeredAccount = accountService.registerAccount(newAccount);
        return ResponseEntity.ok(registeredAccount);
    }

    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account loggedInAccount = accountService.login(account);
        return ResponseEntity.ok(loggedInAccount);
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message newMessage = messageService.createMessage(message);
        return ResponseEntity.ok(newMessage);
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId) {
        Integer rowsAffected = messageService.deleteMessageById(messageId);
        return ResponseEntity.ok(rowsAffected);
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessageTextById(@PathVariable Integer messageId,
                                                         @RequestBody Message message) {
        String newMessageText = message.getMessageText();
        int rowsUpdated = messageService.updateMessageTextById(messageId, newMessageText);
        return ResponseEntity.ok(rowsUpdated);
    }
}
