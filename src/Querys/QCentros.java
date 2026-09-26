/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Querys;

import clases.Centro_Tratamiento;
import java.util.*;
import javax.persistence.*;

public class QCentros {

    public static void main(String[] args) {
        // Abrir conexión a la base de datos (crear si no existe)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();

        // Recuperar todos los centros de tratamiento
        TypedQuery<Centro_Tratamiento> query = em.createQuery("SELECT c FROM Centro_Tratamiento c", Centro_Tratamiento.class);
        List<Centro_Tratamiento> results = query.getResultList();

        // Imprimir cada centro y sus traslados
        for (Centro_Tratamiento c : results) {
            System.out.println(c);
            c.printTraslados(); // método auxiliar de Centro_Tratamiento
        }

        // Cerrar conexión
        em.close();
        emf.close();
    }
}

