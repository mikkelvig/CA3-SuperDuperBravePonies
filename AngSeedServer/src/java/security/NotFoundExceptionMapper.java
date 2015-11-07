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
public class NotFoundExceptionMapper implements ExceptionMapper<javax.ws.rs.NotFoundException>
{

    @Context
    ServletContext context;

    @Override
    public Response toResponse(javax.ws.rs.NotFoundException e)
    {
        {
            JsonObject jo = new JsonObject();
            jo.addProperty("Code", 404);
            jo.addProperty("Message", "The page/service you requested does not exist");
            return Response.status(Response.Status.NOT_FOUND).entity(jo.toString()).build();
        }
    }
}
