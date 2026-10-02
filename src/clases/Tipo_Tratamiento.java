/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

/**
 * Entidad persistente que representa un tipo de tratamiento.
 * Las anotaciones JPA indican cómo ObjectDB guarda sus datos y relaciones.
 */
@Entity
public class Tipo_Tratamiento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String trat_descripcion;   // usamos la descripción como PK

    // Relación: un tipo de tratamiento puede estar en muchos traslados
    @OneToMany
    @JoinColumn(name="tras_trat", nullable=false)
    private List<Traslado> trat_traslados = new ArrayList<>();

    // Constructor vacío requerido por JPA/ObjectDB.
    public Tipo_Tratamiento() {}

    // Constructor de apoyo para crear objetos desde interfaces o pruebas.
    public Tipo_Tratamiento(String descripcion) {
        this.trat_descripcion = descripcion;
    }

    // Texto legible utilizado al imprimir o mostrar objetos.
    @Override
    public String toString() {
        return String.format("\n-----\nTipo de Tratamiento: %s "
                + "\nTraslados asociados: %d\n",
                this.trat_descripcion,
                trat_traslados.size());
    }

    public void printTraslados() {
        System.out.println("Traslados con este tratamiento: " + trat_traslados.size());
        for (Traslado t : trat_traslados) {
            System.out.println(t);
        }
    }

    // Métodos auxiliares
    public void formTrat_traslado(Traslado t) {
        trat_traslados.add(t);
    }

    public void dropTrat_traslado(Traslado t) {
        trat_traslados.remove(t);
    }

    // Getters y Setters
    public String getTrat_descripcion() { return trat_descripcion; }
    public void setTrat_descripcion(String trat_descripcion) { this.trat_descripcion = trat_descripcion; }

    public List<Traslado> getTrat_traslados() { return trat_traslados; }
    public void setTrat_traslados(List<Traslado> trat_traslados) { this.trat_traslados = trat_traslados; }
}

