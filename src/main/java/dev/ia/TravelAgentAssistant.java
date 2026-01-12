package dev.ia;

import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface TravelAgentAssistant {



    String chat(String userMessage);
}
