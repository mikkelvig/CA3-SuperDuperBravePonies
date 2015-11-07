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
public class GenericExceptionMapper implements ExceptionMapper<Throwable>{
    @Context
    ServletContext context;

    @Override
    public Response toResponse(Throwable e)
    {
        JsonObject jo = new JsonObject(); 
        jo.addProperty("Code", 500);
        jo.addProperty("Message", "Internal server Error, we are very sorry for the inconvenience");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jo.toString()).build();
    }
}