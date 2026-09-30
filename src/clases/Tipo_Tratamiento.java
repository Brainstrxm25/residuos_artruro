/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

@Entity
public class Tipo_Tratamiento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String trat_descripcion;

    @OneToMany
    @JoinColumn(name="tras_trat", nullable=false)
    private List<Traslado> trat_traslados = new ArrayList<>();

    public Tipo_Tratamiento() {}

    public Tipo_Tratamiento(String descripcion) {
        this.trat_descripcion = descripcion;
    }

    @Override
    public String toString() {
        return String.format("\n-----\nID: %d | Tipo de Tratamiento: %s "
                + "\nTraslados asociados: %d\n",
                this.id,
                this.trat_descripcion,
                trat_traslados.size());
    }

    public void printTraslados() {
        System.out.println("Traslados con este tratamiento: " + trat_traslados.size());
        for (Traslado t : trat_traslados) {
            System.out.println(t);
        }
    }

    // Métodos form y drop
    public void formTrat_traslado(Traslado t) {
        if (t != null && !trat_traslados.contains(t)) {
            trat_traslados.add(t);
            t.setTratamiento(this);
        }
    }

    public void dropTrat_traslado(Traslado t) {
        if (trat_traslados.remove(t)) {
            if (t.getTratamiento() == this) {
                t.setTratamiento(null);
            }
        }
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTrat_descripcion() { return trat_descripcion; }
    public void setTrat_descripcion(String trat_descripcion) { this.trat_descripcion = trat_descripcion; }

    public List<Traslado> getTrat_traslados() { return trat_traslados; }
    public void setTrat_traslados(List<Traslado> trat_traslados) { this.trat_traslados = trat_traslados; }
}