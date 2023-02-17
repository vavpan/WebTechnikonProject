package com.mycompany.webtechnikonproject.security;

import com.mycompany.webtechnikonproject.repository.PropertyOwnerRepository;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    @Inject
    private PropertyOwnerRepository ownerRepository;
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        Method method = resourceInfo.getResourceMethod();
        if (method.isAnnotationPresent(PermitAll.class)) {
            return;
        }
        if (method.isAnnotationPresent(DenyAll.class)) {
            crc.abortWith(Response.status(Response.Status.FORBIDDEN).entity("Access blocked for all users !!").build());
            return;
        }
        final MultivaluedMap<String, String> headers = crc.getHeaders();
        final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
        if (authorization == null || authorization.isEmpty()) {
            crc.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("You cannot access this resource").build());
            return;
        }
        final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
        String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

        if (method.isAnnotationPresent(RolesAllowed.class)) {
            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
            Set<String> rolesSetForTheResource = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

            //Is user valid?
            if (!isUserAllowed(username, password, rolesSetForTheResource)) {
                crc.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("You cannot access this resource").build());
            }
        }

    }

    private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSetExpected) {
        return rolesSetExpected.contains(ownerRepository.checkRole(username, password));
    }
}
