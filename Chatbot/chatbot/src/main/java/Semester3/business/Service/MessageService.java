package Semester3.business.Service;
import Semester3.business.Model.Message;
import Semester3.persistence.impl.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Create a new message using the repository
    public Message createMessage(String userMessage, String botResponse) {
        Message message = Message.builder()
                .userMessage(userMessage)
                .botResponse(botResponse)
                .build();

        // Save the message via the repository
        return messageRepository.save(message);
    }

    // Get bot response for a specific user message using the repository
    public String getBotResponseForUserMessage(String userMessage) {
        return messageRepository.findAll().stream()
                .filter(message -> message.getUserMessage().equalsIgnoreCase(userMessage))
                .map(Message::getBotResponse)
                .findFirst()
                .orElse("Sorry, I don't have a response for that.");
    }
    public Message findMessageByRegex(String regexPattern) {
        List<Message> messages = messageRepository.findAll();

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);

        // Loop through messages and find the first match
        for (Message message : messages) {
            Matcher matcher = pattern.matcher(message.getUserMessage());
            if (matcher.find()) {
                return message;  // Return the first message that matches the regex
            }
        }

        // If no match is found, return null or a default response
        return null;
    }
    // Get all messages using the repository
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Get a message by ID using the repository
    public Message getMessageById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }

    // Delete a message by ID using the repository
    public boolean deleteMessageById(Long id) {
        return messageRepository.deleteById(id);
    }
}