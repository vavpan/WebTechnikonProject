package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.dto.RepairDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.model.Repair;
import com.mycompany.webtechnikonproject.services.AdminService;
import com.mycompany.webtechnikonproject.services.OwnerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdminResource {

    @Inject
    private AdminService adminService;

    @Inject
    private OwnerService ownerService;

    @GET
    @Path("pendingRepairs")
    @Produces("application/json")
    public List<RepairDto> getPendingRepairs() {
        List<Repair> repairs = adminService.getPendingRepairs();
        return repairs.stream().map(RepairDto::new).collect(Collectors.toList());
    }

    @GET
    @Path("actualDatesPendingRepairs")
    @Produces("application/json")
    public List<Repair> getActualDatesOfPendingRepairs() {
        List<Repair> repairs = adminService.displayActualDatesOfPendingRepairs();
        return repairs;

    }

    @PUT
    @Path("proposeCostsAndDates/{repairId}")
    @Consumes("application/json")
    public RestApiResult<RepairDto> proposeCostsAndDates(RepairDto repairDto, @PathParam("repairId") int repairId) {
        return ownerService.updateRepair(repairDto, repairId);
    }

}
