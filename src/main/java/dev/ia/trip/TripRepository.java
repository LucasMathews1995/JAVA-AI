package dev.ia.trip;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;


@ApplicationScoped
public class TripRepository implements PanacheRepository<Trip> {

    public Optional<Trip> findByDestination(String destination){
        return find("destination",destination).firstResultOptional();
    }

}
