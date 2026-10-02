/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Querys;

import clases.Traslado;
import java.util.*;
import javax.persistence.*;

/** Consulta de prueba para listar traslados y relaciones. */
public class QTraslados {

    // Punto de entrada de esta consulta de prueba.
    public static void main(String[] args) {
        // Abrir conexión a la base de datos (crear si no existe)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();

        // Recuperar todos los traslados
        TypedQuery<Traslado> query = em.createQuery("SELECT tr FROM Traslado tr", Traslado.class);
        List<Traslado> results = query.getResultList();

        // Imprimir cada traslado con sus relaciones
        for (Traslado tr : results) {
            System.out.println(tr);
            tr.printRelacion(); // método auxiliar de Traslado
        }

        // Cerrar conexión
        em.close();
        emf.close();
    }
}
