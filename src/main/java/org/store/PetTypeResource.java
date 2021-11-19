package com.example.petstore;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1/pettypes")
@Produces("application/json")
public class PetTypeResource {

    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "All Results for Pet Types", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))) })
    @GET
    public Response getPetTypes(){
        List<PetType> pettypes=PetType.listAll();
        return Response.ok(pettypes).build();
    }

    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Result", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))),
            @APIResponse(responseCode = "404", description = "No Result for the Given Pet Type Id.") })
    @GET
    @Path("{id}")
    public Response getPetType(@PathParam("id") int id) {

        PetType petType=PetType.findById(id);
        if(petType == null){
            return Response.ok("Pet type ID invalid").build();
        }else {
            return Response.ok(petType).build();
        }
    }

    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Pet Type Addition Successful.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))),
            @APIResponse(responseCode = "404", description = "Pet Type Addition Failed.") })
    @POST
    @Path("addpettype")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addPetType(@RequestBody PetType petType) {
        PetType.persist(petType);
        return Response.ok(petType).build();
    }


    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Pet Type Deletion Successful", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))),
            @APIResponse(responseCode = "404", description = "Pet Type Deletion Failed.") })
    @DELETE
    @Transactional
    @Path("delete/{typeId}")
    public Response deletePetType(@PathParam("typeId") int id) {

        System.out.println("Deleting...");

        Boolean isDeleted=PetType.deleteById(id);

        if(isDeleted){
            return Response.ok("Pet Type Deletion Successful").build();
        }else {
            return Response.ok("Pet Type Deletion Failed.").build();
        }
    }


    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Pet type Updated", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "PetType"))),
            @APIResponse(responseCode = "404", description = "Pet type not exist.") })
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("update")
    public Response updatePet(@RequestBody PetType petType) {

        int n=PetType.update("petType= ?1 where id = ?2",petType.getPetType(),petType.getId());

        if(n == 0){
            return Response.ok("Invalid Pet Type").build();
        }else {
            PetType pt=PetType.findById(petType.getId());
            return Response.ok(pt).build();
        }

    }


}
