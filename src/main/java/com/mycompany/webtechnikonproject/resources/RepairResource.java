/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.dto.RepairDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.enums.RepairStatus;
import com.mycompany.webtechnikonproject.enums.RepairType;
import com.mycompany.webtechnikonproject.services.OwnerService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

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
    @Path("repair/{repairId}")
    @Produces("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public RestApiResult<RepairDto> getRepair(@PathParam("repairId") int repairId) {
        return ownerService.getRepair(repairId);
    }

    @GET
    @Path("repairs")
    @Produces("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public List<RepairDto> getAllRepairs() {
        return ownerService.getAllRepairs();
    }

    @GET
    @Path("/repairs/submissionDate/{submissionDate}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public List<RepairDto> readRepairsBySubmissionDate(@PathParam("submissionDate") String submissionDate) {
        return ownerService.getRepairsBySubmissionDate(submissionDate);
    }

    @GET
    @Path("/repairs/owner/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public List<RepairDto> readRepairsOfOwner(@PathParam("id") int id) {
        return ownerService.getRepairsOfOwner(id);
    }

    @PUT
    @Path("repair/{repairId}")
    @Consumes("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public RestApiResult<RepairDto> updateRepair(RepairDto repairDto, @PathParam("repairId") int repairId) {
        return ownerService.updateRepair(repairDto, repairId);
    }

    @PUT
    @Path("repairType/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateRepairType(@PathParam("repairId") int repairId, RepairType repairType) {
        return ownerService.updateRepairType(repairId, repairType);
    }

    @PUT
    @Path("repairDescription/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public RepairDto updateRepairDescription(@PathParam("repairId") int repairId, String repairDescription) {
        return ownerService.updateRepairDescription(repairId, repairDescription);
    }

    @PUT
    @Path("submissionDate/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateSubmissionDate(@PathParam("repairId") int repairId, String submissionDate) {
        return ownerService.updateSubmissionDate(repairId, submissionDate);
    }

    @PUT
    @Path("workDescription/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateWorkDescription(@PathParam("repairId") int repairId, String workDescription) {
        return ownerService.updateWorkDescription(repairId, workDescription);
    }

    @PUT
    @Path("startDate/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateStartDate(@PathParam("repairId") int repairId, String startDate) {
        return ownerService.updateStartDate(repairId, startDate);
    }

    @PUT
    @Path("endDate/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateEndDate(@PathParam("repairId") int repairId, String endDate) {
        return ownerService.updateEndDate(repairId, endDate);
    }

    @PUT
    @Path("cost/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateCost(@PathParam("repairId") int repairId, double cost) {
        return ownerService.updateCost(repairId, cost);
    }

    @PUT
    @Path("acceptance/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateAcceptance(@PathParam("repairId") int repairId, boolean acceptance) {
        return ownerService.updateAcceptance(repairId, acceptance);
    }

    @PUT
    @Path("repairStatus/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateRepairStatus(@PathParam("repairId") int repairId, RepairStatus repairStatus) {
        return ownerService.updateRepairStatus(repairId, repairStatus);
    }

    @PUT
    @Path("actualStartDate/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateActualDate(@PathParam("repairId") int repairId, String actualStartDate) {
        return ownerService.updateActualStartDate(repairId, actualStartDate);
    }

    @PUT
    @Path("actualEndDate/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateActualEndDate(@PathParam("repairId") int repairId, String actualEndDate) {
        return ownerService.updateActualEndDate(repairId, actualEndDate);
    }

    @POST
    @Path("/repair")
    @Produces("application/json")
    @Consumes("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public void createNewRepair(RepairDto repair) {
        ownerService.createRepair(repair);
    }

    @DELETE
    @Path("repair/{repairId}")
    @Consumes("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public boolean deleteRepair(@PathParam("repairId") int repairId) {
        return ownerService.deleteRepair(repairId);
    }

}
