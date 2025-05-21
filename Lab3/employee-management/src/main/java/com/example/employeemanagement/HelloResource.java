package com.example.employeemanagement;

import com.example.employeemanagement.models.employee;
import com.example.employeemanagement.services.employeeServices;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collection;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HelloResource {

    @Inject
    private employeeServices employeeServices;

    @GET
    public Collection<employee> getAllCoffees() {
        return employeeServices.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getCoffee(@PathParam("id") int id) {
        employee emp = employeeServices.getById(id);
        if (emp == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(emp).build();
    }

    @POST
    public Response addCoffee(employee emp) {
        employee created = employeeServices.create(emp);
        return Response.status(Response.Status.CREATED).entity(emp).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCoffee(@PathParam("id") int id, employee emp) {
        employee updated = employeeServices.update(id, emp);
        if (updated == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCoffee(@PathParam("id") int id) {
        boolean deleted = employeeServices.delete(id);
        if (!deleted)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }
}