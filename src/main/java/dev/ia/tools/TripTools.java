package dev.ia.tools;

import dev.ia.booking.Category;
import dev.ia.trip.TripDTO;
import dev.ia.trip.TripService;
import dev.ia.trip.exceptions.TripAlreadyExistsException;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TripTools {
@Inject
TripService tripService;

@Tool("Salva uma viagem na minha tripService . Extraia tudo destino  ,duracao em dias que pode ficar , descricao da viagem , preco ,  as atividades , a politica de cancelamento e  a categoria da viagem")
public String saveTrip(
        @P("Qual o destino da viagem a armazenar") String destination,
        @P("Duração da viagem em dias")Integer duracao,
        @P("Descricao da viagem ")String descricao,
        @P("Atividades que pode fazer por lá")String atividades,
        @P("Preco da viagem")Double preco,
        @P("Politica de cancelamento")String politicaCancelamento,
        @P("Categoria da viagem ADVENTURE ou TREASURE ")String cat
){
    TripDTO tripDTO = new TripDTO(destination,duracao,descricao,atividades,preco,politicaCancelamento, Category.valueOf(cat.toUpperCase()));

    try{
        return tripService.saveTrip(tripDTO);
    }catch(TripAlreadyExistsException e){
       return e.getMessage();
    }
}




}
