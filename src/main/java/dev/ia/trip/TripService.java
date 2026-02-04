package dev.ia.trip;


import dev.ia.trip.exceptions.TripAlreadyExistsException;
import dev.ia.trip.exceptions.TripNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import java.util.Optional;

@ApplicationScoped
public class TripService {




    @Inject
    TripRepository tripRepository;

@Transactional
    public String saveTrip(TripDTO tripDTO) {
    String destinationSemAcento =StringUtils.stripAccents(tripDTO.destination().trim());
   Optional<Trip> tripFound=  tripRepository.findByDestinationAndCategoryAndDuracao(tripDTO.destination(),tripDTO.categoria(),tripDTO.duracao());

        if(tripFound.isPresent()){
            throw new TripAlreadyExistsException("Viagem já existe . Por isso não dá para salvar");
        }
      Trip trip = new  Trip();
      trip.atividades = tripDTO.atividades();
      trip.descricao = tripDTO.descricao();
      trip.duracao = tripDTO.duracao();
      trip.politicaCancelamento = tripDTO.politicaCancelamento();
      trip.preco = tripDTO.preco();
      trip.destination = tripDTO.destination();
      trip.category = tripDTO.categoria();


    tripRepository.persist(trip);
    System.out.printf("Salvamos a viagem %s com duração para %d %n cuja sua descricao é : %s %n e politica de cancelamento : %d e o preco : %.2f",trip.destination,trip.duracao,trip.descricao,trip.politicaCancelamento,trip.preco);
     return String.format("Salvamos a viagem %s com duração para %d %n cuja sua descricao é : %s %n e politica de cancelamento : %d e o preco : %.2f",trip.destination,trip.duracao,trip.descricao,trip.politicaCancelamento,trip.preco);
    }


    public String getByDestination(String destination){
    Optional<Trip> tripFound =  tripRepository.findByDestination(destination);
        System.out.println(tripFound);
    if(tripFound.isEmpty()){
        throw new TripNotFoundException("Nenhuma viagem encontrada por favor tente outra localidade");
    }
    Trip trip = tripFound.get();
        return String.format("Buscamos a viagem %s com duração para %d \n cuja sua descricao é : %s \n e politica de cancelamento : %s e o preco : %s",trip.destination,trip.duracao,trip.descricao,trip.politicaCancelamento,trip.preco);


    }

@Transactional
    public String removerTrip(String destination, Category category ) {


        String destinationSemAcento =StringUtils.stripAccents(destination.trim());

       Trip tripFound=  tripRepository.findByDestinationAndCategory(destinationSemAcento,category).orElseThrow(()->  new TripNotFoundException("nenhuma viagem encontrada para remover"));

        System.out.println(tripFound);
        tripRepository.delete(tripFound);

        return String.format("Remover a viagem %s com duração para %d \n cuja sua descricao é : %s \n e politica de cancelamento : %s e o preco : %s"
                ,tripFound.destination,tripFound.duracao,tripFound.descricao,
                tripFound.politicaCancelamento,tripFound.preco);


    }


    @Transactional
    public String updateTrip(String destination, Category category, TripDTO tripDTO) {

        Trip tripFound=  tripRepository.findByDestinationAndCategory(StringUtils.stripAccents(destination.trim()),category).orElseThrow(()->  new TripNotFoundException("nenhuma viagem encontrada para remover"));

        if(tripDTO.preco()!=null && tripDTO.preco()>0){tripFound.preco = tripDTO.preco();}
        if(tripDTO.duracao()!=null && tripDTO.duracao()>0){  tripFound.duracao = tripDTO.duracao();}
        if(tripDTO.descricao()!=null && tripDTO.descricao().isBlank()){tripFound.descricao = tripDTO.descricao();}
      if( tripDTO.politicaCancelamento()!=null && tripDTO.politicaCancelamento()>0){ tripFound.politicaCancelamento = tripDTO.politicaCancelamento();}
      if(tripDTO.atividades()!=null && tripDTO.atividades().isBlank()){ tripFound.atividades = tripDTO.atividades();}
      if(tripDTO.destination()!=null && tripDTO.destination().isBlank()){tripFound.destination = tripDTO.destination();}


       tripRepository.persist(tripFound);

       return  String.format("Atualizou a viagem %s com duração para %d \n cuja sua descricao é : %s \n e politica de cancelamento : %s e o preco : %s",tripFound.destination,tripFound.duracao,
               tripFound.descricao,tripFound.politicaCancelamento,tripFound.preco);

    }



}
