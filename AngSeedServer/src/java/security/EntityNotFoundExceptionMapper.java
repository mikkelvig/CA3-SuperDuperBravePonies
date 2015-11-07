/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import com.google.gson.JsonObject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Bente
 */
@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException>{
@Context
ServletContext context;

    @Override
    public Response toResponse(EntityNotFoundException e) {
        JsonObject j = new JsonObject();
        j.addProperty("Code", 404);
        j.addProperty("Message", e.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(j.toString()).build();
    }
    
}