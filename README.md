# Spring Boot + Ollama Phi3 Chatbot

A REST-based chatbot application built with Spring Boot 3 and Ollama's Phi3 model.

## 🚀 Tech Stack
- Java 17
- Spring Boot 3.x
- Ollama + Phi3 LLM
- Maven

## ✨ Key Features
- Chat Memory: Maintains conversation history using SessionId
- Error Handling: Null checks and try-catch for Ollama API failures  
- Async Processing: Improved response time

## 🛠️ How to Run
1. Install [Ollama](https://ollama.ai/) and pull phi3 model: ollama run phi3
2. Clone this repo
3. Run SpringOpenAiApplication.java
4. Test API: POST http://localhost:8080/api/chat

## 🎯 Challenges Faced & Solutions
1. NPE on empty response → Added null checks + try-catch
2. Slow response → Loaded model in background 
3. No chat memory → Used List with SessionId

---
Made by Tabbu02
