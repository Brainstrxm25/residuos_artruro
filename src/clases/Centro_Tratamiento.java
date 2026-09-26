/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

@Entity
public class Centro_Tratamiento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String cen_descripcion;   // usamos la descripción como PK
    private String cen_ubicacion;

    // Relación: un centro puede recibir muchos traslados
    @OneToMany
    @JoinColumn(name="tras_cen", nullable=false)
    private List<Traslado> cen_traslados = new ArrayList<>();

    public Centro_Tratamiento() {}

    public Centro_Tratamiento(String descripcion, String ubicacion) {
        this.cen_descripcion = descripcion;
        this.cen_ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return String.format("\n-----\nCentro: %s "
                + "\nUbicación: %s "
                + "\nTraslados recibidos: %d\n",
                this.cen_descripcion,
                this.cen_ubicacion,
                cen_traslados.size());
    }

    public void printTraslados() {
        System.out.println("Traslados recibidos: " + cen_traslados.size());
        for (Traslado t : cen_traslados) {
            System.out.println(t);
        }
    }

    // Métodos auxiliares para manejar la relación
    public void formCen_traslado(Traslado t) {
        cen_traslados.add(t);
    }

    public void dropCen_traslado(Traslado t) {
        cen_traslados.remove(t);
    }

    // Getters y Setters
    public String getCen_descripcion() { return cen_descripcion; }
    public void setCen_descripcion(String cen_descripcion) { this.cen_descripcion = cen_descripcion; }

    public String getCen_ubicacion() { return cen_ubicacion; }
    public void setCen_ubicacion(String cen_ubicacion) { this.cen_ubicacion = cen_ubicacion; }

    public List<Traslado> getCen_traslados() { return cen_traslados; }
    public void setCen_traslados(List<Traslado> cen_traslados) { this.cen_traslados = cen_traslados; }
}

