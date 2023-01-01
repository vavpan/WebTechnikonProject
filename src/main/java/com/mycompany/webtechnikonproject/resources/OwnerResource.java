package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.dto.PropertyOwnerDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.services.OwnerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("ownerResource")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OwnerResource {

    @GET
    public Response ping() {
        return Response
                .ok("pang")
                .build();
    }

    @Inject
    private OwnerService ownerService;

    @GET
    @Path("owner/{ownerId}")
    @Produces("application/json")
    public RestApiResult<PropertyOwnerDto> getOwner(@PathParam("ownerId") int ownerId) {
        return ownerService.getOwner(ownerId);

    }

    @POST
    @Path("owner")
    @Produces("application/json")
    @Consumes("application/json")
    public void createNewOwner(PropertyOwnerDto owner) {
        ownerService.createPropertyOwner(owner);

    }

}
