/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Residuos;

import clases.Centro_Tratamiento;
import clases.Composicion_Quimico;
import clases.Empresa;
import clases.Envase;
import clases.Quimico;
import clases.Residuo;
import clases.Tipo_Tratamiento;
import clases.Transporte;
import clases.Transportista;
import clases.Traslado;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author THUNDEROBOT
 */
public class opCRUD {

    void opCreateEmpresa(Empresa ee) {
        Empresa e;
        // Abrir conexión a la base de datos (crear si no existe aún)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        e = ee;
        em.persist(e);
        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Empresa registrada");
    }

    void opUpdateEmpresa(Empresa ee) {
        Empresa e;
        String aux;
        System.out.println("Empresa entra: " + ee);

        // Abrir conexión a la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        aux = ee.getEmp_nombre(); // clave primaria
        em.getTransaction().begin();
        e = em.find(Empresa.class, aux);
        System.out.println("Empresa encontrada con id " + aux);
        System.out.println(e);

        // Actualizar campos
        e.setEmp_ubicacion(ee.getEmp_ubicacion());

        System.out.println("Empresa modificada \n" + e);

        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Empresa actualizada");
    }

    void opCreateResiduo(Residuo rr) {
        Residuo r;
        // Abrir conexión a la base de datos (crear si no existe aún)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        r = rr;
        em.persist(r);
        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Residuo registrado");
    }

    void opUpdateResiduo(Residuo rr) {
        Residuo r;
        String aux;
        System.out.println("Residuo entra: " + rr);

        // Abrir conexión a la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        aux = rr.getRes_nombre(); // clave primaria
        em.getTransaction().begin();
        r = em.find(Residuo.class, aux);
        System.out.println("Residuo encontrado con id " + aux);
        System.out.println(r);

        // Actualizar campos editables
        r.setCantidad_total(rr.getCantidad_total());
        r.setEmpresa(rr.getEmpresa());
        r.setEnvase(rr.getEnvase());
        r.setRes_composiciones(rr.getRes_composiciones()); // opcional: actualizar lista completa

        System.out.println("Residuo modificado \n" + r);

        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Residuo actualizado");
    }

    void opCreateTraslado(Traslado tt) {
        Traslado t;
        // Abrir conexión a la base de datos (crear si no existe aún)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        t = tt;
        em.persist(t);
        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Traslado registrado");
    }

    void opUpdateTraslado(Traslado tt) {
        Traslado t;
        String aux;
        System.out.println("Traslado entra: " + tt);

        // Abrir conexión a la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        aux = tt.getTras_origen(); // clave primaria
        em.getTransaction().begin();
        t = em.find(Traslado.class, aux);
        System.out.println("Traslado encontrado con id " + aux);
        System.out.println(t);

        // Actualizar campos editables
        t.setCantidad_trasladada(tt.getCantidad_trasladada());
        t.setFecha_inicio(tt.getFecha_inicio());
        t.setFecha_llegada(tt.getFecha_llegada());
        t.setCosto(tt.getCosto());
        t.setKm_recorridos(tt.getKm_recorridos());
        t.setEmpresa(tt.getEmpresa());
        t.setResiduo(tt.getResiduo());
        t.setCentro(tt.getCentro());
        t.setTratamiento(tt.getTratamiento());
        t.setTransporte(tt.getTransporte());

        System.out.println("Traslado modificado \n" + t);

        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Traslado actualizado");
    }

    void opCreateCentro(Centro_Tratamiento cc) {
        Centro_Tratamiento c;
        // Abrir conexión a la base de datos (crear si no existe aún)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        c = cc;
        em.persist(c);
        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Centro de Tratamiento registrado");
    }

    void opUpdateCentro(Centro_Tratamiento cc) {
        Centro_Tratamiento c;
        String aux;
        System.out.println("Centro entra: " + cc);

        // Abrir conexión a la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        aux = cc.getCen_descripcion(); // clave primaria
        em.getTransaction().begin();
        c = em.find(Centro_Tratamiento.class, aux);
        System.out.println("Centro encontrado con id " + aux);
        System.out.println(c);

        // Actualizar campos editables
        c.setCen_ubicacion(cc.getCen_ubicacion());
        c.setCen_traslados(cc.getCen_traslados()); // opcional: actualizar lista completa de traslados

        System.out.println("Centro modificado \n" + c);

        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Centro de Tratamiento actualizado");
    }

