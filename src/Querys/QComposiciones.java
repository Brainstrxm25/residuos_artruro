/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Querys;

import clases.Composicion_Quimico;
import java.util.*;
import javax.persistence.*;

/** Consulta de prueba para listar composiciones químicas y sus relaciones. */
public class QComposiciones {

    // Punto de entrada de esta consulta de prueba.
    public static void main(String[] args) {
        // Abrir conexión a la base de datos (crear si no existe)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();

        // Recuperar todas las composiciones químicas
        TypedQuery<Composicion_Quimico> query = em.createQuery("SELECT c FROM Composicion_Quimico c", Composicion_Quimico.class);
        List<Composicion_Quimico> results = query.getResultList();

        // Imprimir cada composición con su residuo y químico
        for (Composicion_Quimico c : results) {
            System.out.println(c);
            c.printRelacion(); // método auxiliar que muestre residuo y químico
        }

        // Cerrar conexión
        em.close();
        emf.close();
    }
}

