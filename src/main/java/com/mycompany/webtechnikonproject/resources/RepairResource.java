/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.dto.RepairDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.services.OwnerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author ADMIN
 */
@Path("repairResource")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RepairResource {

    @Inject
    private OwnerService ownerService;

    @GET
    @Path("repairs")
    @Produces("application/json")
    public void getAll() {
        ownerService.getAll();
    }

    @GET
    @Path("repair/{repairId}")
    @Produces("application/json")
    public RestApiResult<RepairDto> getRepair(@PathParam("repairId") int repairId) {
        return ownerService.getRepair(repairId);
    }

    @POST
    @Path("/repair")
    @Produces("application/json")
    @Consumes("application/json")
    public void createNewRepair(RepairDto repair) {
        ownerService.createRepair(repair);
    }

    @DELETE
    @Path("repair/{repairId}")
    @Consumes("application/json")
    public boolean deleteRepair(@PathParam("repairId") int repairId) {
        return ownerService.deleteRepair(repairId);
    }

}
