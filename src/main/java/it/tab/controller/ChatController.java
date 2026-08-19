package it.tab.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.tab.service.OllamaService1;

@RestController
@RequestMapping("/api")
public class ChatController {

	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

	@Autowired
	private OllamaService1 ollamaService;

	@PostMapping("/chat")
	public ResponseEntity<Map<String, String>> chatMethod(@RequestBody Map<String, String> request) {

		Map<String, String> response = new HashMap<>();

		try {
			String userMsg = request.get("msg");
			logger.info("Received chat request: {}", userMsg);

			if (userMsg == null || userMsg.trim().isEmpty()) {

				logger.warn("Empty msg received");
				response.put("error", "Message cannot be empty");
				return ResponseEntity.badRequest().body(response);
			}

			String aiReply = ollamaService.getReply(userMsg);
			logger.info("Sending reply to user", aiReply);
			response.put("reply", aiReply);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			logger.error("Error in chat method", e);
			response.put("error", "Something went wrong: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}
}
