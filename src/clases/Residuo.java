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
    @GeneratedValue
    private Long id;

    private String res_nombre;
    private double cantidad_total;

    @ManyToOne
    @JoinColumn(name = "res_emp", nullable = false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "res_env", nullable = false)
    private Envase envase;

    @OneToMany
    @JoinColumn(name = "comp_res", nullable = false)
    private List<Composicion_Quimico> res_composiciones = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "tras_res", nullable = false)
    private List<Traslado> res_traslados = new ArrayList<>();

    public Residuo() {
    }

    public Residuo(String nombre, double cantidad_total) {
        this.res_nombre = nombre;
        this.cantidad_total = cantidad_total;
    }

    @Override
    public String toString() {
        return String.format("\n-----\nID: %d | Nombre Residuo: %s "
                + "\nCantidad total: %.2f "
                + "\nEmpresa: %s "
                + "\nEnvase: %s "
                + "\nComposiciones químicas: %d"
                + "\nTraslados realizados: %d\n",
                this.id,
                this.res_nombre,
                this.cantidad_total,
                empresa != null ? empresa.getEmp_nombre() : "N/A",
                envase != null ? envase.getEnv_descripcion() : "N/A",
                res_composiciones.size(),
                res_traslados.size());
    }

    public void printComposiciones() {
        System.out.println("Composiciones químicas: " + res_composiciones.size());
        for (Composicion_Quimico cq : res_composiciones) {
            System.out.println(cq);
        }
    }

    // Métodos form y drop para Empresa
    public void formRes_empresa(Empresa e) {
        this.empresa = e;
        if (e != null && !e.getEmp_residuos().contains(this)) {
            e.getEmp_residuos().add(this);
        }
    }

    public void dropRes_empresa(Empresa e) {
        if (this.empresa == e) {
            this.empresa = null;
            if (e != null) {
                e.getEmp_residuos().remove(this);
            }
        }
    }

    // Métodos form y drop para Envase
    public void formRes_envase(Envase env) {
        this.envase = env;
        if (env != null && !env.getEnv_residuos().contains(this)) {
            env.getEnv_residuos().add(this);
        }
    }

    public void dropRes_envase(Envase env) {
        if (this.envase == env) {
            this.envase = null;
            if (env != null) {
                env.getEnv_residuos().remove(this);
            }
        }
    }

    // Métodos form y drop para Composiciones
    public void formRes_composicion(Composicion_Quimico cq) {
        if (cq != null && !res_composiciones.contains(cq)) {
            res_composiciones.add(cq);
            cq.setResiduo(this);
        }
    }

    public void dropRes_composicion(Composicion_Quimico cq) {
        if (res_composiciones.remove(cq)) {
            if (cq.getResiduo() == this) {
                cq.setResiduo(null);
            }
        }
    }

    // Métodos form y drop para Traslados
    public void formRes_traslado(Traslado t) {
        if (t != null && !res_traslados.contains(t)) {
            res_traslados.add(t);
            t.setResiduo(this);
        }
    }

    public void dropRes_traslado(Traslado t) {
        if (res_traslados.remove(t)) {
            if (t.getResiduo() == this) {
                t.setResiduo(null);
            }
        }
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRes_nombre() {
        return res_nombre;
    }

    public void setRes_nombre(String res_nombre) {
        this.res_nombre = res_nombre;
    }

    public double getCantidad_total() {
        return cantidad_total;
    }

    public void setCantidad_total(double cantidad_total) {
        this.cantidad_total = cantidad_total;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Envase getEnvase() {
        return envase;
    }

    public void setEnvase(Envase envase) {
        this.envase = envase;
    }

    public List<Composicion_Quimico> getRes_composiciones() {
        return res_composiciones;
    }

    public void setRes_composiciones(List<Composicion_Quimico> res_composiciones) {
        this.res_composiciones = res_composiciones;
    }

    public List<Traslado> getRes_traslados() {
        return res_traslados;
    }

    public void setRes_traslados(List<Traslado> res_traslados) {
        this.res_traslados = res_traslados;
    }
}
