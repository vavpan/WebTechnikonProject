package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.dto.RepairDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.enums.RepairStatus;
import com.mycompany.webtechnikonproject.enums.RepairType;
import com.mycompany.webtechnikonproject.services.RepairService;
import jakarta.annotation.security.PermitAll;
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
    private RepairService repairService;

    @GET
    @Path("repair/{repairId}")
    @Produces("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public RestApiResult<RepairDto> getRepair(@PathParam("repairId") int repairId) {
        return repairService.getRepair(repairId);
    }

    @GET
    @Path("repairs")
    @Produces("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public List<RepairDto> getAllRepairs() {
        return repairService.getAllRepairs();
    }

    @GET
    @Path("/repairs/submissionDate/{submissionDate}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public List<RepairDto> readRepairsBySubmissionDate(@PathParam("submissionDate") String submissionDate) {
        return repairService.getRepairsBySubmissionDate(submissionDate);
    }

    @GET
    @Path("/repairs/owner/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public List<RepairDto> readRepairsOfOwner(@PathParam("id") int id) {
        return repairService.getRepairsOfOwner(id);
    }

    @PUT
    @Path("repair/{repairId}")
    @Consumes("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public RestApiResult<RepairDto> updateRepair(RepairDto repairDto, @PathParam("repairId") int repairId) {
        return repairService.updateRepair(repairDto, repairId);
    }

    @PUT
    @Path("repairType/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateRepairType(@PathParam("repairId") int repairId, RepairType repairType) {
        return repairService.updateRepairType(repairId, repairType);
    }

    @PUT
    @Path("repairDescription/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public RepairDto updateRepairDescription(@PathParam("repairId") int repairId, String repairDescription) {
        return repairService.updateRepairDescription(repairId, repairDescription);
    }

    @PUT
    @Path("submissionDate/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateSubmissionDate(@PathParam("repairId") int repairId, String submissionDate) {
        return repairService.updateSubmissionDate(repairId, submissionDate);
    }

    @PUT
    @Path("workDescription/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateWorkDescription(@PathParam("repairId") int repairId, String workDescription) {
        return repairService.updateWorkDescription(repairId, workDescription);
    }

    @PUT
    @Path("startDate/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateStartDate(@PathParam("repairId") int repairId, String startDate) {
        return repairService.updateStartDate(repairId, startDate);
    }

    @PUT
    @Path("endDate/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateEndDate(@PathParam("repairId") int repairId, String endDate) {
        return repairService.updateEndDate(repairId, endDate);
    }

    @PUT
    @Path("cost/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateCost(@PathParam("repairId") int repairId, double cost) {
        return repairService.updateCost(repairId, cost);
    }

    @PUT
    @Path("acceptance/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateAcceptance(@PathParam("repairId") int repairId, boolean acceptance) {
        return repairService.updateAcceptance(repairId, acceptance);
    }

    @PUT
    @Path("repairStatus/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateRepairStatus(@PathParam("repairId") int repairId, RepairStatus repairStatus) {
        return repairService.updateRepairStatus(repairId, repairStatus);
    }

    @PUT
    @Path("actualStartDate/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateActualDate(@PathParam("repairId") int repairId, String actualStartDate) {
        return repairService.updateActualStartDate(repairId, actualStartDate);
    }

    @PUT
    @Path("actualEndDate/{repairId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public RepairDto updateActualEndDate(@PathParam("repairId") int repairId, String actualEndDate) {
        return repairService.updateActualEndDate(repairId, actualEndDate);
    }

    @POST
    @Path("/repair")
    @Produces("application/json")
    @Consumes("application/json")
    @PermitAll
    public void createNewRepair(RepairDto repair) {
        repairService.createRepair(repair);
    }

    @DELETE
    @Path("repair/{repairId}")
    @Consumes("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public boolean deleteRepair(@PathParam("repairId") int repairId) {
        return repairService.deleteRepair(repairId);
    }

}
