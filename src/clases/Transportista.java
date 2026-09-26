/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

@Entity
public class Transportista implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String trans_nombre;   // usamos el nombre como PK
    private String direccion;
    private String telefono;

    // Relación: un transportista puede tener varios transportes
    @OneToMany
    @JoinColumn(name="trans_transportista", nullable=false)
    private List<Transporte> trans_transportes = new ArrayList<>();

    public Transportista() {}

    public Transportista(String nombre, String direccion, String telefono) {
        this.trans_nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return String.format("\n-----\nTransportista: %s "
                + "\nDirección: %s "
                + "\nTeléfono: %s "
                + "\nTransportes asociados: %d\n",
                this.trans_nombre,
                this.direccion,
                this.telefono,
                trans_transportes.size());
    }

    public void printTransportes() {
        System.out.println("Transportes de este transportista: " + trans_transportes.size());
        for (Transporte t : trans_transportes) {
            System.out.println(t);
        }
    }
    

    // Métodos auxiliares
    public void formTrans_transportes(Transporte t) {
        trans_transportes.add(t);
    }

    public void dropTrans_transportes(Transporte t) {
        trans_transportes.remove(t);
    }

    // Getters y Setters
    public String getTrans_nombre() { return trans_nombre; }
    public void setTrans_nombre(String trans_nombre) { this.trans_nombre = trans_nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public List<Transporte> getTrans_transportes() { return trans_transportes; }
    public void setTrans_transportes(List<Transporte> trans_transportes) { this.trans_transportes = trans_transportes; }
}

