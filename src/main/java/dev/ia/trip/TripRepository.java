package dev.ia.trip;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;


@ApplicationScoped
public class TripRepository implements PanacheRepository<Trip> {

    public Optional<Trip> findByDestination(String destination){
        return find("destination ILIKE ?1",destination.trim()).firstResultOptional();
    }
    public Optional<Trip> findByDestinationAndCategory(String destination,Category category){
        return find("destination ILIKE ?1 and category ILIKE ?2","%"+destination+"%","%"+category+"%").firstResultOptional();
    }
    public Optional<Trip> findByDestinationAndCategoryAndDuracao(String destination, Category category, int duracao){
        return find("destination ILIKE ?1 and category = ?2 and duracao = ?3 ","%"+destination+"%",category,duracao).firstResultOptional();
    }


}
