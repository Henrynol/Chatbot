package Semester3.configuration.db;

import Semester3.business.Service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MessageDataInitializer {

    private final MessageService messageService;

    @EventListener(ApplicationReadyEvent.class)
    public void populateInitialDummyData() {
        // Check if there are no messages initialized, then add some sample messages
        if (messageService.getAllMessages().isEmpty()) {
            messageService.createMessage("Hello, chatbot!", "Hello! How can I assist you today?");
            messageService.createMessage("What's the weather like?", "I don't have weather information right now.");
            messageService.createMessage("Tell me a joke.", "Why don't scientists trust atoms? Because they make up everything!");
        }
}}