    void opCreateComposicion(Composicion_Quimico cc) {
        Composicion_Quimico c;
        // Abrir conexión a la base de datos (crear si no existe aún)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        c = cc;
        em.persist(c);
        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Composición registrada");
    }

    void opUpdateComposicion(Composicion_Quimico cc) {
        Composicion_Quimico c;
        String aux;
        System.out.println("Composición entra: " + cc);

        // Abrir conexión a la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        aux = cc.getComp_nombre(); // clave primaria
        em.getTransaction().begin();
        c = em.find(Composicion_Quimico.class, aux);
        System.out.println("Composición encontrada con id " + aux);
        System.out.println(c);

        // Actualizar campos editables
        c.setCantidad(cc.getCantidad());
        c.setResiduo(cc.getResiduo());
        c.setQuimico(cc.getQuimico());

        System.out.println("Composición modificada \n" + c);

        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Composición actualizada");
    }

    void opCreateEnvase(Envase ee) {
        Envase e;
        // Abrir conexión a la base de datos (crear si no existe aún)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        e = ee;
        em.persist(e);
        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Envase registrado");
    }

    void opUpdateEnvase(Envase ee) {
        Envase e;
        String aux;
        System.out.println("Envase entra: " + ee);

        // Abrir conexión a la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        aux = ee.getEnv_descripcion(); // clave primaria
        em.getTransaction().begin();
        e = em.find(Envase.class, aux);
        System.out.println("Envase encontrado con id " + aux);
        System.out.println(e);

        // Actualizar campos editables
        e.setCategoria_material(ee.getCategoria_material());
        e.setEnv_residuos(ee.getEnv_residuos()); // opcional: actualizar lista completa de residuos

        System.out.println("Envase modificado \n" + e);

        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Envase actualizado");
    }

    void opCreateQuimico(Quimico qq) {
        Quimico q;
        // Abrir conexión a la base de datos (crear si no existe aún)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        q = qq;
        em.persist(q);
        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Químico registrado");
    }

    void opUpdateQuimico(Quimico qq) {
        Quimico q;
        String aux;
        System.out.println("Químico entra: " + qq);

        // Abrir conexión a la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        aux = qq.getQuim_nombre(); // clave primaria
        em.getTransaction().begin();
        q = em.find(Quimico.class, aux);
        System.out.println("Químico encontrado con id " + aux);
        System.out.println(q);

        // Actualizar campos editables
        q.setTipo_peligrosidad(qq.getTipo_peligrosidad());
        q.setQuim_composiciones(qq.getQuim_composiciones()); // opcional: actualizar lista completa de composiciones

        System.out.println("Químico modificado \n" + q);

        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Químico actualizado");
    }

    void opCreateTratamiento(Tipo_Tratamiento tt) {
        Tipo_Tratamiento t;
        // Abrir conexión a la base de datos (crear si no existe aún)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        t = tt;
        em.persist(t);
        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Tipo de Tratamiento registrado");
    }

    void opUpdateTratamiento(Tipo_Tratamiento tt) {
        Tipo_Tratamiento t;
        String aux;
        System.out.println("Tratamiento entra: " + tt);

        // Abrir conexión a la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        aux = tt.getTrat_descripcion(); // clave primaria
        em.getTransaction().begin();
        t = em.find(Tipo_Tratamiento.class, aux);
        System.out.println("Tratamiento encontrado con id " + aux);
        System.out.println(t);

        // Actualizar campos editables
        t.setTrat_traslados(tt.getTrat_traslados()); // opcional: actualizar lista completa de traslados

        System.out.println("Tratamiento modificado \n" + t);

        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Tipo de Tratamiento actualizado");
    }

    void opCreateTransporte(Transporte tt) {
        Transporte t;
        // Abrir conexión a la base de datos (crear si no existe aún)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        t = tt;
        em.persist(t);
        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Transporte registrado");
    }

    void opUpdateTransporte(Transporte tt) {
        Transporte t;
        String aux;
        System.out.println("Transporte entra: " + tt);

        // Abrir conexión a la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        aux = tt.getTrans_tipo(); // clave primaria
        em.getTransaction().begin();
        t = em.find(Transporte.class, aux);
        System.out.println("Transporte encontrado con id " + aux);
        System.out.println(t);

        // Actualizar campos editables
        t.setTransportista(tt.getTransportista());
        t.setTrans_traslados(tt.getTrans_traslados()); // opcional: actualizar lista completa de traslados

        System.out.println("Transporte modificado \n" + t);

        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Transporte actualizado");
    }

