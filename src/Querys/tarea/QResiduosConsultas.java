/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Querys.tarea;

import clases.*;
import javax.persistence.*;
import java.util.*;

/** Ejemplos de consultas JPQL/ObjectDB sobre la entidad Residuo. */
public class QResiduosConsultas {

    // Punto de entrada de esta consulta de prueba.
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();

        // 1. Residuos y lotes del productor X
        TypedQuery<Object[]> q1 = em.createQuery(
                "SELECT r.res_nombre, r.cantidad_total FROM Residuo r WHERE r.empresa.emp_nombre = :prod",
                Object[].class);
        q1.setParameter("prod", "EcoPachuca");
        System.out.println("\n1. Residuos y lotes del productor EcoPachuca:");
        for (Object[] row : q1.getResultList()) {
            System.out.println("Residuo: " + row[0] + " | Cantidad: " + row[1]);
        }

        // 2. Residuos que contienen el químico X
        TypedQuery<Object[]> q2 = em.createQuery(
                "SELECT q.quim_nombre, r.res_nombre, cq.cantidad "
                + "FROM Composicion_Quimico cq JOIN cq.residuo r JOIN cq.quimico q "
                + "WHERE q.quim_nombre = :quim", Object[].class);
        q2.setParameter("quim", "Mercurio");
        System.out.println("\n2. Residuos que contienen Mercurio:");
        for (Object[] row : q2.getResultList()) {
            System.out.println("Químico: " + row[0] + " | Residuo: " + row[1] + " | Cantidad: " + row[2]);
        }

        // 3. Residuo con mayor cantidad del químico X
        TypedQuery<String> q3 = em.createQuery(
                "SELECT r.res_nombre FROM Composicion_Quimico cq JOIN cq.residuo r JOIN cq.quimico q "
                + "WHERE q.quim_nombre = :quim ORDER BY cq.cantidad DESC", String.class);
        q3.setParameter("quim", "Mercurio");
        q3.setMaxResults(1);
        System.out.println("\n3. Residuo con mayor cantidad de Mercurio:");
        for (String nombre : q3.getResultList()) {
            System.out.println("Residuo: " + nombre);
        }

        // 4. Número de traslados del residuo X
        TypedQuery<Long> q4 = em.createQuery(
                "SELECT COUNT(t) FROM Traslado t WHERE t.tras_origen = :res", Long.class);
        q4.setParameter("res", "EcoPachuca");
        System.out.println("\n4. Número de traslados del residuo EcoPachuca: " + q4.getSingleResult());

        // 5. Residuos trasladados a la tratadora X en el último mes
        TypedQuery<String> q5 = em.createQuery(
                "SELECT r.res_nombre "
                + "FROM Residuo r, Traslado t "
                + "WHERE r.empresa.emp_nombre = t.tras_origen "
                + "AND t.tratamiento.trat_descripcion = :trat", String.class);
        q5.setParameter("trat", "Incineración");

        System.out.println("\n5. Residuos trasladados a la tratadora Incineración:");
        for (String nombre : q5.getResultList()) {
            System.out.println("Residuo: " + nombre);
        }

        // 6. Empresas que han trasladado el residuo X
        TypedQuery<String> q6 = em.createQuery(
                "SELECT DISTINCT t.empresa.emp_nombre "
                + "FROM Traslado t "
                + "WHERE t.residuo.res_nombre = :res", String.class);
        q6.setParameter("res", "Aceite usado");

        System.out.println("\n6. Empresas que han trasladado Aceite usado:");
        for (String nombre : q6.getResultList()) {
            System.out.println("Empresa: " + nombre);
        }

        // 7. Vehículo con mayor número de traslados
        TypedQuery<Object[]> q7 = em.createQuery(
                "SELECT tr.trans_tipo, COUNT(t) FROM Traslado t JOIN t.transporte tr GROUP BY tr.trans_tipo ORDER BY COUNT(t) DESC",
                Object[].class);
        q7.setMaxResults(1);
        System.out.println("\n7. Vehículo con mayor número de traslados:");
        for (Object[] row : q7.getResultList()) {
            System.out.println("Vehículo: " + row[0] + " | Traslados: " + row[1]);
        }

        // 8. Composición del residuo X
        TypedQuery<Object[]> q8 = em.createQuery(
                "SELECT q.quim_nombre, cq.cantidad FROM Composicion_Quimico cq JOIN cq.quimico q JOIN cq.residuo r "
                + "WHERE r.res_nombre = :res", Object[].class);
        q8.setParameter("res", "Aceite usado");
        System.out.println("\n8. Composición del residuo Aceite usado:");
        for (Object[] row : q8.getResultList()) {
            System.out.println("Químico: " + row[0] + " | Cantidad: " + row[1]);
        }

        // 9. Empresa con mayor cantidad de residuos producidos
        TypedQuery<Object[]> q9 = em.createQuery(
                "SELECT e.emp_nombre, COUNT(r) FROM Empresa e JOIN e.emp_residuos r GROUP BY e.emp_nombre ORDER BY COUNT(r) DESC",
                Object[].class);
        q9.setMaxResults(1);
        System.out.println("\n9. Empresa con mayor cantidad de residuos:");
        for (Object[] row : q9.getResultList()) {
            System.out.println("Empresa: " + row[0] + " | Residuos: " + row[1]);
        }

        // 10. Cantidad de residuo X en empresa productora
        TypedQuery<Object[]> q10 = em.createQuery(
                "SELECT r.res_nombre, r.cantidad_total FROM Residuo r WHERE r.res_nombre = :res AND r.empresa.emp_nombre = :emp",
                Object[].class);
        q10.setParameter("res", "Aceite usado");
        q10.setParameter("emp", "ReciclaHidalgo");
        System.out.println("\n10. Cantidad de Aceite usado en ReciclaHidalgo:");
        for (Object[] row : q10.getResultList()) {
            System.out.println("Residuo: " + row[0] + " | Cantidad: " + row[1]);
        }

        em.close();
        emf.close();
    }
}
