package dev.ia.trip;

import dev.ia.booking.BookingRepository;
import dev.ia.trip.exceptions.TripAlreadyExistsException;
import dev.ia.trip.exceptions.TripNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

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


}
