package dev.ia;


import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BookingTools {

    @Inject
    BookingService bookingService;


    @Tool("Obtém os detalhes completos de um reserva com base em seu número de identificação (bookingId).")
    public String getBookingDetails(Long bookingId) {
        return bookingService.getBookingDetails(bookingId).map(Booking::toString).orElse("Reserva com ID " +  bookingId + " nao encontrada.");

    }

    @Tool("""
            Cancela uma reserva existente.
            Para confirmar o cancelamento, é necessário fornecer o ID  da reserva (bookingId)
            e o último noem do cliente (customerLastName)
            """)
    public String cancelBooking(Long bookingId,String customerLastName) {
        return bookingService.cancelBooking(bookingId,customerLastName).map(booking ->
            "Reserva" + bookingId + "cancelada com sucesso . Status Atual" + booking.status()
        ).orElse("Não foi possível cancelar a reserva . Verifique o Id da reserva e o sobrenome do cliente estão corretos");

    }
}
