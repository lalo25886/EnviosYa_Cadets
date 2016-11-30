package com.enviosya.cadet.domain;

import com.enviosya.cadet.exception.DatoErroneoException;
import com.enviosya.cadet.exception.EntidadNoExisteException;
import com.enviosya.cadet.persistence.CadetEntity;
import com.google.gson.Gson;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Gonzalo
 */
@Stateless
@LocalBean
public class CadetBean {
 static Logger log = Logger.getLogger("FILE");
    @PersistenceContext
    private EntityManager em;
    @PostConstruct
    private void init() {
    }

public CadetEntity agregar(CadetEntity unCadete) throws DatoErroneoException {
        try {
        em.persist(unCadete);
        } catch (Exception e) {
            log.error("Error al agregar:" + this.getClass().toString()
                    + e.getMessage());
            throw new DatoErroneoException("Error al agregar un cadete. "
                    + "Verifique los datos ingresados.");
        }
        return unCadete;
    }

    public CadetEntity agregar(String body) 
            throws DatoErroneoException {
       CadetEntity unCadete = null;
        try {
            Gson gson = new Gson();
            unCadete = gson.fromJson(body, CadetEntity.class);
             em.persist(unCadete);
        } catch (Exception e) {
            log.error("Error al agregar:" + this.getClass().toString()
                    + e.getMessage());
            throw new DatoErroneoException("Error al agregar un cadete. "
                    + "Verifique los datos ingresados.");
        }
       return unCadete;
    }
    public CadetEntity modificar(Long id, String nombreNuevo)
            throws EntidadNoExisteException {
        try {
            CadetEntity unCadete = em.find(CadetEntity.class, id);
            unCadete.setNombre(nombreNuevo);
            em.merge(unCadete);
            return unCadete;
        } catch (Exception e) {
            log.error("Error al modificar:" + this.getClass().toString()
                    + e.getMessage());
            throw new EntidadNoExisteException("Error al modificar un cadete. "
                    + "El cadete con el id: " + id + " no se encuentra.");
        }
    }
      public CadetEntity modificar(CadetEntity unCadete) 
              throws EntidadNoExisteException {
       try {
        em.merge(unCadete);
        return unCadete;
        } catch (Exception e) {
            log.error("Error al modificar:" + this.getClass().toString()
                    + e.getMessage());
            throw new EntidadNoExisteException("Error al modificar un cadete. "
                    + "El cadete con el id: " + unCadete.getId() + " no "
                    + "se encuentra.");
        }
    }
     public boolean eliminar(CadetEntity unCadete) {
        try {
            CadetEntity aBorrar = em.find(CadetEntity.class,
                    unCadete.getId());
            em.remove(aBorrar);
            return true;
        } catch (Exception e) {
            log.error("Error al eliminar:" + this.getClass().toString()
                    + e.getMessage());
        }
        return false;
    }

    public boolean eliminar(Long id) {
        try {
        CadetEntity unCadete = em.find(CadetEntity.class, id);
        em.remove(unCadete);
        return true;
         } catch (Exception e) {
            log.error("Error al eliminar:" + this.getClass().toString()
                    + e.getMessage());
        }
        return false;
    }

    public List<CadetEntity> listar() {        
        List<CadetEntity> list =
                em.createQuery("select u from CadetEntity u").getResultList();
        return list;
    }

    public Cadet buscar(Long id)  throws EntidadNoExisteException {
        CadetEntity unCadeteEntity = null;
        Cadet unCadete = null;
        try {
            unCadeteEntity = em.find(CadetEntity.class, id);
            unCadete = new Cadet();
            unCadete .setId(unCadeteEntity.getId());
            unCadete .setNombre(unCadeteEntity.getNombre());
        } catch (Exception e) {
            throw new EntidadNoExisteException("Error al buscar un cadete. "
                    + "El cadete con el id: " + id + " no "
                    + "se encuentra.");
        }
        return unCadete;
    }

    public List<CadetEntity> buscar(String nombre) 
            throws EntidadNoExisteException {
        List<CadetEntity> listaCadetes = null;
        try {
            listaCadetes =
            em.createQuery("select c from CadetEntity c "
            + "where c.nombre = :nombre")
            .setParameter("nombre", nombre).getResultList();
        } catch (Exception e) {
            throw new EntidadNoExisteException("Error al buscar un cadete. "
                    + "El cadete con el nombre: " + nombre + " no "
                    + "se encuentra.");
        }
        return listaCadetes;
    }
    public String buscarCadete(Long id) throws EntidadNoExisteException {
        CadetEntity unCadeteEntity = null;
        try {
            unCadeteEntity = em.find(CadetEntity.class, id);
        } catch (Exception e) {
            throw new EntidadNoExisteException("Error al buscar el mail"
                    + " del cadete con id: " + id + ". El cadete no "
                    + "se encuentra.");
        }
        return unCadeteEntity.getEmail();
    }
    public CadetEntity buscarCadeteEntidad(Long id) throws EntidadNoExisteException {
        CadetEntity unCadeteEntity = null;
        try {
            unCadeteEntity = em.find(CadetEntity.class, id);
        } catch (Exception e) {
            throw new EntidadNoExisteException("Error al buscar el mail"
                    + " del cadete con id: " + id + ". El cadete no "
                    + "se encuentra.");
        }
        return unCadeteEntity;
    }
}