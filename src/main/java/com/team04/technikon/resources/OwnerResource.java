package com.team04.technikon.resources;

import com.team04.technikon.dto.PropertyOwnerDto;
import com.team04.technikon.dto.RestApiResult;
import com.team04.technikon.services.OwnerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("ownerResource")
public class OwnerResource {
    
    @Inject
    private OwnerService ownerService;
    
    
    
    @GET
    @Path("owner/{ownerId}")
    @Produces("application/json")
    public RestApiResult<PropertyOwnerDto> getOwner(@PathParam("ownerId") int ownerId){
        return ownerService.getOwner(ownerId);
        
    }
    
    @POST
    @Path("owner")
    @Produces("application/json")
    @Consumes("application/json")
    public void createNewOwner(PropertyOwnerDto owner) {
        ownerService.registerNewOwnerDto(owner);
        
    }
    
}
