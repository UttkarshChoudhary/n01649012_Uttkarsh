package com.example.employeemanagement;

import com.example.employeemanagement.models.employee;
import com.example.employeemanagement.services.employeeServices;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
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
    public Collection<employee> getAllEmployees() {
        return employeeServices.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getEmployeeById(@PathParam("id") int id) {
        employee emp = employeeServices.getById(id);
        if (emp == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(emp).build();
    }

    @POST
    public Response addEmployee(@Valid employee emp) {
        employee created = employeeServices.create(emp);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateEmployee(@PathParam("id") int id, @Valid employee emp) {
        employee updated = employeeServices.update(id, emp);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") int id) {
        boolean deleted = employeeServices.delete(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
