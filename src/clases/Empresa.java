/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

/**
 * Entidad persistente que representa una empresa del sistema.
 * Las anotaciones JPA indican cómo ObjectDB guarda sus datos y relaciones.
 */
@Entity
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String emp_nombre;   // igual que Cine usaba el nombre como PK
    private String emp_ubicacion;

    @OneToMany
    @JoinColumn(name="res_emp", nullable=false)
    private List<Residuo> emp_residuos = new ArrayList<Residuo>();

    // Constructor vacío requerido por JPA/ObjectDB.
    public Empresa() {}

    // Constructor de apoyo para crear objetos desde interfaces o pruebas.
    public Empresa(String nombre, String ubicacion) {
        this.emp_nombre = nombre;
        this.emp_ubicacion = ubicacion;
    }

    // Texto legible utilizado al imprimir o mostrar objetos.
    @Override
    public String toString() {
        return String.format("\n-----\nNombre Empresa: %s "
                + "\nUbicación: %s "
                + "\nCantidad de residuos: %d\n",
                this.emp_nombre, this.emp_ubicacion,
                this.emp_residuos.size());
    }

    public void printResiduos() {
        System.out.println("Residuos registrados: " + emp_residuos.size());
        for (Residuo r : emp_residuos) {
            System.out.println(r);
        }
    }

    // Métodos auxiliares para manejar la relación
    public void formEmp_residuo(Residuo r) {
        emp_residuos.add(r);
    }

    public void dropEmp_residuo(Residuo r) {
        emp_residuos.remove(r);
    }

    // Getters y Setters
    public String getEmp_nombre() {
        return emp_nombre;
    }

    public void setEmp_nombre(String emp_nombre) {
        this.emp_nombre = emp_nombre;
    }

    public String getEmp_ubicacion() {
        return emp_ubicacion;
    }

    public void setEmp_ubicacion(String emp_ubicacion) {
        this.emp_ubicacion = emp_ubicacion;
    }

    public List<Residuo> getEmp_residuos() {
        return emp_residuos;
    }

    public void setEmp_residuos(List<Residuo> emp_residuos) {
        this.emp_residuos = emp_residuos;
    }
}


