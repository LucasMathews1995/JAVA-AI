package dev.ia.tools;


import dev.ia.booking.BookingDTO;
import dev.ia.booking.exceptions.BookingNotFoundExceptions;
import dev.ia.booking.Category;
import dev.ia.booking.BookingService;
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

        return bookingService.listarMinhasReservas().toString();
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


    @Tool("Cancelar uma viagem. Extraia o destino dessa viagem, e qual data inicial que deseja retirar")
    public String cancelBooking(@P("O destino da viagem") String destination,@P("data inicial (yyyy-MM-dd)") LocalDate startDate){

        BookingDTO dto = new BookingDTO(destination,startDate,null,null);
        try {
            return bookingService.cancelarReserva(dto).toString();
        }catch(BookingNotFoundExceptions e){
            return   e.getMessage();
        }
    }

    @Tool("Obter todas as listas de viagens, buscar as primeiras 10, caso ele peça mais coloque mais 10")
    public String listarTodasReservas(){

            return bookingService.getAllBookings().toString();


    }
    @Tool("Reservar uma viagem. Extraia a destination, data de início e quantidade de dias.")
    public String saveBookingDias(
            @P("O destination da viagem (ex: Paris)") String destination,
            @P("Data de início no formato ISO yyyy-MM-dd") String startDate, // Receba como String para garantir
            @P("Quantidade de dias da estadia") int totalDias,
            @P("A categoria: ADVENTURE ou TREASURE") String category
    )
    {

        LocalDate date = LocalDate.parse(startDate);
        Category cat =  Category.valueOf(category.toUpperCase());
        BookingDTO dto = new BookingDTO(destination,date,date.plusDays(totalDias),cat);

        try {
            return bookingService.saveBooking(dto).toString();
        }catch(BookingNotFoundExceptions e){
            return   e.getMessage();
        }
    }







}
