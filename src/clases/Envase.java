/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

@Entity
public class Envase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String env_descripcion;   // usamos la descripción como PK
    private String categoria_material;

    // Relación: un envase puede contener muchos residuos
    @OneToMany
    @JoinColumn(name="res_env", nullable=false)
    private List<Residuo> env_residuos = new ArrayList<>();

    public Envase() {}

    public Envase(String descripcion, String categoria_material) {
        this.env_descripcion = descripcion;
        this.categoria_material = categoria_material;
    }

    @Override
    public String toString() {
        return String.format("\n-----\nDescripción Envase: %s "
                + "\nCategoría material: %s "
                + "\nResiduos contenidos: %d\n",
                this.env_descripcion,
                this.categoria_material,
                env_residuos.size());
    }

    public void printResiduos() {
        System.out.println("Residuos en este envase: " + env_residuos.size());
        for (Residuo r : env_residuos) {
            System.out.println(r);
        }
    }

    // Métodos auxiliares para manejar la relación
    public void formEnv_residuo(Residuo r) {
        env_residuos.add(r);
    }

    public void dropEnv_residuo(Residuo r) {
        env_residuos.remove(r);
    }

    // Getters y Setters
    public String getEnv_descripcion() {
        return env_descripcion;
    }

    public void setEnv_descripcion(String env_descripcion) {
        this.env_descripcion = env_descripcion;
    }

    public String getCategoria_material() {
        return categoria_material;
    }

    public void setCategoria_material(String categoria_material) {
        this.categoria_material = categoria_material;
    }

    public List<Residuo> getEnv_residuos() {
        return env_residuos;
    }

    public void setEnv_residuos(List<Residuo> env_residuos) {
        this.env_residuos = env_residuos;
    }
}

