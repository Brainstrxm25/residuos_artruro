/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

@Entity
public class Traslado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;

    private String tras_origen;
    private double cantidad_trasladada;
    private String fecha_inicio;
    private String fecha_llegada;
    private double costo;
    private double km_recorridos;

    @ManyToOne
    @JoinColumn(name = "tras_emp", nullable = false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "tras_res", nullable = false)
    private Residuo residuo;

    @ManyToOne
    @JoinColumn(name = "tras_cen", nullable = false)
    private Centro_Tratamiento centro;

    @ManyToOne
    @JoinColumn(name = "tras_trat", nullable = false)
    private Tipo_Tratamiento tratamiento;

    @ManyToOne
    @JoinColumn(name = "tras_trans", nullable = false)
    private Transporte transporte;

    public Traslado() {}

    public Traslado(String origen, double cantidad, String inicio, String llegada,
                    double costo, double km) {
        this.tras_origen = origen;
        this.cantidad_trasladada = cantidad;
        this.fecha_inicio = inicio;
        this.fecha_llegada = llegada;
        this.costo = costo;
        this.km_recorridos = km;
    }

    @Override
    public String toString() {
        return String.format("\n-----\nID: %d | Traslado desde: %s "
                + "\nCantidad trasladada: %.2f "
                + "\nFecha inicio: %s "
                + "\nFecha llegada: %s "
                + "\nCosto: %.2f "
                + "\nKm recorridos: %.2f "
                + "\nEmpresa: %s "
                + "\nResiduo: %s "
                + "\nCentro: %s "
                + "\nTratamiento: %s "
                + "\nTransporte: %s\n",
                this.id,
                this.tras_origen,
                this.cantidad_trasladada,
                this.fecha_inicio,
                this.fecha_llegada,
                this.costo,
                this.km_recorridos,
                empresa != null ? empresa.getEmp_nombre() : "N/A",
                residuo != null ? residuo.getRes_nombre() : "N/A",
                centro != null ? centro.getCen_descripcion() : "N/A",
                tratamiento != null ? tratamiento.getTrat_descripcion() : "N/A",
                transporte != null ? transporte.getTrans_tipo() : "N/A");
    }

    public void printRelacion() {
        System.out.println("ID: " + id);
        System.out.println("Traslado desde: " + tras_origen);
        System.out.println("Cantidad trasladada: " + cantidad_trasladada);
        System.out.println("Fecha inicio: " + fecha_inicio);
        System.out.println("Fecha llegada: " + fecha_llegada);
        System.out.println("Costo: " + costo);
        System.out.println("Km recorridos: " + km_recorridos);
        System.out.println("Empresa: " + (empresa != null ? empresa.getEmp_nombre() : "N/A"));
        System.out.println("Residuo: " + (residuo != null ? residuo.getRes_nombre() : "N/A"));
        System.out.println("Centro: " + (centro != null ? centro.getCen_descripcion() : "N/A"));
        System.out.println("Tratamiento: " + (tratamiento != null ? tratamiento.getTrat_descripcion() : "N/A"));
        System.out.println("Transporte: " + (transporte != null ? transporte.getTrans_tipo() : "N/A"));
        System.out.println("-----");
    }

    // Métodos form y drop para relaciones ManyToOne
    public void formTras_empresa(Empresa e) {
        this.empresa = e;
        if (e != null && !e.getEmp_traslados().contains(this)) {
            e.getEmp_traslados().add(this);
        }
    }

    public void dropTras_empresa(Empresa e) {
        if (this.empresa == e) {
            this.empresa = null;
            if (e != null) {
                e.getEmp_traslados().remove(this);
            }
        }
    }

    public void formTras_residuo(Residuo r) {
        this.residuo = r;
        if (r != null && !r.getRes_traslados().contains(this)) {
            r.getRes_traslados().add(this);
        }
    }

    public void dropTras_residuo(Residuo r) {
        if (this.residuo == r) {
            this.residuo = null;
            if (r != null) {
                r.getRes_traslados().remove(this);
            }
        }
    }

    public void formTras_centro(Centro_Tratamiento c) {
        this.centro = c;
        if (c != null && !c.getCen_traslados().contains(this)) {
            c.getCen_traslados().add(this);
        }
    }

    public void dropTras_centro(Centro_Tratamiento c) {
        if (this.centro == c) {
            this.centro = null;
            if (c != null) {
                c.getCen_traslados().remove(this);
            }
        }
    }

    public void formTras_tratamiento(Tipo_Tratamiento t) {
        this.tratamiento = t;
        if (t != null && !t.getTrat_traslados().contains(this)) {
            t.getTrat_traslados().add(this);
        }
    }

    public void dropTras_tratamiento(Tipo_Tratamiento t) {
        if (this.tratamiento == t) {
            this.tratamiento = null;
            if (t != null) {
                t.getTrat_traslados().remove(this);
            }
        }
    }

    public void formTras_transporte(Transporte t) {
        this.transporte = t;
        if (t != null && !t.getTrans_traslados().contains(this)) {
            t.getTrans_traslados().add(this);
        }
    }

    public void dropTras_transporte(Transporte t) {
        if (this.transporte == t) {
            this.transporte = null;
            if (t != null) {
                t.getTrans_traslados().remove(this);
            }
        }
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTras_origen() { return tras_origen; }
    public void setTras_origen(String tras_origen) { this.tras_origen = tras_origen; }

    public double getCantidad_trasladada() { return cantidad_trasladada; }
    public void setCantidad_trasladada(double cantidad_trasladada) { this.cantidad_trasladada = cantidad_trasladada; }

    public String getFecha_inicio() { return fecha_inicio; }
    public void setFecha_inicio(String fecha_inicio) { this.fecha_inicio = fecha_inicio; }

    public String getFecha_llegada() { return fecha_llegada; }
    public void setFecha_llegada(String fecha_llegada) { this.fecha_llegada = fecha_llegada; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    public double getKm_recorridos() { return km_recorridos; }
    public void setKm_recorridos(double km_recorridos) { this.km_recorridos = km_recorridos; }

    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }

    public Residuo getResiduo() { return residuo; }
    public void setResiduo(Residuo residuo) { this.residuo = residuo; }

    public Centro_Tratamiento getCentro() { return centro; }
    public void setCentro(Centro_Tratamiento centro) { this.centro = centro; }

    public Tipo_Tratamiento getTratamiento() { return tratamiento; }
    public void setTratamiento(Tipo_Tratamiento tratamiento) { this.tratamiento = tratamiento; }

    public Transporte getTransporte() { return transporte; }
    public void setTransporte(Transporte transporte) { this.transporte = transporte; }
}