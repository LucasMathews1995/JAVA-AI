# ✈️ Mundo Viagens AI - Assistente de Viagens Inteligente

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Quarkus](https://img.shields.io/badge/Quarkus-Supersonic-4695eb?style=for-the-badge&logo=quarkus&logoColor=white)
![LangChain4j](https://img.shields.io/badge/LangChain4j-AI_Integration-green?style=for-the-badge)
![Ollama](https://img.shields.io/badge/Ollama-Local_LLM-white?style=for-the-badge&logo=ollama&logoColor=black)

> Um microsserviço de Agente de Viagens Autônomo que utiliza **IA Generativa (LLMs)** com **Tool Calling** seguro para gerenciar reservas, responder dúvidas e interagir com dados reais do usuário.

## 🧠 Sobre o Projeto

Este projeto não é apenas um chatbot wrapper. É uma implementação robusta de **Arquitetura de Agentes** onde a IA possui ferramentas ("Tools") para executar ações no banco de dados.

O diferencial principal é a arquitetura **Zero Trust**: a IA nunca recebe acesso direto ao banco. Todas as ferramentas injetam o contexto de segurança (JWT) automaticamente, garantindo que um usuário nunca acesse dados de outro, independente do prompt utilizado.

### Principais Funcionalidades
* **Chat Contextual:** Memória de conversação para entender pedidos complexos.
* **RAG & Tool Calling:** A IA decide quando consultar o banco de dados para ver passagens, preços ou datas.
* **Segurança via Token:** Integração profunda entre o `JsonWebToken` do Quarkus e as Tools do LangChain4j.
* **Busca Semântica/Híbrida:** Consultas otimizadas com Hibernate Panache.

## 🛠️ Tech Stack

* **Core:** Java 21 + Quarkus (Supersonic Subatomic Java)
* **AI Framework:** LangChain4j
* **LLM Engine:** Ollama (Llama 3 / Mistral)
