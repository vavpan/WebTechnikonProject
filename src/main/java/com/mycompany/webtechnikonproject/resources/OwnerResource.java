package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.dto.PropertyOwnerDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.services.OwnerService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("ownerResource")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OwnerResource {

    @Inject
    private OwnerService ownerService;

    @GET
    @Path("owner/{ownerId}")
    @Produces("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public RestApiResult<PropertyOwnerDto> getOwner(@PathParam("ownerId") int ownerId) {
        return ownerService.getOwner(ownerId); 

    }

    @GET
    @Path("owner/vat/{vat}")
    @Produces("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public RestApiResult<PropertyOwnerDto> getOwnerByVat(@PathParam("vat") int vat) {
        return ownerService.getOwnerByVat(vat);

    }

    @GET
    @Path("owner/email/{email}")
    @Produces("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public RestApiResult<PropertyOwnerDto> getOwnerByEmail(@PathParam("email") String email) {
        return ownerService.getOwnerByEmail(email); 

    }

    @GET
    @Path("owners")
    @Produces("application/json")
    @RolesAllowed({"ADMIN"})
    public List<PropertyOwnerDto> getAllOwners() {
        return ownerService.getAllOwners();

    }  

    @PUT
    @Path("owner/{ownerId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public RestApiResult<PropertyOwnerDto> updateOwner(PropertyOwnerDto ownerDto, @PathParam("ownerId") int ownerId) {
        return ownerService.updateOwner(ownerDto, ownerId);
    }

    @PUT
    @Path("/updateAddress/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public PropertyOwnerDto updateAddress(@PathParam("id") int id, String newAddress) {
        return ownerService.updateAddress(id, newAddress);
    }

    @PUT
    @Path("/updateEmail/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public PropertyOwnerDto updateEmail(@PathParam("id") int id, String email) {
        return ownerService.updateEmail(id, email);
    }

    @PUT
    @Path("/updateName/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public PropertyOwnerDto updateName(@PathParam("id") int id, String name) {
        return ownerService.updateName(id, name);
    }

    @PUT
    @Path("/updateSurname/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public PropertyOwnerDto updateSurname(@PathParam("id") int id, String surname) {
        return ownerService.updateSurname(id, surname);
    }
 
    @PUT 
    @Path("/updatePassword/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN) 
    @RolesAllowed({"ADMIN", "USER"})
    public PropertyOwnerDto updatePassword(@PathParam("id") int id, String password) {
        return ownerService.updatePassword(id, password);
    }

    @PUT
    @Path("/updatePhoneNumber/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public PropertyOwnerDto updatePhoneNumber(@PathParam("id") int id, String phoneNumber) {
        return ownerService.updatePhoneNumber(id, phoneNumber);
    }

    @PUT 
    @Path("/updateUsername/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public PropertyOwnerDto updateUsername(@PathParam("id") int id, String username) {
        return ownerService.updateUsername(id, username);
    }

    @PUT
    @Path("/updateVat/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public PropertyOwnerDto updateVat(@PathParam("id") int id, String vatString) {
        int vat = Integer.parseInt(vatString);
        return ownerService.updateVat(id, vat);
    }

    @POST
    @Path("owner")
    @Produces("application/json")
    @Consumes("application/json")
    @PermitAll
    public PropertyOwnerDto createNewOwner(PropertyOwnerDto owner) {
        return ownerService.createPropertyOwner(owner);
    }

    @DELETE
    @Path("owner/{ownerId}")
    @Produces("application/json")
    @Consumes("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public boolean deleteOwner(@PathParam("ownerId") int ownerId) {
        return ownerService.deletePropertyOwner(ownerId);
    }

}
 