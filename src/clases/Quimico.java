/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

@Entity
public class Quimico implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String quim_nombre;   // usamos el nombre como PK
    private String tipo_peligrosidad;

    // Relación: un químico puede aparecer en varias composiciones
    @OneToMany
    @JoinColumn(name="comp_quim", nullable=false)
    private List<Composicion_Quimico> quim_composiciones = new ArrayList<>();

    public Quimico() {}

    public Quimico(String nombre, String tipo_peligrosidad) {
        this.quim_nombre = nombre;
        this.tipo_peligrosidad = tipo_peligrosidad;
    }

    @Override
    public String toString() {
        return String.format("\n-----\nNombre Químico: %s "
                + "\nTipo de peligrosidad: %s "
                + "\nUsado en composiciones: %d\n",
                this.quim_nombre,
                this.tipo_peligrosidad,
                quim_composiciones.size());
    }

    public void printComposiciones() {
        System.out.println("Composiciones con este químico: " + quim_composiciones.size());
        for (Composicion_Quimico cq : quim_composiciones) {
            System.out.println(cq);
        }
    }

    // Métodos auxiliares para manejar la relación
    public void formQuim_composicion(Composicion_Quimico cq) {
        quim_composiciones.add(cq);
    }

    public void dropQuim_composicion(Composicion_Quimico cq) {
        quim_composiciones.remove(cq);
    }

    // Getters y Setters
    public String getQuim_nombre() {
        return quim_nombre;
    }

    public void setQuim_nombre(String quim_nombre) {
        this.quim_nombre = quim_nombre;
    }

    public String getTipo_peligrosidad() {
        return tipo_peligrosidad;
    }

    public void setTipo_peligrosidad(String tipo_peligrosidad) {
        this.tipo_peligrosidad = tipo_peligrosidad;
    }

    public List<Composicion_Quimico> getQuim_composiciones() {
        return quim_composiciones;
    }

    public void setQuim_composiciones(List<Composicion_Quimico> quim_composiciones) {
        this.quim_composiciones = quim_composiciones;
    }
}