    void opCreateTransportista(Transportista tt) {
        Transportista t;
        // Abrir conexión a la base de datos (crear si no existe aún)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        t = tt;
        em.persist(t);
        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Transportista registrado");
    }

    void opUpdateTransportista(Transportista tt) {
        Transportista t;
        String aux;
        System.out.println("Transportista entra: " + tt);

        // Abrir conexión a la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        aux = tt.getTrans_nombre(); // clave primaria
        em.getTransaction().begin();
        t = em.find(Transportista.class, aux);
        System.out.println("Transportista encontrado con id " + aux);
        System.out.println(t);

        // Actualizar campos editables
        t.setDireccion(tt.getDireccion());
        t.setTelefono(tt.getTelefono());
        t.setTrans_transportes(tt.getTrans_transportes()); // opcional: actualizar lista completa de transportes

        System.out.println("Transportista modificado \n" + t);

        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Transportista actualizado");
    }

    public List opRead(String ent, String field, String crit) {

        // Abrir conexión a la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();

        if (ent.equals("Empresa")) {
            TypedQuery<Empresa> query;
            List<Empresa> results;
            if (crit.equals("")) {
                query = em.createQuery("select e from Empresa e", Empresa.class);
            } else {
                query = em.createQuery("select e from Empresa e where e." + field.toLowerCase()
                        + " like '%" + crit + "%'", Empresa.class);
            }
            results = query.getResultList();
            System.out.println("Objetos encontrados: " + results.size());
            em.close();
            emf.close();
            return results;
        }

        if (ent.equals("Residuo")) {
            TypedQuery<Residuo> query;
            List<Residuo> results;
            if (crit.equals("")) {
                query = em.createQuery("select r from Residuo r", Residuo.class);
            } else {
                query = em.createQuery("select r from Residuo r where r." + field.toLowerCase()
                        + " like '%" + crit + "%'", Residuo.class);
            }
            results = query.getResultList();
            System.out.println("Objetos encontrados: " + results.size());
            em.close();
            emf.close();
            return results;
        }

        if (ent.equals("Traslado")) {
            TypedQuery<Traslado> query;
            List<Traslado> results;
            if (crit.equals("")) {
                query = em.createQuery("select t from Traslado t", Traslado.class);
            } else {
                query = em.createQuery("select t from Traslado t where t." + field.toLowerCase()
                        + " like '%" + crit + "%'", Traslado.class);
            }
            results = query.getResultList();
            System.out.println("Objetos encontrados: " + results.size());
            em.close();
            emf.close();
            return results;
        }

        if (ent.equals("Centro_Tratamiento")) {
            TypedQuery<Centro_Tratamiento> query;
            List<Centro_Tratamiento> results;
            if (crit.equals("")) {
                query = em.createQuery("select c from Centro_Tratamiento c", Centro_Tratamiento.class);
            } else {
                query = em.createQuery("select c from Centro_Tratamiento c where c." + field.toLowerCase()
                        + " like '%" + crit + "%'", Centro_Tratamiento.class);
            }
            results = query.getResultList();
            System.out.println("Objetos encontrados: " + results.size());
            em.close();
            emf.close();
            return results;
        }

        if (ent.equals("Composicion_Quimico")) {
            TypedQuery<Composicion_Quimico> query;
            List<Composicion_Quimico> results;
            if (crit.equals("")) {
                query = em.createQuery("select cq from Composicion_Quimico cq", Composicion_Quimico.class);
            } else {
                query = em.createQuery("select cq from Composicion_Quimico cq where cq." + field.toLowerCase()
                        + " like '%" + crit + "%'", Composicion_Quimico.class);
            }
            results = query.getResultList();
            System.out.println("Objetos encontrados: " + results.size());
            em.close();
            emf.close();
            return results;
        }

        if (ent.equals("Envase")) {
            TypedQuery<Envase> query;
            List<Envase> results;
            if (crit.equals("")) {
                query = em.createQuery("select e from Envase e", Envase.class);
            } else {
                query = em.createQuery("select e from Envase e where e." + field.toLowerCase()
                        + " like '%" + crit + "%'", Envase.class);
            }
            results = query.getResultList();
            System.out.println("Objetos encontrados: " + results.size());
            em.close();
            emf.close();
            return results;
        }

        if (ent.equals("Quimico")) {
            TypedQuery<Quimico> query;
            List<Quimico> results;
            if (crit.equals("")) {
                query = em.createQuery("select q from Quimico q", Quimico.class);
            } else {
                query = em.createQuery("select q from Quimico q where q." + field.toLowerCase()
                        + " like '%" + crit + "%'", Quimico.class);
            }
            results = query.getResultList();
            System.out.println("Objetos encontrados: " + results.size());
            em.close();
            emf.close();
            return results;
        }

        if (ent.equals("Tipo_Tratamiento")) {
            TypedQuery<Tipo_Tratamiento> query;
            List<Tipo_Tratamiento> results;
            if (crit.equals("")) {
                query = em.createQuery("select tt from Tipo_Tratamiento tt", Tipo_Tratamiento.class);
            } else {
                query = em.createQuery("select tt from Tipo_Tratamiento tt where tt." + field.toLowerCase()
                        + " like '%" + crit + "%'", Tipo_Tratamiento.class);
            }
            results = query.getResultList();
            System.out.println("Objetos encontrados: " + results.size());
            em.close();
            emf.close();
            return results;
        }

        if (ent.equals("Transporte")) {
            TypedQuery<Transporte> query;
            List<Transporte> results;
            if (crit.equals("")) {
                query = em.createQuery("select tr from Transporte tr", Transporte.class);
            } else {
                query = em.createQuery("select tr from Transporte tr where tr." + field.toLowerCase()
                        + " like '%" + crit + "%'", Transporte.class);
            }
            results = query.getResultList();
            System.out.println("Objetos encontrados: " + results.size());
            em.close();
            emf.close();
            return results;
        }

        if (ent.equals("Transportista")) {
            TypedQuery<Transportista> query;
            List<Transportista> results;
            if (crit.equals("")) {
                query = em.createQuery("select tp from Transportista tp", Transportista.class);
            } else {
                query = em.createQuery("select tp from Transportista tp where tp." + field.toLowerCase()
                        + " like '%" + crit + "%'", Transportista.class);
            }
            results = query.getResultList();
            System.out.println("Objetos encontrados: " + results.size());
            em.close();
            emf.close();
            return results;
        }

        // Cerrar conexión si no se encontró entidad
        em.close();
        emf.close();
        return null;
    }

