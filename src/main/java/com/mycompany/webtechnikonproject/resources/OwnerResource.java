package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.dto.PropertyOwnerDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.services.OwnerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("ownerResource")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OwnerResource {

    @Inject
    private OwnerService ownerService;

    @GET
    @Path("owner/{ownerId}")
    @Produces("application/json")
    public RestApiResult<PropertyOwnerDto> getOwner(@PathParam("ownerId") int ownerId) {
        return ownerService.getOwner(ownerId);

    }

    @GET
    @Path("owner/vat/{vat}")
    @Produces("application/json")
    public RestApiResult<PropertyOwnerDto> getOwnerByVat(@PathParam("vat") int vat) {
        return ownerService.getOwnerByVat(vat);

    }

    @GET
    @Path("owner/email/{email}")
    @Produces("application/json")
    public RestApiResult<PropertyOwnerDto> getOwnerByEmail(@PathParam("email") String email) {
        return ownerService.getOwnerByEmail(email);

    }

    @PUT
    @Path("owner/{ownerId}")
    @Consumes("application/json")
    public RestApiResult<PropertyOwnerDto> updateOwner(PropertyOwnerDto ownerDto, @PathParam("ownerId") int ownerId) {
        return ownerService.updateOwner(ownerDto, ownerId);
    }

    @POST
    @Path("owner")
    @Produces("application/json")
    @Consumes("application/json")
    public void createNewOwner(PropertyOwnerDto owner) {
        ownerService.createPropertyOwner(owner);

    }

    @DELETE
    @Path("owner/{ownerId}")
    @Produces("application/json")
    @Consumes("application/json")
    public boolean deleteOwner(@PathParam("ownerId") int ownerId) {
        return ownerService.deletePropertyOwner(ownerId);
    }

}
