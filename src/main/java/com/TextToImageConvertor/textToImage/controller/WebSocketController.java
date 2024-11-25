package com.TextToImageConvertor.textToImage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Base64;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "all-images-topic", groupId = "image-group")
    public void sendImageToFrontend(String base64Image) {
        try {

            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);

            messagingTemplate.convertAndSend("/topic/images", encodedImage);

            System.out.println("Image sent to frontend through WebSocket.");
        } catch (Exception e) {
            System.err.println("Failed to decode or send the image: " + e.getMessage());
        }
    }

    @KafkaListener(topics="abusive-images-topic", groupId="image-group")
    public void sendAbusiveImagesToFrontend(String base64Image){
        try{
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);

            messagingTemplate.convertAndSend("/topic/abusiveImages",encodedImage);
            System.out.println("Abusive Image Sent to frontend through WebSocket");
        }
        catch(Exception e){
            System.err.println("Failed to decode or send the abusive image: "+ e.getMessage());
        }
    }

    @MessageMapping("/sendMessage")
    public void sendMessageToKafka(String message) {
        try {
            kafkaTemplate.send("test-topic", message);
            System.out.println("Message sent to Kafka: " + message);
        } catch (Exception e) {
            System.err.println("Failed to send message to Kafka: " + e.getMessage());
        }
    }

}
