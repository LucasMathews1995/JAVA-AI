package dev.ia.service;


import dev.ia.dto.BookingDTO;
import dev.ia.exceptions.BookingNotFoundExceptions;
import dev.ia.exceptions.DateInvertedException;
import dev.ia.model.Booking;
import dev.ia.model.BookingStatus;
import dev.ia.model.Category;
import dev.ia.repository.BookingRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

public List<Booking> listarMinhasReservasPorNome(String nome) {


    if(nome == null){
        throw new BookingNotFoundExceptions("Nenhum agendamento localizado");
    }

    return bookingRepository.findByCustomerName(nome);

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


@Transactional
public Booking saveBooking(BookingDTO dto ) {
    String userIdDoToken = jwt.getSubject();
    if(userIdDoToken == null){
        throw new BookingNotFoundExceptions("Nenhum agendamento localizado");
    }
    if(dto.startDate().isAfter(dto.endDate())){
        throw new DateInvertedException("A data de inicial está a frente da final");
    }

    Booking booking = new Booking();

    booking.userId = userIdDoToken;
    booking.customerName = jwt.getName();
    booking.category = dto.category();
    booking.startDate = dto.startDate();
    booking.endDate = dto.endDate();
    booking.status = BookingStatus.PENDING;
    bookingRepository.persist(booking);

    return booking;

}

public List<Booking> getAllBookings() {
    return bookingRepository.listAll();
}





}
