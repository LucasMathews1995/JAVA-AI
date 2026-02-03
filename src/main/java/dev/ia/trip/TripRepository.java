package dev.ia.trip;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;


@ApplicationScoped
public class TripRepository implements PanacheRepository<Trip> {

    public Optional<Trip> findByDestination(String destination){
        return find("destination ILIKE ?1",destination.trim()).firstResultOptional();
    }
    public Optional<Trip> findByDestinationAndAtividades(String destination,String atividades){
        return find("destination ILIKE ?1 and atividades ILIKE ?2","%"+destination+"%","%"+atividades+"%").firstResultOptional();
    }



}