    public TableModel listtoTM(List rs, String entit) {
        Vector columnNames = new Vector();
        Vector rows = new Vector();

        if (entit.equals("Empresa")) {
            Empresa e;
            columnNames.addElement("Nombre");
            columnNames.addElement("Ubicación");
            columnNames.addElement("Residuos");

            Iterator it = rs.iterator();
            while (it.hasNext()) {
                e = (Empresa) it.next();
                Vector newRow = new Vector();
                newRow.addElement(e.getEmp_nombre());
                newRow.addElement(e.getEmp_ubicacion());
                newRow.addElement(e.getEmp_residuos());
                rows.addElement(newRow);
            }
        }

        if (entit.equals("Residuo")) {
            Residuo r;
            columnNames.addElement("Nombre");
            columnNames.addElement("Cantidad");

            Iterator it = rs.iterator();
            while (it.hasNext()) {
                r = (Residuo) it.next();
                Vector newRow = new Vector();
                newRow.addElement(r.getRes_nombre());
                newRow.addElement(r.getCantidad_total());
                rows.addElement(newRow);
            }
        }

        if (entit.equals("Traslado")) {
            Traslado t;
            columnNames.addElement("Origen");
            columnNames.addElement("Cantidad Trasladada");
            columnNames.addElement("Fecha Inicio");
            columnNames.addElement("Fecha Llegada");

            Iterator it = rs.iterator();
            while (it.hasNext()) {
                t = (Traslado) it.next();
                Vector newRow = new Vector();
                newRow.addElement(t.getTras_origen());
                newRow.addElement(t.getCantidad_trasladada());
                newRow.addElement(t.getFecha_inicio());
                newRow.addElement(t.getFecha_llegada());
                rows.addElement(newRow);
            }
        }

        if (entit.equals("Centro_Tratamiento")) {
            Centro_Tratamiento c;
            columnNames.addElement("Descripción");
            columnNames.addElement("Ubicación");

            Iterator it = rs.iterator();
            while (it.hasNext()) {
                c = (Centro_Tratamiento) it.next();
                Vector newRow = new Vector();
                newRow.addElement(c.getCen_descripcion());
                newRow.addElement(c.getCen_ubicacion());
                rows.addElement(newRow);
            }
        }

        if (entit.equals("Composicion_Quimico")) {
            Composicion_Quimico cq;
            columnNames.addElement("Nombre");
            columnNames.addElement("Cantidad");
            columnNames.addElement("Residuo");
            columnNames.addElement("Químico");

            Iterator it = rs.iterator();
            while (it.hasNext()) {
                cq = (Composicion_Quimico) it.next();
                Vector newRow = new Vector();
                newRow.addElement(cq.getComp_nombre());
                newRow.addElement(cq.getCantidad());
                newRow.addElement(cq.getResiduo() != null ? cq.getResiduo().getRes_nombre() : "N/A");
                newRow.addElement(cq.getQuimico() != null ? cq.getQuimico().getQuim_nombre() : "N/A");
                rows.addElement(newRow);
            }
        }

        if (entit.equals("Envase")) {
            Envase e;
            columnNames.addElement("Descripción");
            columnNames.addElement("Categoría Material");

            Iterator it = rs.iterator();
            while (it.hasNext()) {
                e = (Envase) it.next();
                Vector newRow = new Vector();
                newRow.addElement(e.getEnv_descripcion());
                newRow.addElement(e.getCategoria_material());
                rows.addElement(newRow);
            }
        }

        if (entit.equals("Quimico")) {
            Quimico q;
            columnNames.addElement("Nombre");
            columnNames.addElement("Tipo Peligrosidad");

            Iterator it = rs.iterator();
            while (it.hasNext()) {
                q = (Quimico) it.next();
                Vector newRow = new Vector();
                newRow.addElement(q.getQuim_nombre());
                newRow.addElement(q.getTipo_peligrosidad());
                rows.addElement(newRow);
            }
        }

        if (entit.equals("Tipo_Tratamiento")) {
            Tipo_Tratamiento tt;
            columnNames.addElement("Descripción");

            Iterator it = rs.iterator();
            while (it.hasNext()) {
                tt = (Tipo_Tratamiento) it.next();
                Vector newRow = new Vector();
                newRow.addElement(tt.getTrat_descripcion());
                rows.addElement(newRow);
            }
        }

        if (entit.equals("Transporte")) {
            Transporte tr;
            columnNames.addElement("Tipo");
            columnNames.addElement("Transportista");

            Iterator it = rs.iterator();
            while (it.hasNext()) {
                tr = (Transporte) it.next();
                Vector newRow = new Vector();
                newRow.addElement(tr.getTrans_tipo());
                newRow.addElement(tr.getTransportista() != null ? tr.getTransportista().getTrans_nombre() : "N/A");
                rows.addElement(newRow);
            }
        }

        if (entit.equals("Transportista")) {
            Transportista tp;
            columnNames.addElement("Nombre");
            columnNames.addElement("Dirección");
            columnNames.addElement("Teléfono");

            Iterator it = rs.iterator();
            while (it.hasNext()) {
                tp = (Transportista) it.next();
                Vector newRow = new Vector();
                newRow.addElement(tp.getTrans_nombre());
                newRow.addElement(tp.getDireccion());
                newRow.addElement(tp.getTelefono());
                rows.addElement(newRow);
            }
        }

        return new DefaultTableModel(rows, columnNames);
    }

