/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

/**
 * Entidad persistente que representa un medio de transporte.
 * Las anotaciones JPA indican cómo ObjectDB guarda sus datos y relaciones.
 */
@Entity
public class Transporte implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String trans_tipo;   // usamos el tipo de transporte como PK

    // Relación: cada transporte pertenece a un transportista
    @ManyToOne
    @JoinColumn(name="trans_transportista", nullable=false)
    private Transportista transportista;

    // Relación: un transporte puede estar en muchos traslados
    @OneToMany
    @JoinColumn(name="tras_trans", nullable=false)
    private List<Traslado> trans_traslados = new ArrayList<>();

    // Constructor vacío requerido por JPA/ObjectDB.
    public Transporte() {}

    // Constructor de apoyo para crear objetos desde interfaces o pruebas.
    public Transporte(String tipo) {
        this.trans_tipo = tipo;
    }

    // Texto legible utilizado al imprimir o mostrar objetos.
    @Override
    public String toString() {
        return String.format("\n-----\nTipo de Transporte: %s "
                + "\nTransportista: %s "
                + "\nTraslados realizados: %d\n",
                this.trans_tipo,
                transportista != null ? transportista.getTrans_nombre() : "N/A",
                trans_traslados.size());
    }

    public void printTraslados() {
        System.out.println("Traslados con este transporte: " + trans_traslados.size());
        for (Traslado t : trans_traslados) {
            System.out.println(t);
        }
    }

    // Métodos auxiliares
    public void formTrans_traslado(Traslado t) {
        trans_traslados.add(t);
    }

    public void dropTrans_traslado(Traslado t) {
        trans_traslados.remove(t);
    }

    // Getters y Setters
    public String getTrans_tipo() { return trans_tipo; }
    public void setTrans_tipo(String trans_tipo) { this.trans_tipo = trans_tipo; }

    public Transportista getTransportista() { return transportista; }
    public void setTransportista(Transportista transportista) { this.transportista = transportista; }

    public List<Traslado> getTrans_traslados() { return trans_traslados; }
    public void setTrans_traslados(List<Traslado> trans_traslados) { this.trans_traslados = trans_traslados; }
}

