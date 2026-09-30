package Test;

import clases.*;
import javax.persistence.*;

public class Test {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // 1. Entidades raíz
        Empresa e1 = new Empresa("EcoPachuca", "Pachuca");
        Empresa e2 = new Empresa("ReciclaHidalgo", "Tepeji");
        em.persist(e1); 
        em.persist(e2);

        Envase env1 = new Envase("Bidón 50L", "Plástico");
        Envase env2 = new Envase("Tambor metálico", "Metal");
        em.persist(env1); 
        em.persist(env2);

        Quimico q1 = new Quimico("Mercurio", "Alta");
        Quimico q2 = new Quimico("Plomo", "Media");
        em.persist(q1); 
        em.persist(q2);

        Centro_Tratamiento ct1 = new Centro_Tratamiento("Planta Pachuca", "Zona Industrial");
        Centro_Tratamiento ct2 = new Centro_Tratamiento("Planta Tepeji", "Zona Sur");
        em.persist(ct1); 
        em.persist(ct2);

        Tipo_Tratamiento tt1 = new Tipo_Tratamiento("Incineración");
        Tipo_Tratamiento tt2 = new Tipo_Tratamiento("Reciclaje");
        em.persist(tt1); 
        em.persist(tt2);

        Transportista tp1 = new Transportista("TransHidalgo", "Av. Reforma", "7719876543");
        Transportista tp2 = new Transportista("TransTepeji", "Centro", "7731234567");
        em.persist(tp1); 
        em.persist(tp2);

        // 2. Residuos y Transportes (los métodos form vinculan en ambos sentidos)
        Residuo r1 = new Residuo("Plástico", 200);
        e1.formEmp_residuo(r1);
        env1.formEnv_residuo(r1);
        em.persist(r1);

        Residuo r2 = new Residuo("Aceite usado", 50);
        e2.formEmp_residuo(r2);
        env2.formEnv_residuo(r2);
        em.persist(r2);

        Transporte tr1 = new Transporte("Camión cisterna");
        tp1.formTrans_transportes(tr1);
        em.persist(tr1);

        Transporte tr2 = new Transporte("Camión caja");
        tp2.formTrans_transportes(tr2);
        em.persist(tr2);

        // 3. Composiciones químicas
        Composicion_Quimico cq1 = new Composicion_Quimico("Comp1", 10);
        r2.formRes_composicion(cq1);
        q1.formQuim_composicion(cq1);
        em.persist(cq1);

        Composicion_Quimico cq2 = new Composicion_Quimico("Comp2", 5);
        r2.formRes_composicion(cq2);
        q2.formQuim_composicion(cq2);
        em.persist(cq2);

        // 4. Traslados (asociados a todas sus relaciones correspondientes)
        Traslado t1 = new Traslado("EcoPachuca", 50, "2026-09-25", "2026-09-26", 5000, 120);
        e1.formEmp_traslado(t1);
        r1.formRes_traslado(t1);
        ct1.formCen_traslado(t1);
        tt1.formTrat_traslado(t1);
        tr1.formTrans_traslado(t1);
        em.persist(t1);

        Traslado t2 = new Traslado("ReciclaHidalgo", 100, "2026-09-27", "2026-09-28", 8000, 200);
        e2.formEmp_traslado(t2);
        r2.formRes_traslado(t2);
        ct2.formCen_traslado(t2);
        tt2.formTrat_traslado(t2);
        tr2.formTrans_traslado(t2);
        em.persist(t2);

        em.getTransaction().commit();
        em.close();
        emf.close();

        System.out.println("✅ Se insertaron 2 registros completos en cada tabla con todas las relaciones.");
    }
}

