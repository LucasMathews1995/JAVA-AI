package dev.ia.trip;

import dev.ia.trip.Category;

public record TripDTO(String destination, Integer duracao, String descricao, String atividades, Double preco, Integer politicaCancelamento,
                      Category categoria) {
}
