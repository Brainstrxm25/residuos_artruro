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
    private String comp_nombre;   
    private double cantidad;

    // Relación: cada composición pertenece a un residuo
    @ManyToOne
    @JoinColumn(name="comp_res", nullable=false)
    private Residuo residuo;

    // Relación: cada composición está asociada a un químico
    @ManyToOne
    @JoinColumn(name="comp_quim", nullable=false)
    private Quimico quimico;

    public Composicion_Quimico() {}

    public Composicion_Quimico(String nombre, double cantidad) {
        this.comp_nombre = nombre;
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return String.format("\n-----\nNombre Composición: %s "
                + "\nCantidad: %.2f "
                + "\nResiduo: %s "
                + "\nQuímico: %s\n",
                this.comp_nombre,
                this.cantidad,
                residuo != null ? residuo.getRes_nombre() : "N/A",
                quimico != null ? quimico.getQuim_nombre() : "N/A");
    }

    // 🔑 Método auxiliar para imprimir la relación, estilo cine.printSalas()
    public void printRelacion() {
        System.out.println("Composición: " + comp_nombre);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Residuo: " + (residuo != null ? residuo.getRes_nombre() : "N/A"));
        System.out.println("Químico: " + (quimico != null ? quimico.getQuim_nombre() : "N/A"));
        System.out.println("-----");
    }

    // Getters y Setters
    public String getComp_nombre() { return comp_nombre; }
    public void setComp_nombre(String comp_nombre) { this.comp_nombre = comp_nombre; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public Residuo getResiduo() { return residuo; }
    public void setResiduo(Residuo residuo) { this.residuo = residuo; }

    public Quimico getQuimico() { return quimico; }
    public void setQuimico(Quimico quimico) { this.quimico = quimico; }
}
