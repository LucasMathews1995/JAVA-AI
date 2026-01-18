package dev.ia.repository;

import dev.ia.model.Booking;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.List;
@ApplicationScoped
public class BookingRepository implements PanacheRepository<Booking> {

    public List<Booking> findByUser(long userId) {
        return list("userId", userId);
    }
    public List<Booking> findByCustomerName(String name) {
        return list("customerName", name);
    }
    public List<Booking> buscarPorUsuario(String usuarioId) {
        return list("usuarioId", usuarioId);
    }

    public List<Booking> buscarPorPeriodo(String userId, LocalDate inicio, LocalDate fim) {
        return list("usuarioId = ?1 and startDate >= ?2 and endDate<= ?3", userId, inicio, fim);
    }
    public List<Booking> findByDestino(Long userId, String cidade) {

        return list("userId = ?1 and lower(destination) like lower(?2)", userId, "%" + cidade + "%");
    }

}
