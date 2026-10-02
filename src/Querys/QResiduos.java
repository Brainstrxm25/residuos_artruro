/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Querys;

import clases.Residuo;
import java.util.*;
import javax.persistence.*;

/** Consulta de prueba para listar residuos almacenados en ObjectDB. */
public class QResiduos {

    // Punto de entrada de esta consulta de prueba.
    public static void main(String[] args) {
        // Abrir conexión a la base de datos (crear si no existe)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();

        // Recuperar todos los residuos
        TypedQuery<Residuo> query = em.createQuery("SELECT r FROM Residuo r", Residuo.class);
        List<Residuo> results = query.getResultList();

        // Imprimir cada residuo y sus composiciones químicas
        for (Residuo r : results) {
            System.out.println(r);
            r.printComposiciones(); // método auxiliar de Residuo
        }

        // Cerrar conexión
        em.close();
        emf.close();
    }
}
