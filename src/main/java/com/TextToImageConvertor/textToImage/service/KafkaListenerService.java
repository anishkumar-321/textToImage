package com.TextToImageConvertor.textToImage.service;

import com.TextToImageConvertor.textToImage.model.AbusiveModel;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KafkaListenerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaListenerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void processTextNotification(String textContent) {
        try {
            String placeholderUrl = "https://via.placeholder.com/300x200.png?text=" + textContent.replace(" ", "+");

            RestTemplate restTemplate = new RestTemplate();


            byte[] imageBytes = restTemplate.getForObject(placeholderUrl, byte[].class);

            String base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);

            kafkaTemplate.send("all-images-topic", base64Image);
            System.out.println("Base64 encoded image published to all-images-topic in Kafka: " + base64Image.substring(0, 50) + "...");

            if(AbusiveModel.checkAbusiveWordOrNot(textContent)){
                kafkaTemplate.send("abusive-images-topic",base64Image);
                System.out.println("Base64 encoded image published to abusive-images-topic in kafka: "+ base64Image.substring(0,50)+"...");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
