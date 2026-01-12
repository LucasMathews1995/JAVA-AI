package dev.ia;


import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Produces;
import dev.langchain4j.data.segment.TextSegment;

import java.util.function.Supplier;

@ApplicationScoped
public class RagConfiguration implements Supplier<RetrievalAugmentor> {

    @Inject
    EmbeddingStore<TextSegment> store;

    @Inject
    EmbeddingModel model;


    @Override
    public RetrievalAugmentor get() {

        return  DefaultRetrievalAugmentor.builder().contentRetriever(EmbeddingStoreContentRetriever.builder()
                        .embeddingModel(model)
                        .embeddingStore(store)
                        .maxResults(5).build())
                .build();

    }
}
