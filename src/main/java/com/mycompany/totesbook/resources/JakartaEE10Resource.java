package com.mycompany.totesbook.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * Recurs REST que busca la disponibilitat de l'entorn Jakarta EE 10.
 * 
 * Classe de Endpoint GET que retorna un missatge de verificació per confirmar 
 * l'aplicació s'ha desplegat correctament.
 * 
 * @author equip TotEsBook
 */
@Path("jakartaee10")
public class JakartaEE10Resource {
    /**
     * Endpooint que retorna un missatge de confirmació del ping.
     * 
     * @return Cadena de text que indica que el servei està funcionament.
     */
    @GET
    public Response ping(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
}
