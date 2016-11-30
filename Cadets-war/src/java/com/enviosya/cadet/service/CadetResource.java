/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enviosya.cadet.service;

import com.enviosya.cadet.domain.CadetBean;
import com.enviosya.cadet.exception.DatoErroneoException;
import com.enviosya.cadet.exception.EntidadNoExisteException;
import com.enviosya.cadet.persistence.CadetEntity;
import com.google.gson.Gson;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Gonzalo
 */
@Path("cadet")
public class CadetResource {
    @EJB
    private CadetBean cadetBean;

    @Context
    private UriInfo context;

    public CadetResource() {
    }

    @GET
    @Path("getCadets")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        List<CadetEntity> list = cadetBean.listar();
        Gson gson = new Gson();
        return gson.toJson(list);
    }
    @GET
    @Path("getCadet/{id}")
    @Consumes(MediaType.TEXT_HTML)
    public String getCadeteNotificar(@PathParam("id") String id) 
    throws EntidadNoExisteException {
        CadetEntity unCadet = new CadetEntity();
        unCadet.setId(Long.parseLong(id));
        String retorno = cadetBean.buscarCadete(unCadet.getId());
        return retorno;
    }

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agregar(String body) throws DatoErroneoException {
        Gson gson = new Gson();
        Response r;
        CadetEntity creado = null;
        try {
            CadetEntity u = gson.fromJson(body, CadetEntity.class);
            creado = cadetBean.agregar(u);
        } catch (DatoErroneoException e) {
            r = Response
                    .status(Response.Status.CONFLICT)
                    .entity("Cadet")
                    .build();
            return r;
        }
        r = Response
                .status(Response.Status.CREATED)
                .entity(gson.toJson(creado))
                .build();
        return r;
    }

    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modificar(String body) throws EntidadNoExisteException {
        Gson gson = new Gson();
        CadetEntity u = gson.fromJson(body, CadetEntity.class);
        Response r;
        CadetEntity modificado = null;
        try {
            modificado = cadetBean.modificar(u);
        } catch (EntidadNoExisteException e) {
             r = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Cadet")
                    .build();
             return r;
        }
        r = Response
                .status(Response.Status.CREATED)
                .entity(gson.toJson(modificado))
                .build();
        return r;
    }
    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response eliminar(String body) {
        Gson gson = new Gson();
        CadetEntity u = gson.fromJson(body, CadetEntity.class);
        Response r;
        Boolean modificado = cadetBean.eliminar(u);
        if (!modificado) {
            r = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Cadete")
                    .build();
        } else {
            r = Response
                    .status(Response.Status.CREATED)
                    .entity(gson.toJson(modificado))
                    .build();
        }
        return r;
    }
    @GET
    @Path("getCadetEntity/{id}")
    @Consumes(MediaType.TEXT_HTML)
    public String getCadeteEntidad(@PathParam("id") String id) 
    throws EntidadNoExisteException {
        CadetEntity unCadet = new CadetEntity();
        unCadet.setId(Long.parseLong(id));
        CadetEntity cad = cadetBean.buscarCadeteEntidad(unCadet.getId());
        Gson gson = new Gson();
        String retorno  = gson.toJson(cad, CadetEntity.class);
        return retorno;
    }
}