    public TableModel opBuscar(String ent, String field, String crit) {
        TableModel tm = null;

        if (ent.equals("Empresa")) {
            List<Empresa> res = opRead(ent, field, crit);
            tm = listtoTM(res, ent);
        }

        if (ent.equals("Residuo")) {
            List<Residuo> res = opRead(ent, field, crit);
            tm = listtoTM(res, ent);
        }

        if (ent.equals("Traslado")) {
            List<Traslado> res = opRead(ent, field, crit);
            tm = listtoTM(res, ent);
        }

        if (ent.equals("Centro_Tratamiento")) {
            List<Centro_Tratamiento> res = opRead(ent, field, crit);
            tm = listtoTM(res, ent);
        }

        if (ent.equals("Composicion_Quimico")) {
            List<Composicion_Quimico> res = opRead(ent, field, crit);
            tm = listtoTM(res, ent);
        }

        if (ent.equals("Envase")) {
            List<Envase> res = opRead(ent, field, crit);
            tm = listtoTM(res, ent);
        }

        if (ent.equals("Quimico")) {
            List<Quimico> res = opRead(ent, field, crit);
            tm = listtoTM(res, ent);
        }

        if (ent.equals("Tipo_Tratamiento")) {
            List<Tipo_Tratamiento> res = opRead(ent, field, crit);
            tm = listtoTM(res, ent);
        }

        if (ent.equals("Transporte")) {
            List<Transporte> res = opRead(ent, field, crit);
            tm = listtoTM(res, ent);
        }

        if (ent.equals("Transportista")) {
            List<Transportista> res = opRead(ent, field, crit);
            tm = listtoTM(res, ent);
        }

        return tm;
    }

