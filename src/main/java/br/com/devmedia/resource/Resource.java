package br.com.devmedia.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import br.com.devmedia.model.MessageBean;

@Path("/recurso")
@Consumes(MediaType.WILDCARD)
@Produces(MediaType.APPLICATION_JSON)
public class Resource {
	
	@GET 
    public MessageBean get() {
        return new MessageBean("GET /recurso executado com sucesso!");
    }
	
	@POST
    public MessageBean post() {
        return new MessageBean("POST /recurso executado com sucesso!");
    }
	
	@PUT
    public MessageBean put() {
		return new MessageBean("PUT /recurso executado com sucesso!");
    }
    
	@DELETE
    public MessageBean delete() {
		return new MessageBean("DELETE /recurso executado com sucesso!");
    }
    
}

