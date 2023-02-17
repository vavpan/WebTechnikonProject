package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.dto.PropertyOwnerDto;
import com.mycompany.webtechnikonproject.services.OwnerService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author ADMIN
 */
@Path("/loginResource")
public class LoginResource {

    @Inject
    private OwnerService ownerService;

    @POST
    @Path("/login")
    @RolesAllowed({"ADMIN", "USER"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PropertyOwnerDto loginUser(@HeaderParam("Authorization") String authorization) {
        return ownerService.getUser(authorization);

    }
}