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
   Optional<Trip> tripFound=  tripRepository.findByDestination(tripDTO.destination());

        if(tripFound.isPresent()){
            throw new TripAlreadyExistsException("Trip already exists");

        }
      Trip trip = new  Trip();
      trip.atividades = tripDTO.atividades();
      trip.descricao = tripDTO.descricao();
      trip.duracao = tripDTO.duracao();
      trip.politicaCancelamento = tripDTO.politicaCancelamento();
      trip.preco = tripDTO.preco();
      trip.destination = tripDTO.destination();


    tripRepository.persist(trip);
     return String.format("Salvamos a viagem %s com duração para %d \n cuja sua descricao é : %s \n e politica de cancelamento : %s e o preco : %s",trip.destination,trip.duracao,trip.descricao,trip.politicaCancelamento,trip.preco);
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
    public String removerTrip(String destination, String atividades ) {
        String atividadesSemAcento =StringUtils.stripAccents(atividades.trim());
        String destinationSemAcento =StringUtils.stripAccents(destination.trim());
       Trip tripFound=  tripRepository.findByDestinationAndAtividades(destinationSemAcento,atividadesSemAcento).orElseThrow(()->  new TripNotFoundException("nenhuma viagem encontrada para remover"));

        System.out.println(tripFound);
        tripRepository.delete(tripFound);

        return String.format("Remover a viagem %s com duração para %d \n cuja sua descricao é : %s \n e politica de cancelamento : %s e o preco : %s",tripFound.destination,tripFound.duracao,tripFound.descricao,tripFound.politicaCancelamento,tripFound.preco);




    }



}
