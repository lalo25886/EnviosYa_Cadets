package com.enviosya.persistence.cadet;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gonzalo
 */
@Entity
@XmlRootElement
public class CadetEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

     @NotNull
   @Column(length = 300,unique = true)
   private String ci;

   @NotNull
   @Column(length = 300)
   private String nombre;

   @Column(length = 300)
   private String apellido;
   
   @NotNull
   @Column(length = 300)
   private String email;
    
   @NotNull
   @Column(length = 300)
   private String vehiculoMatricula;
   
   @NotNull
   @Column(length = 300)
   private String vehiculoDescripcion;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVehiculoMatricula() {
        return vehiculoMatricula;
    }

    public void setVehiculoMatricula(String vehiculoMatricula) {
        this.vehiculoMatricula = vehiculoMatricula;
    }

    public String getVehiculoDescripcion() {
        return vehiculoDescripcion;
    }

    public void setVehiculoDescripcion(String vehiculoDescripcion) {
        this.vehiculoDescripcion = vehiculoDescripcion;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CadetEntity)) {
            return false;
        }
        CadetEntity other = (CadetEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.enviosya.persistence.cadet.CadetEntity[ id=" + id + " ]";
    }
    
}
