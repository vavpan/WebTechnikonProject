/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.services.OwnerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
    public Response ping() {
        return Response
                .ok("repairPing")
                .build();
    }

    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok().entity(ownerService.getAll()).build();
    }

}
