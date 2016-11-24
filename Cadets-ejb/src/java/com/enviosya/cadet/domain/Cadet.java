package com.enviosya.cadet.domain;

import java.util.Objects;

/**
 *
 * @author Gonzalo
 */
public class Cadet {
     private Long id;
    private Long ci;
    private String nombre;
    private String apellido;
    private String email;
    private String vehiculoMatricula;
    private String vehiculoDescripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCi() {
        return ci;
    }

    public void setCi(Long ci) {
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
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cadet other = (Cadet) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cadet{" + "ID=" + id + ", "
                + "Nombre=" + nombre + ", "
                + "Apellido=" + apellido + '}';
    }

   
    
}
