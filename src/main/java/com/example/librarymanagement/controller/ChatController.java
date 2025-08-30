package com.example.librarymanagement.controller;

import com.example.librarymanagement.model.ChatMessage;
import com.example.librarymanagement.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // <-- NEW: For sending private messages

    // Handles public messages sent to "/app/chat.sendMessage"
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessageRepository.save(chatMessage);

        // Broadcast the public message to everyone subscribed to /topic/public
        messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }

    // --- NEW METHOD for Private Messages ---
    // Handles private messages sent to "/app/chat.sendPrivateMessage"
    @MessageMapping("/chat.sendPrivateMessage")
    public void sendPrivateMessage(@Payload ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessageRepository.save(chatMessage);

        // Send the private message directly to the recipient's user-specific queue
        // The destination will be something like /user/recipient@example.com/queue/private
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipient(), "/queue/private", chatMessage
        );
    }

    // This is a regular REST endpoint to get the chat history when the page loads
    @GetMapping("/chat-history")
    @ResponseBody
    public List<ChatMessage> getChatHistory() {
        // For simplicity, we'll load all messages. A real app would filter.
        return chatMessageRepository.findAll();
    }
}