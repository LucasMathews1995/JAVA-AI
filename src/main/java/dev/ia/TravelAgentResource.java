package dev.ia;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.awt.*;

@Path("/travel")
public class TravelAgentResource {


    @Inject
    PackageExpert assistant;

    @Inject
    JsonWebToken jwt;




    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String ask(String question) {
    String userName = jwt.getName();
        if(userName!= null){
            try {
        SecurityContext.setCurrentUser(userName);
        return assistant.chat(userName,question);
            }finally {
                SecurityContext.clearCurrentUser();
            }
        }else {
            return "Usuário precisa estar autenticado";
        }


    }

}
