/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Querys;

import clases.Transportista;
import java.util.*;
import javax.persistence.*;

public class QTransportistas {

    public static void main(String[] args) {
        // Abrir conexión a la base de datos (crear si no existe)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();

        // Recuperar todos los transportistas
        TypedQuery<Transportista> query = em.createQuery("SELECT t FROM Transportista t", Transportista.class);
        List<Transportista> results = query.getResultList();

        // Imprimir cada transportista y su transporte
        for (Transportista t : results) {
            System.out.println(t);
            t.printTransportes(); // método auxiliar de Transportista
        }

        // Cerrar conexión
        em.close();
        emf.close();
    }
}

