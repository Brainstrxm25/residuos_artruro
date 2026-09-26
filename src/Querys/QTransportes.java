/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Querys;

import clases.Transporte;
import java.util.*;
import javax.persistence.*;

public class QTransportes {

    public static void main(String[] args) {
        // Abrir conexión a la base de datos (crear si no existe)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();

        // Recuperar todos los transportes
        TypedQuery<Transporte> query = em.createQuery("SELECT tr FROM Transporte tr", Transporte.class);
        List<Transporte> results = query.getResultList();

        // Imprimir cada transporte y sus traslados
        for (Transporte tr : results) {
            System.out.println(tr);
            tr.printTraslados(); // método auxiliar de Transporte
        }

        // Cerrar conexión
        em.close();
        emf.close();
    }
}