    public Object opBuscar1(String ent, String crit) {
        Object c = null;
        // Abrir conexión a la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();

        if (ent.equals("Empresa")) {
            c = em.find(Empresa.class, crit); // PK: emp_nombre
        }
        if (ent.equals("Residuo")) {
            c = em.find(Residuo.class, crit); // PK: res_nombre
        }
        if (ent.equals("Traslado")) {
            c = em.find(Traslado.class, crit); // PK: tras_id (ajusta según tu clase)
        }
        if (ent.equals("Centro_Tratamiento")) {
            c = em.find(Centro_Tratamiento.class, crit); // PK: cen_descripcion
        }
        if (ent.equals("Composicion_Quimico")) {
            c = em.find(Composicion_Quimico.class, crit); // PK: comp_nombre
        }
        if (ent.equals("Envase")) {
            c = em.find(Envase.class, crit); // PK: env_descripcion
        }
        if (ent.equals("Quimico")) {
            c = em.find(Quimico.class, crit); // PK: quim_nombre
        }
        if (ent.equals("Tipo_Tratamiento")) {
            c = em.find(Tipo_Tratamiento.class, crit); // PK: trat_descripcion
        }
        if (ent.equals("Transporte")) {
            c = em.find(Transporte.class, crit); // PK: trans_tipo
        }
        if (ent.equals("Transportista")) {
            c = em.find(Transportista.class, crit); // PK: trans_nombre
        }

        // Cerrar conexión
        em.close();
        emf.close();
        return c;
    }

    public void opDelete(String ent, String crit) {
        Object c = null;
        // Abrir conexión a la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        if (ent.equals("Empresa")) {
            c = em.find(Empresa.class, crit); // PK: emp_nombre
            if (c != null) {
                em.remove(c);
            }
        }
        if (ent.equals("Residuo")) {
            c = em.find(Residuo.class, crit); // PK: res_nombre
            if (c != null) {
                em.remove(c);
            }
        }
        if (ent.equals("Traslado")) {
            c = em.find(Traslado.class, crit); // PK: tras_id (ajusta según tu clase)
            if (c != null) {
                em.remove(c);
            }
        }
        if (ent.equals("Centro_Tratamiento")) {
            c = em.find(Centro_Tratamiento.class, crit); // PK: cen_descripcion
            if (c != null) {
                em.remove(c);
            }
        }
        if (ent.equals("Composicion_Quimico")) {
            c = em.find(Composicion_Quimico.class, crit); // PK: comp_nombre
            if (c != null) {
                em.remove(c);
            }
        }
        if (ent.equals("Envase")) {
            c = em.find(Envase.class, crit); // PK: env_descripcion
            if (c != null) {
                em.remove(c);
            }
        }
        if (ent.equals("Quimico")) {
            c = em.find(Quimico.class, crit); // PK: quim_nombre
            if (c != null) {
                em.remove(c);
            }
        }
        if (ent.equals("Tipo_Tratamiento")) {
            c = em.find(Tipo_Tratamiento.class, crit); // PK: trat_descripcion
            if (c != null) {
                em.remove(c);
            }
        }
        if (ent.equals("Transporte")) {
            c = em.find(Transporte.class, crit); // PK: trans_tipo
            if (c != null) {
                em.remove(c);
            }
        }
        if (ent.equals("Transportista")) {
            c = em.find(Transportista.class, crit); // PK: trans_nombre
            if (c != null) {
                em.remove(c);
            }
        }

        em.getTransaction().commit();
        // Cerrar conexión
        em.close();
        emf.close();
        System.out.println("Objeto eliminado");
    }

}
