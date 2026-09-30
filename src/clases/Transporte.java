/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

@Entity
public class Transporte implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String trans_tipo;

    @ManyToOne
    @JoinColumn(name="trans_transportista", nullable=false)
    private Transportista transportista;

    @OneToMany
    @JoinColumn(name="tras_trans", nullable=false)
    private List<Traslado> trans_traslados = new ArrayList<>();

    public Transporte() {}

    public Transporte(String tipo) {
        this.trans_tipo = tipo;
    }

    @Override
    public String toString() {
        return String.format("\n-----\nID: %d | Tipo de Transporte: %s "
                + "\nTransportista: %s "
                + "\nTraslados realizados: %d\n",
                this.id,
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

    // Métodos form y drop para Transportista
    public void formTrans_transportista(Transportista t) {
        this.transportista = t;
        if (t != null && !t.getTrans_transportes().contains(this)) {
            t.getTrans_transportes().add(this);
        }
    }

    public void dropTrans_transportista(Transportista t) {
        if (this.transportista == t) {
            this.transportista = null;
            if (t != null) {
                t.getTrans_transportes().remove(this);
            }
        }
    }

    // Métodos form y drop para Traslados
    public void formTrans_traslado(Traslado t) {
        if (t != null && !trans_traslados.contains(t)) {
            trans_traslados.add(t);
            t.setTransporte(this);
        }
    }

    public void dropTrans_traslado(Traslado t) {
        if (trans_traslados.remove(t)) {
            if (t.getTransporte() == this) {
                t.setTransporte(null);
            }
        }
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTrans_tipo() { return trans_tipo; }
    public void setTrans_tipo(String trans_tipo) { this.trans_tipo = trans_tipo; }

    public Transportista getTransportista() { return transportista; }
    public void setTransportista(Transportista transportista) { this.transportista = transportista; }

    public List<Traslado> getTrans_traslados() { return trans_traslados; }
    public void setTrans_traslados(List<Traslado> trans_traslados) { this.trans_traslados = trans_traslados; }
}