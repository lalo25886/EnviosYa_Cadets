package com.enviosya.cadet.domain;

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

public CadetEntity agregar(CadetEntity unCadete) {
        try {
        em.persist(unCadete);
        } catch (Exception e) {
            log.error("Error al agregar:" + this.getClass().toString()
                    + e.getMessage());
            return null;
        }
        return unCadete;
    }

    public CadetEntity agregar(String body) {
       try {
       Gson gson = new Gson();
       CadetEntity unCadete = gson.fromJson(body, CadetEntity.class);
        em.persist(unCadete);
        return unCadete;
        } catch (Exception e) {
            log.error("Error al agregar:" + this.getClass().toString()
                    + e.getMessage());
            return null;
        }
    }
    public CadetEntity modificar(Long id, String nombreNuevo) {
        try {
            CadetEntity unCadete = em.find(CadetEntity.class, id);
            unCadete.setNombre(nombreNuevo);
            em.merge(unCadete);
            return unCadete;
        } catch (Exception e) {
            log.error("Error al modificar:" + this.getClass().toString()
                    + e.getMessage());
            return null;
        }
    }
      public CadetEntity modificar(CadetEntity unCadete) {
       try {
        em.merge(unCadete);
        return unCadete;
        } catch (Exception e) {
            log.error("Error al modificar:" + this.getClass().toString()
                    + e.getMessage());
            return null;
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

    public Cadet buscar(Long id) {
        CadetEntity unCadeteEntity = em.find(CadetEntity.class, id);
        Cadet unCadete = new Cadet();
        unCadete .setId(unCadeteEntity.getId());
        unCadete .setNombre(unCadeteEntity.getNombre());
        return unCadete;
    }

    public List<CadetEntity> buscar(String nombre) {
        List<CadetEntity> listaCadetes =
        em.createQuery("select c from CadetEntity c "
        + "where c.nombre = :nombre")
        .setParameter("nombre", nombre).getResultList();
        return listaCadetes;
    }
   public String buscarCadete(Long id) {
        CadetEntity unCadeteEntity = em.find(CadetEntity.class, id);
        return unCadeteEntity.getEmail();
    }
}