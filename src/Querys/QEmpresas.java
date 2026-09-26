/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Querys;

import clases.Empresa;
import java.util.*;
import javax.persistence.*;

public class QEmpresas {

    public static void main(String[] args) {
        // Abrir conexión a la base de datos (crear si no existe)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();

        // Recuperar todas las empresas
        TypedQuery<Empresa> query = em.createQuery("SELECT e FROM Empresa e", Empresa.class);
        List<Empresa> results = query.getResultList();

        // Imprimir cada empresa y sus residuos
        for (Empresa e : results) {
            System.out.println(e);
            e.printResiduos(); // método auxiliar de Empresa
        }

        // Cerrar conexión
        em.close();
        emf.close();
    }
}

