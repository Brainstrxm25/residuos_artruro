/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

@Entity
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    private Long id;

    private String emp_nombre;
    private String emp_ubicacion;

    @OneToMany
    @JoinColumn(name="res_emp", nullable=false)
    private List<Residuo> emp_residuos = new ArrayList<>();

    @OneToMany
    @JoinColumn(name="tras_emp", nullable=false)
    private List<Traslado> emp_traslados = new ArrayList<>();

    public Empresa() {}

    public Empresa(String nombre, String ubicacion) {
        this.emp_nombre = nombre;
        this.emp_ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return String.format("\n-----\nID: %d | Nombre Empresa: %s "
                + "\nUbicación: %s "
                + "\nCantidad de residuos: %d"
                + "\nCantidad de traslados: %d\n",
                this.id, this.emp_nombre, this.emp_ubicacion,
                this.emp_residuos.size(), this.emp_traslados.size());
    }

    public void printResiduos() {
        System.out.println("Residuos registrados: " + emp_residuos.size());
        for (Residuo r : emp_residuos) {
            System.out.println(r);
        }
    }

    // Métodos form y drop para Residuos
    public void formEmp_residuo(Residuo r) {
        if (r != null && !emp_residuos.contains(r)) {
            emp_residuos.add(r);
            r.setEmpresa(this);
        }
    }

    public void dropEmp_residuo(Residuo r) {
        if (emp_residuos.remove(r)) {
            if (r.getEmpresa() == this) {
                r.setEmpresa(null);
            }
        }
    }

    // Métodos form y drop para Traslados
    public void formEmp_traslado(Traslado t) {
        if (t != null && !emp_traslados.contains(t)) {
            emp_traslados.add(t);
            t.setEmpresa(this);
        }
    }

    public void dropEmp_traslado(Traslado t) {
        if (emp_traslados.remove(t)) {
            if (t.getEmpresa() == this) {
                t.setEmpresa(null);
            }
        }
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmp_nombre() { return emp_nombre; }
    public void setEmp_nombre(String emp_nombre) { this.emp_nombre = emp_nombre; }

    public String getEmp_ubicacion() { return emp_ubicacion; }
    public void setEmp_ubicacion(String emp_ubicacion) { this.emp_ubicacion = emp_ubicacion; }

    public List<Residuo> getEmp_residuos() { return emp_residuos; }
    public void setEmp_residuos(List<Residuo> emp_residuos) { this.emp_residuos = emp_residuos; }

    public List<Traslado> getEmp_traslados() { return emp_traslados; }
    public void setEmp_traslados(List<Traslado> emp_traslados) { this.emp_traslados = emp_traslados; }
}


