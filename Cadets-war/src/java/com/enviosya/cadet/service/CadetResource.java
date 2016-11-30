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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.PersistenceException;
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
    public Response agregar(String body) {
        Gson gson = new Gson();
        String  ret = gson.toJson("Error al agregar un cadete. "
                + "Verifique los datos ingresados");
        String existe = "El cadete ya existe en la base de datos";
        Response r;
        String vacio = "";
        CadetEntity u;
        CadetEntity creado;
        try {
            u = gson.fromJson(body, CadetEntity.class);
            if (cadetBean.existeCadete(u.getCi())) {
                return Response
                        .status(Response.Status.ACCEPTED)
                        .entity(existe)
                        .build();
            }
            if (u.getEmail().equalsIgnoreCase(vacio)
                || u.getNombre().equalsIgnoreCase(vacio)
                || u.getVehiculoMatricula().equalsIgnoreCase(vacio)
                || u.getVehiculoDescripcion().equalsIgnoreCase(vacio)) {
                return Response
                        .status(Response.Status.ACCEPTED)
                        .entity(ret)
                        .build();
            }
            creado = cadetBean.agregar(u);
        } catch (DatoErroneoException ex) {
           return Response
                   .status(Response.Status.ACCEPTED)
                   .entity(ret)
                   .build();
        } catch (EntidadNoExisteException ex) {
           return Response
                   .status(Response.Status.ACCEPTED)
                   .entity(existe).build();
        }
        return Response
                    .status(Response.Status.CREATED)
                    .entity(gson.toJson(creado))
                    .build();
    }

    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modificar(String body) throws EntidadNoExisteException {
        Gson gson = new Gson();
        String  ret = gson.toJson("Error al modificar un cadete. "
                + "Verifique los datos ingresados");
        String existe = "El cadete no existe en la base de datos";
        CadetEntity u = gson.fromJson(body, CadetEntity.class);
        Response r;
        CadetEntity modificado;
        try {
            if (!cadetBean.existeCadete(u.getCi())) {
                return Response
                        .status(Response.Status.ACCEPTED)
                        .entity(existe)
                        .build();
            }
            modificado = cadetBean.modificar(u);
            r = Response
                .status(Response.Status.CREATED)
                .entity(gson.toJson(modificado))
                .build();
        } catch (EntidadNoExisteException e) {
            r = Response
                .status(Response.Status.ACCEPTED)
                .entity(ret)
                .build();
            return r;
        }
        return r;
    }
    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response eliminar(String body) {
        Gson gson = new Gson();
        String  ret = gson.toJson("Error al eliminar un cadete. "
                + "Verifique los datos.");
        String ok = "El cadete fue eliminado exitosamente";
        String existe = "El cadete no existe en la base de datos";
        CadetEntity u = gson.fromJson(body, CadetEntity.class);
        Response r;
        Boolean modificado;
        try {
            if (!cadetBean.existeCadete(u.getCi())) {
                return Response
                        .status(Response.Status.ACCEPTED)
                        .entity(existe)
                        .build();
            }
            modificado = cadetBean.eliminar(u);
            r = Response
                .status(Response.Status.CREATED)
                .entity(gson.toJson(ok))
                .build();
        } catch (EntidadNoExisteException ex) {
            r = Response
                    .status(Response.Status.ACCEPTED)
                    .entity(ret)
                    .build();
        }
        return r;
    }
    @GET
    @Path("getCadetEntity/{id}")
    @Consumes(MediaType.TEXT_HTML)
    public String getCadeteEntidad(@PathParam("id") String id) 
        throws EntidadNoExisteException {
        Gson gson = new Gson();
        String existe = "El cadete no existe en la base de datos";
        CadetEntity unCadet = new CadetEntity();
        unCadet.setId(Long.parseLong(id));
        try {
        if (!cadetBean.existeCadete(unCadet.getCi())) {
                return "-5";
        }
        CadetEntity cad = cadetBean.buscarCadeteEntidad(unCadet.getId());
        String retorno  = gson.toJson(cad, CadetEntity.class);
        return retorno;
        } catch (PersistenceException e) {
            return "-5";
        }        
    }
}
