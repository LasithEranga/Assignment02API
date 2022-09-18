/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mit.assignment02restapi;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.ToDo;

/**
 * REST Web Service
 *
 * @author Lasith
 */
@Path("todo")
@RequestScoped
public class TodoController {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public TodoController() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        return ToDo.find();
    }
    
    
    @GET
    @Path("{todo_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String search(@PathParam("todo_id") int todo_id) {
        return ToDo.find(todo_id);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String save(ToDo todo) {
        return ToDo.save(todo);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String update(String request) {
        JsonElement responseElement = JsonParser.parseString(request);
        JsonObject responseObj = responseElement.getAsJsonObject();
        int id = Integer.parseInt(responseObj.get("todo_id").toString());
        String updatedTodo = responseObj.get("todo").toString();
        ToDo todo = new Gson().fromJson(updatedTodo, ToDo.class);
        return ToDo.update(todo, id);
    }
    
    @POST
    @Path("done/{todo_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String completedTasks(@PathParam("todo_id") int todo_id) {
        return ToDo.markAsComplete(todo_id);
    }
    
    @DELETE
    @Path("{todo_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String remove(@PathParam("todo_id") int todo_id) {
        return ToDo.remove(todo_id);
    }
 

}
