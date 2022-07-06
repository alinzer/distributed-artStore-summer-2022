package edu.yu.cs.hub;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.springframework.web.reactive.function.client.WebClient;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Path("/hub")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Hub {
    
    public static void main(String[] args) throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost());
        System.out.println(InetAddress.getLocalHost().getHostAddress());
    }
    
    @GET
    public String testGet() {
        System.out.println("called server");
        return "successfully called the server";
    }


    //Gallery talking to hub (during user POST to create a gallery)
    //Hub talks back to gallery and returns the updated list of the IPs 
    @POST
    @Transactional
    // , @Context UriInfo uriInfo
    public Response create(galleryInfo gi, @Context UriInfo uriInfo) {
        gi.persist();

        Map<Long, InetAddress> allGI = gi.toMap();
        for (long currentId : allGI.keySet()) {
            //Code to update each IP with the new data 
            // WebClient webClient = WebClient.create(allGI.get(currentId));

            // String response = webClient.post()
            //         .uri("/hub")
            //         .contentType(MediaType.APPLICATION_JSON)
            //         .bodyValue("{\"galleryId\":\"" + id + "\",\"ia\":\"" + this.ia + "\"}")
            //         .retrieve()
            //         .bodyToMono(String.class).block();
        }

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Long.toString(gi.galleryId));
        return Response.created(uriBuilder.build()).entity(gi).status(Status.CREATED).build();
        
        // System.out.println(gi.galleryId + gi.ia.toString());
        // return (gi.galleryId + gi.ia.toString());
    }
}
