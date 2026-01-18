package dev.ia.tools;


import dev.ia.SecurityContext;
import dev.ia.dto.BookingDTO;
import dev.ia.exceptions.BookingNotFoundExceptions;
import dev.ia.model.Booking;
import dev.ia.model.Category;
import dev.ia.service.BookingService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;

@ApplicationScoped
public class BookingTools {

    @Inject
    BookingService bookingService;



    @Tool("Busca no banco de dados todas as viagens, pacotes turísticos e reservas confirmadas do usuário. " +
            "Acione esta ferramenta sempre que o usuário perguntar por 'minhas viagens', 'meus agendamentos', " +
            "'o que eu tenho reservado' ou solicitar uma listagem do seu histórico de viagens.")
    public String listarMinhasReservas(){
        String currentUser = SecurityContext.getCurrentUser();
        return bookingService.listarMinhasReservasPorNome(currentUser).toString();
    }

    @Tool("Reservar uma viagem. Extraia o destino, datas e categoria dos dados da descrição da viagem.")
    public String saveBooking(
            @P("O destino da viagem") String destination,
            @P("Data de início (yyyy-MM-dd)") LocalDate startDate,
            @P("Data de término (yyyy-MM-dd)") LocalDate endDate,
            @P("A categoria da viagem. Use APENAS 'ADVENTURE' ou 'TREASURE'.") Category category
    )
    {

        BookingDTO dto = new BookingDTO(destination,startDate,endDate,category);

        try {
            return bookingService.saveBooking(dto).toString();
        }catch(BookingNotFoundExceptions e){
          return   e.getMessage();
        }
    }

    @Tool("Obter todas as listas de viagens, buscar as primeiras 10, caso ele peça mais coloque mais 10")
    public String listarTodasReservas(){

            return bookingService.getAllBookings().toString();


    }



}
