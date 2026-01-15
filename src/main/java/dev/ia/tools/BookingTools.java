package dev.ia.tools;


import dev.ia.model.Booking;
import dev.ia.service.BookingService;
import dev.ia.Category;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@ApplicationScoped
public class BookingTools {

    @Inject
    BookingService bookingService;

    @Inject
    JsonWebToken jwt;

    @Tool("Obtém os detalhes completos de um reserva com base em seu número de identificação (bookingId).")
    public String getBookingDetails(Long bookingId) {
        return bookingService.getBookingDetails(bookingId).map(Booking::toString).orElse("Reserva com ID " +  bookingId + " nao encontrada.");

    }

    @Tool("""
            Cancela uma reserva existente.
            Para confirmar o cancelamento, é necessário fornecer o ID  da reserva (bookingId).
            O usuário deve estar autenticado
            
            """)
    public String cancelBooking(Long bookingId) {
        return bookingService.cancelBooking(bookingId).map(booking ->
            "Reserva" + bookingId + "cancelada com sucesso . Status Atual"
        ).orElse("Não foi possível cancelar a reserva . Verifique o Id da reserva e o sobrenome do cliente estão corretos");

    }


    @Tool("Lista os pacotes de viagem disponíveis para um determinada categoria (ex: ADVENTURE , TREASURES).")
    public String listPackagesByCategory(Category category) {
        List<Booking> packages = bookingService.findPackagesByCategory(category);
        if(packages.isEmpty()) {
            return "Nenhum pacote encontrado";

        }
        return "Pacotes encontrados para a categoria '" + category + "': "+ packages.stream().map(Booking::destination).toList();
    }


    @Tool("Retorna os detalhes da reserva do usuario atual")
    public Booking getBooking( ) {
        if(jwt.getName() == null) {

            throw new SecurityException("Usuário não autenticado");
        }
        String userId = jwt.getSubject();
        System.out.println("Buscando reservas para o usuario seguro: "+ userId);

        return booking
    }
}
