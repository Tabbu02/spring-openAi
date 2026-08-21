# Spring Boot + Ollama Phi3 Chatbot 🤖

A REST-based chatbot application built with **Spring Boot** that talks to a locally running **Ollama** server powered by the **Phi3** LLM — no paid APIs, everything runs on your machine.

## 🚀 Tech Stack

- Java 17
- Spring Boot 3.5.x (Spring Web)
- Ollama + Phi3 LLM
- RestTemplate (Ollama HTTP API integration)
- SLF4J Logging
- Maven

## ✨ Key Features

- **REST Chat API** – simple `POST /api/chat` endpoint (`{ "msg": "your question" }` → `{ "reply": "ai answer" }`)
- **Local LLM Integration** – communicates with Ollama's native chat API at `localhost:11434`
- **Robust Error Handling**
  - Empty/missing message → `400 Bad Request`
  - Ollama not running / unreachable → friendly connection error message
  - Null or malformed responses → graceful fallback instead of crashing
- **Logging** – every request, reply, and failure is logged for easy debugging

## 🏗️ How It Works

```
Client ──POST /api/chat──▶ ChatController ──▶ OllamaService1 ──HTTP──▶ Ollama (phi3)
   ▲                                                                        │
   └────────────────────── { "reply": "..." } ◀─────────────────────────────┘
```

1. Client sends a message to `POST /api/chat`
2. Controller validates the input (rejects empty messages)
3. Service builds an Ollama chat payload (`model=phi3`, `stream=false`) and calls `http://localhost:11434/api/chat`
4. The AI's reply is extracted from the response and returned as JSON

## 🛠️ How to Run

1. **Install [Ollama](https://ollama.ai/)** and pull the model:
   ```bash
   ollama run phi3
   ```

2. **Clone this repo**
   ```bash
   git clone https://github.com/Tabbu02/spring-openAi.git
   cd spring-openAi
   ```

3. **Run the app** (Java 17+ required)
   ```bash
   ./mvnw spring-boot:run
   ```
   On Windows:
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

4. **Test the API**
   ```bash
   curl -X POST http://localhost:8080/api/chat \
        -H "Content-Type: application/json" \
        -d '{"msg": "Explain what an LLM is in one line"}'
   ```

   Response:
   ```json
   { "reply": "An LLM is a neural network trained on massive text data to understand and generate human-like language." }
   ```

## 📁 Project Structure

```
src/main/java/it/tab/
├── SpringOpenAiApplication.java   # Entry point
├── controller/
│   └── ChatController.java        # POST /api/chat endpoint + validation
└── service/
    └── OllamaService1.java        # Ollama API integration + error handling
```

## 🎯 Challenges Faced & Solutions

| Challenge | Solution |
|-----------|----------|
| NPE when Ollama returned an empty response | Added null checks at each response level + try-catch |
| App crashed when Ollama wasn't running | Caught `RestClientException` and returned a clear "is Ollama running?" message |
| Hard-to-debug failures | Added SLF4J logging for requests, replies, and errors |

---

Made by [Tabbu02](https://github.com/Tabbu02) 🚀
