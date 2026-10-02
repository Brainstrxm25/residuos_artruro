/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Querys;

import clases.Envase;
import java.util.*;
import javax.persistence.*;

/** Consulta de prueba para listar envases almacenados en ObjectDB. */
public class QEnvases {

    // Punto de entrada de esta consulta de prueba.
    public static void main(String[] args) {
        // Abrir conexión a la base de datos (crear si no existe)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();

        // Recuperar todos los envases
        TypedQuery<Envase> query = em.createQuery("SELECT v FROM Envase v", Envase.class);
        List<Envase> results = query.getResultList();

        // Imprimir cada envase y sus residuos
        for (Envase v : results) {
            System.out.println(v);
            v.printResiduos(); // método auxiliar de Envase
        }

        // Cerrar conexión
        em.close();
        emf.close();
    }
}

