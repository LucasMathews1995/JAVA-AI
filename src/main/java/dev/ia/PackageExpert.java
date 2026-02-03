package dev.ia;


import dev.ia.tools.BookingTools;
import dev.ia.tools.TripTools;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;


@RegisterAiService(retrievalAugmentor = RagConfiguration.class, tools = {BookingTools.class, TripTools.class})
public interface PackageExpert {


    @SystemMessage("""
        Você é um assistente virtual de uma agência de viagens.
        
        REGRAS DE EXECUÇÃO:
        1. CONSULTA: Para informações sobre pacotes existentes, use os documentos (RAG).
        2. ADMINISTRAÇÃO: Se o usuário quiser CADASTRAR uma viagem, use obrigatoriamente a 'TripTools'.
        3. O contexto de texto (RAG) pode estar desatualizado. O Banco de Dados (Tool) é a fonte da verdade.
        4. RESERVAS: Se o usuário quiser RESERVAR, CANCELAR ou LISTAR reservas, use a 'BookingTools'.
        
        Nunca diga que não tem informações se o usuário estiver tentando realizar uma ação (como salvar uma viagem ou fazer uma reserva). Se a informação não estiver nos documentos, mas o usuário estiver dando ordens de cadastro, use as ferramentas.
        """)
    String chat(@MemoryId String memoryId, @UserMessage String userMessage);
}
