package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.dto.PropertyOwnerDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.model.PropertyOwner;
import com.mycompany.webtechnikonproject.services.OwnerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

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

    @GET
    @Path("owners")
    @Produces("application/json")
    public List<PropertyOwner> getAllOwners() {
        List<PropertyOwner> owners = ownerService.getAllOwners();
        return owners;

    }

    @PUT
    @Path("owner/{ownerId}")
    @Consumes("application/json")
    public RestApiResult<PropertyOwnerDto> updateOwner(PropertyOwnerDto ownerDto, @PathParam("ownerId") int ownerId) {
        return ownerService.updateOwner(ownerDto, ownerId);
    }

    @PUT
    @Path("/updateAddress/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public PropertyOwnerDto updateAddress(@PathParam("id") int id, String newAddress) {
        return ownerService.updateAddress(id, newAddress);
    }

    @PUT
    @Path("/updateEmail/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public PropertyOwnerDto updateEmail(@PathParam("id") int id, String email) {
        return ownerService.updateEmail(id, email);
    }

    @PUT
    @Path("/updateName/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public PropertyOwnerDto updateName(@PathParam("id") int id, String name) {
        return ownerService.updateName(id, name);
    }

    @PUT
    @Path("/updateSurname/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public PropertyOwnerDto updateSurname(@PathParam("id") int id, String surname) {
        return ownerService.updateSurname(id, surname);
    }

    @PUT
    @Path("/updatePassword/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public PropertyOwnerDto updatePassword(@PathParam("id") int id, String password) {
        return ownerService.updatePassword(id, password);
    }

    @PUT
    @Path("/updatePhoneNumber/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public PropertyOwnerDto updatePhoneNumber(@PathParam("id") int id, String phoneNumber) {
        return ownerService.updatePhoneNumber(id, phoneNumber);
    }

    @PUT
    @Path("/updateUsername/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public PropertyOwnerDto updateUsername(@PathParam("id") int id, String username) {
        return ownerService.updateUsername(id, username);
    }

    @PUT
    @Path("/updateVat/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public PropertyOwnerDto updateVat(@PathParam("id") int id, String vatString) {
        int vat = Integer.parseInt(vatString);
        return ownerService.updateVat(id, vat);
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
