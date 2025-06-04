package com.example.coffeeshoprestapi.app;

import com.example.coffeeshoprestapi.auth.JWTAuthFilter;
import com.example.coffeeshoprestapi.resources.CoffeeShopResource;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class CoffeeShopApp extends Application {
    // Scans for @Path-annotated classes in the package
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(CoffeeShopResource.class);
        classes.add(JWTAuthFilter.class);
        return classes;
    }
}
