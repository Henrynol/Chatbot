package Semester3.persistence.impl;

import Semester3.business.Model.Message;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MessageRepository {

    private List<Message> messages = new ArrayList<>();
    private AtomicLong idGenerator = new AtomicLong(1);


    public Message save(Message message) {
        message.setId(idGenerator.getAndIncrement());
        messages.add(message);
        return message;
    }


    public List<Message> findAll() {
        return new ArrayList<>(messages);
    }
    // Find a message by ID
    public Optional<Message> findById(Long id) {
        return messages.stream()
                .filter(message -> message.getId().equals(id))
                .findFirst();
    }


    public boolean deleteById(Long id) {
        return messages.removeIf(message -> message.getId().equals(id));
    }
}