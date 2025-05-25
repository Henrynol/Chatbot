package ChatbotTEst;

import Semester3.business.Model.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MessageControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createMessage() {
        Message message = new Message(null, "What's your name?", "I am your friendly chatbot!");

        // POST request to create a new message
        ResponseEntity<Message> response = restTemplate.postForEntity("/api/messages", message, Message.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("What's your name?", response.getBody().getUserMessage());
        assertEquals("I am your friendly chatbot!", response.getBody().getBotResponse());
    }

    @Test
    void getAllMessages() {
        // GET request to retrieve all messages
        ResponseEntity<List> response = restTemplate.getForEntity("/api/messages", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getMessageById() {
        // Create a message first
        Message message = new Message(null, "Tell me a joke.", "Why don't scientists trust atoms? Because they make up everything!");
        ResponseEntity<Message> postResponse = restTemplate.postForEntity("/api/messages", message, Message.class);

        Long messageId = postResponse.getBody().getId();

        // GET request to retrieve a message by its ID
        ResponseEntity<Message> getResponse = restTemplate.getForEntity("/api/messages/" + messageId, Message.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals(messageId, getResponse.getBody().getId());
    }

    @Test
    void deleteMessageById() {
        // Create a message first
        Message message = new Message(null, "What's the weather like?", "I don't have weather information right now.");
        ResponseEntity<Message> postResponse = restTemplate.postForEntity("/api/messages", message, Message.class);

        Long messageId = postResponse.getBody().getId();

        // DELETE request to delete a message by its ID
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/messages/" + messageId, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);

        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
    }
}