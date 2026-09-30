/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Composicion_Quimico implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    private Long id;

    private String comp_nombre;
    private double cantidad;

    @ManyToOne
    @JoinColumn(name = "comp_res", nullable = false)
    private Residuo residuo;

    @ManyToOne
    @JoinColumn(name = "comp_quim", nullable = false)
    private Quimico quimico;

    public Composicion_Quimico() {
    }

    public Composicion_Quimico(String nombre, double cantidad) {
        this.comp_nombre = nombre;
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return String.format("\n-----\nID: %d | Nombre Composición: %s "
                + "\nCantidad: %.2f "
                + "\nResiduo: %s "
                + "\nQuímico: %s\n",
                this.id,
                this.comp_nombre,
                this.cantidad,
                residuo != null ? residuo.getRes_nombre() : "N/A",
                quimico != null ? quimico.getQuim_nombre() : "N/A");
    }

    public void printRelacion() {
        System.out.println("ID: " + id);
        System.out.println("Composición: " + comp_nombre);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Residuo: " + (residuo != null ? residuo.getRes_nombre() : "N/A"));
        System.out.println("Químico: " + (quimico != null ? quimico.getQuim_nombre() : "N/A"));
        System.out.println("-----");
    }

    // Métodos form y drop
    public void formComp_residuo(Residuo r) {
        this.residuo = r;
        if (r != null && !r.getRes_composiciones().contains(this)) {
            r.getRes_composiciones().add(this);
        }
    }

    public void dropComp_residuo(Residuo r) {
        if (this.residuo == r) {
            this.residuo = null;
            if (r != null) {
                r.getRes_composiciones().remove(this);
            }
        }
    }

    public void formComp_quimico(Quimico q) {
        this.quimico = q;
        if (q != null && !q.getQuim_composiciones().contains(this)) {
            q.getQuim_composiciones().add(this);
        }
    }

    public void dropComp_quimico(Quimico q) {
        if (this.quimico == q) {
            this.quimico = null;
            if (q != null) {
                q.getQuim_composiciones().remove(this);
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

    public String getComp_nombre() {
        return comp_nombre;
    }

    public void setComp_nombre(String comp_nombre) {
        this.comp_nombre = comp_nombre;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Residuo getResiduo() {
        return residuo;
    }

    public void setResiduo(Residuo residuo) {
        this.residuo = residuo;
    }

    public Quimico getQuimico() {
        return quimico;
    }

    public void setQuimico(Quimico quimico) {
        this.quimico = quimico;
    }
}
