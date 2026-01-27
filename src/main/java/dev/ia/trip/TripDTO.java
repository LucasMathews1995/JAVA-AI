package dev.ia.trip;

import dev.ia.booking.Category;

public record TripDTO(String destination, Integer duracao, String descricao, String atividades, Double preco, String politicaCancelamento,
                      Category categoria) {
}
