/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Querys;

import clases.Tipo_Tratamiento;
import java.util.*;
import javax.persistence.*;

public class QTratamientos {

    public static void main(String[] args) {
        // Abrir conexión a la base de datos (crear si no existe)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();

        // Recuperar todos los tipos de tratamiento
        TypedQuery<Tipo_Tratamiento> query = em.createQuery("SELECT t FROM Tipo_Tratamiento t", Tipo_Tratamiento.class);
        List<Tipo_Tratamiento> results = query.getResultList();

        // Imprimir cada tipo de tratamiento y sus traslados
        for (Tipo_Tratamiento t : results) {
            System.out.println(t);
            t.printTraslados(); // método auxiliar de Tipo_Tratamiento
        }

        // Cerrar conexión
        em.close();
        emf.close();
    }
}

