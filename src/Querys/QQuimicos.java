/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Querys;

import clases.Quimico;
import java.util.*;
import javax.persistence.*;

/** Consulta de prueba para listar químicos almacenados en ObjectDB. */
public class QQuimicos {

    // Punto de entrada de esta consulta de prueba.
    public static void main(String[] args) {
        // Abrir conexión a la base de datos (crear si no existe)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();

        // Recuperar todos los químicos
        TypedQuery<Quimico> query = em.createQuery("SELECT q FROM Quimico q", Quimico.class);
        List<Quimico> results = query.getResultList();

        // Imprimir cada químico y sus composiciones
        for (Quimico q : results) {
            System.out.println(q);
            q.printComposiciones(); // método auxiliar de Quimico
        }

        // Cerrar conexión
        em.close();
        emf.close();
    }
}

