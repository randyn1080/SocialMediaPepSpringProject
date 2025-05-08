package com.randyn1080.socialmediapepspringproject.controller;

import com.randyn1080.socialmediapepspringproject.entity.Account;
import com.randyn1080.socialmediapepspringproject.entity.Message;
import com.randyn1080.socialmediapepspringproject.service.AccountService;
import com.randyn1080.socialmediapepspringproject.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
