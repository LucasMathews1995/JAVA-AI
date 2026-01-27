package dev.ia.booking;


import dev.ia.booking.exceptions.BookingNotFoundExceptions;
import dev.ia.booking.exceptions.DateInvertedException;
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


    return lista.stream().filter(x-> x.status!= BookingStatus.CANCELLED).toList();


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
    booking.destination = dto.destination();
    bookingRepository.persist(booking);

    return booking;

}

@Transactional
public Booking cancelarReserva(BookingDTO dto ) {
        String userIdDoToken = jwt.getSubject();
        if(userIdDoToken == null){
            throw new BookingNotFoundExceptions("Nenhum agendamento localizado");
        }
       Booking booking =  bookingRepository.findByUserNameAndDestination(jwt.getName(),dto.destination()).orElseThrow(()-> new BookingNotFoundExceptions("Nenhum agendamento localizado"));

        booking.status = BookingStatus.CANCELLED;
     bookingRepository.persist(booking);
     return booking;
}

public List<Booking> getAllBookings() {
    return bookingRepository.listAll();
}





}
