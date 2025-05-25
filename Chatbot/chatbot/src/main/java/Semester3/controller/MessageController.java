package Semester3.controller;

import Semester3.business.Model.Message;
import Semester3.business.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @PostMapping("/respond")
    public String getBotResponse(@RequestBody Message userMessage) {
        return messageService.getBotResponseForUserMessage(userMessage.getUserMessage());
    }
    // POST endpoint to create a message (user input and bot response)
    @PostMapping
    public Message createMessage(@RequestBody Message newMessage) {
        return messageService.createMessage(newMessage.getUserMessage(), newMessage.getBotResponse());
    }
    @PostMapping("/regex")
    public Message getMessageByRegex(@RequestBody String regexPattern) {
        return messageService.findMessageByRegex(regexPattern);
    }
    // GET endpoint to retrieve all messages
    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }
    @GetMapping("/")
    public String home() {
        return "index";  // Returns the "index.html" from the "templates" folder
    }
    // GET endpoint to retrieve a message by its ID
    @GetMapping("/{id}")
    public Message getMessage(@PathVariable Long id) {
        return messageService.getMessageById(id);
    }
    @DeleteMapping("/{id}")
    public String deleteMessage(@PathVariable Long id) {
        boolean isRemoved = messageService.deleteMessageById(id);
        if (isRemoved) {
            return "Message deleted successfully.";
        } else {
            return "Message not found.";
        }
    }
}
