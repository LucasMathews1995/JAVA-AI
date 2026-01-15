package dev.ia.service;


import dev.ia.exceptions.BookingNotFoundExceptions;
import dev.ia.exceptions.DateInvertedException;
import dev.ia.model.Booking;
import dev.ia.repository.BookingRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class BookingService {


@Inject
BookingRepository bookingRepository;
@Inject
JsonWebToken jwt;


public List<Booking> listarMinhasReservas() {
    String userIdDoToken = jwt.getSubject();

    if( userIdDoToken == null){
        throw  new BookingNotFoundExceptions("Nenhum agendamento localizado");
    }
    List<Booking> lista = bookingRepository.buscarPorUsuario(userIdDoToken);
    if(lista.isEmpty()){
        throw new BookingNotFoundExceptions("Nenhum agendamento localizado");
    }
    return lista;


}


public List<Booking> buscarPorPeriodo(String userId, LocalDate inicio, LocalDate fim) {
    String userIdDoToken = jwt.getSubject();
    List<Booking> lista = bookingRepository.listAll();
    if(lista.isEmpty() || userIdDoToken == null){
        throw new BookingNotFoundExceptions("Nenhum agendamento localizado nesse período");
    }
    if(inicio.isAfter(fim)){
        throw new DateInvertedException("A data de inicial está a frente da final");
    }

   List <Booking> listaBooking= bookingRepository.buscarPorPeriodo(userIdDoToken, inicio, fim);

    if(listaBooking.isEmpty()){
        throw new BookingNotFoundExceptions("Nenhum agendamento localizado");
    }
    return listaBooking;

}





}
