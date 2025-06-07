package com.example.coffeeshoprestapi.resources;

import com.example.coffeeshoprestapi.models.Coffee;
import com.example.coffeeshoprestapi.services.CoffeeService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;

@Path("/coffees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoffeeShopResource {

    @Inject
    private CoffeeService coffeeService;

    @GET
    public Response getAllCoffees() {
        List<Coffee> coffees = coffeeService.getAllCoffees();
        return Response.ok(coffees).build();
    }

    @GET
    @Path("/{id}")
    public Response getCoffee(@PathParam("id") int id) {
        Coffee coffee = coffeeService.getById(id);
        if (coffee == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(coffee).build();
    }

    @POST
    public Response addCoffee(@Context SecurityContext sc, Coffee coffee) {
        if (!sc.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Admins only").build();
        }
        Coffee created = coffeeService.addCoffee(coffee);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCoffee(@PathParam("id") int id, Coffee coffee) {
        Coffee updated = coffeeService.updateCoffee(id, coffee);
        if (updated == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCoffee(@Context SecurityContext sc, @PathParam("id") int id) {
        if (!sc.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Admins only").build();
        }
        boolean deleted = coffeeService.deleteCoffee(id);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}