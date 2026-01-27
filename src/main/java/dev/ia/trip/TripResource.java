package dev.ia.trip;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/admin/trips")
public class TripResource {


    @Inject
    TripService tripService;

    @POST
    public Response salvarViaHttp(TripDTO dto) {

        tripService.saveTrip(dto);
        return Response.ok().build();
    }

}
