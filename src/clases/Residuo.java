/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

@Entity
public class Residuo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String res_nombre;   // usamos el nombre como PK
    private double cantidad_total;

    // Relación: cada residuo pertenece a una empresa
    @ManyToOne
    @JoinColumn(name="res_emp", nullable=false)
    private Empresa empresa;

    // Relación: cada residuo está contenido en un envase
    @ManyToOne
    @JoinColumn(name="res_env", nullable=false)
    private Envase envase;

    // Relación: un residuo puede tener varias composiciones químicas
    @OneToMany
    @JoinColumn(name="comp_res", nullable=false)
    private List<Composicion_Quimico> res_composiciones = new ArrayList<>();

    public Residuo() {}

    public Residuo(String nombre, double cantidad_total) {
        this.res_nombre = nombre;
        this.cantidad_total = cantidad_total;
    }

    @Override
    public String toString() {
        return String.format("\n-----\nNombre Residuo: %s "
                + "\nCantidad total: %.2f "
                + "\nEmpresa: %s "
                + "\nEnvase: %s "
                + "\nComposiciones químicas: %d\n",
                this.res_nombre,
                this.cantidad_total,
                empresa != null ? empresa.getEmp_nombre() : "N/A",
                envase != null ? envase.getEnv_descripcion() : "N/A",
                res_composiciones.size());
    }

    public void printComposiciones() {
        System.out.println("Composiciones químicas: " + res_composiciones.size());
        for (Composicion_Quimico cq : res_composiciones) {
            System.out.println(cq);
        }
    }

    // Métodos auxiliares
    public void formRes_composicion(Composicion_Quimico cq) {
        res_composiciones.add(cq);
    }

    public void dropRes_composicion(Composicion_Quimico cq) {
        res_composiciones.remove(cq);
    }

    // Getters y Setters
    public String getRes_nombre() { return res_nombre; }
    public void setRes_nombre(String res_nombre) { this.res_nombre = res_nombre; }

    public double getCantidad_total() { return cantidad_total; }
    public void setCantidad_total(double cantidad_total) { this.cantidad_total = cantidad_total; }

    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }

    public Envase getEnvase() { return envase; }
    public void setEnvase(Envase envase) { this.envase = envase; }

    public List<Composicion_Quimico> getRes_composiciones() { return res_composiciones; }
    public void setRes_composiciones(List<Composicion_Quimico> res_composiciones) { this.res_composiciones = res_composiciones; }
}


