package com.randyn1080.socialmediapepspringproject.service;

import com.randyn1080.socialmediapepspringproject.exception.InvalidMessageException;
import com.randyn1080.socialmediapepspringproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.randyn1080.socialmediapepspringproject.entity.*;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

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

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }
}
