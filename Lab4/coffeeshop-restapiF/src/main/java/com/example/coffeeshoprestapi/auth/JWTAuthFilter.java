package com.example.coffeeshoprestapi.auth;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.security.Key;
import java.security.Principal;
import java.util.Date;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthFilter implements ContainerRequestFilter {

    private static final String SECRET_KEY = "your-256-bit-secret"; // Keep this safe!
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {


        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            abort(requestContext);
            return;
        }

        String token = authHeader.substring("Bearer ".length());

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            String role = claims.get("role", String.class);

            System.out.println(username);

            if (username == null || role == null) {
                abort(requestContext);
                return;
            }

            setSecurityContext(requestContext, username, role);

        } catch (JwtException e) {
            e.printStackTrace();
            abort(requestContext);
        }
    }

    private void setSecurityContext(ContainerRequestContext requestContext, String username, String role) {
        SecurityContext originalContext = requestContext.getSecurityContext();

        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return () -> username;
            }

            @Override
            public boolean isUserInRole(String roleName) {
                return roleName != null && role != null && role.equalsIgnoreCase(roleName);
            }

            @Override
            public boolean isSecure() {
                return requestContext.getUriInfo().getAbsolutePath().getScheme().equals("https");
            }

            @Override
            public String getAuthenticationScheme() {
                return "Bearer";
            }
        });
    }

    private void abort(ContainerRequestContext ctx) {
        ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .header(HttpHeaders.WWW_AUTHENTICATE, "Bearer realm=\"CoffeeShop\"")
                .build());
    }

    private Key getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(); // No Base64 encoding!
        return new SecretKeySpec(keyBytes, SIGNATURE_ALGORITHM.getJcaName());
    }

}
