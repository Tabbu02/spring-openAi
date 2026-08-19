package it.tab.service;

import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import it.tab.controller.ChatController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OllamaService1 {

	private static final Logger logger = LoggerFactory.getLogger(OllamaService1.class);

	private final RestTemplate restTemplate = new RestTemplate();

	private final String OLLAMA_URL = "http://localhost:11434/api/chat";

	public String getReply(String msg) {
		try {
			logger.info("Calling ollama api for msg: {}", msg);
			Map<String, Object> message = Map.of("role", "user", "content", msg);
			Map<String, Object> body = Map.of("model", "phi3", "messages", List.of(message), "stream", false);

			ResponseEntity<Map> response = restTemplate.postForEntity(OLLAMA_URL, body, Map.class);

			Map responseBody = response.getBody();
			if (responseBody == null) {
				logger.error("Ollama returned null response");
				return "Error: Ollama returned empty response";
			}

			Map messageResponse = (Map) responseBody.get("message");
			if (messageResponse == null) {
				return "Error: Message not found in Ollama response";
			}

			logger.info("Ollama replied successfully");

			return (String) messageResponse.get("content");

		} catch (RestClientException e) {

			return "Error: Could not connect to Ollama. Please check if Ollama is running on port 11434";
		} catch (Exception e) {
			logger.error("Failed to connect to ollama", e);

			return "Error: " + e.getMessage();
		}
	}
}
