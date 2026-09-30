package Residuos;

import clases.*;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class opCRUD {


    public void opCreateEmpresa(Empresa ee) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(ee);
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Empresa registrada");
    }

    public void opCreateResiduo(Residuo rr) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(rr);
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Residuo registrado");
    }

    public void opCreateTraslado(Traslado tt) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(tt);
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Traslado registrado");
    }

    public void opCreateCentro(Centro_Tratamiento cc) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(cc);
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Centro de Tratamiento registrado");
    }

    public void opCreateComposicion(Composicion_Quimico cc) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(cc);
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Composición registrada");
    }

    public void opCreateEnvase(Envase ee) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(ee);
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Envase registrado");
    }

    public void opCreateQuimico(Quimico qq) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(qq);
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Químico registrado");
    }

    public void opCreateTratamiento(Tipo_Tratamiento tt) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(tt);
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Tipo de Tratamiento registrado");
    }

    public void opCreateTransporte(Transporte tt) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(tt);
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Transporte registrado");
    }

    public void opCreateTransportista(Transportista tt) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(tt);
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Transportista registrado");
    }

    // ==========================================
    // UPDATES
    // ==========================================

    public void opUpdateEmpresa(Empresa ee) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Empresa e = em.find(Empresa.class, ee.getEmp_nombre());
        if (e != null) {
            e.setEmp_ubicacion(ee.getEmp_ubicacion());
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Empresa actualizada");
    }

    public void opUpdateResiduo(Residuo rr) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Residuo r = em.find(Residuo.class, rr.getRes_nombre());
        if (r != null) {
            r.setCantidad_total(rr.getCantidad_total());
            if (rr.getEmpresa() != null) {
                rr.getEmpresa().formEmp_residuo(r);
            }
            if (rr.getEnvase() != null) {
                rr.getEnvase().formEnv_residuo(r);
            }
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Residuo actualizado");
    }

    public void opUpdateTraslado(Traslado tt) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Traslado t = em.find(Traslado.class, tt.getTras_origen());
        if (t != null) {
            t.setCantidad_trasladada(tt.getCantidad_trasladada());
            t.setFecha_inicio(tt.getFecha_inicio());
            t.setFecha_llegada(tt.getFecha_llegada());
            t.setCosto(tt.getCosto());
            t.setKm_recorridos(tt.getKm_recorridos());
            if (tt.getEmpresa() != null) tt.getEmpresa().formEmp_traslado(t);
            if (tt.getResiduo() != null) tt.getResiduo().formRes_traslado(t);
            if (tt.getCentro() != null) tt.getCentro().formCen_traslado(t);
            if (tt.getTratamiento() != null) tt.getTratamiento().formTrat_traslado(t);
            if (tt.getTransporte() != null) tt.getTransporte().formTrans_traslado(t);
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Traslado actualizado");
    }

    public void opUpdateCentro(Centro_Tratamiento cc) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Centro_Tratamiento c = em.find(Centro_Tratamiento.class, cc.getCen_descripcion());
        if (c != null) {
            c.setCen_ubicacion(cc.getCen_ubicacion());
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Centro de Tratamiento actualizado");
    }

    public void opUpdateComposicion(Composicion_Quimico cc) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Composicion_Quimico c = em.find(Composicion_Quimico.class, cc.getComp_nombre());
        if (c != null) {
            c.setCantidad(cc.getCantidad());
            if (cc.getResiduo() != null) cc.getResiduo().formRes_composicion(c);
            if (cc.getQuimico() != null) cc.getQuimico().formQuim_composicion(c);
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Composición actualizada");
    }

    public void opUpdateEnvase(Envase ee) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Envase e = em.find(Envase.class, ee.getEnv_descripcion());
        if (e != null) {
            e.setCategoria_material(ee.getCategoria_material());
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Envase actualizado");
    }

    public void opUpdateQuimico(Quimico qq) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Quimico q = em.find(Quimico.class, qq.getQuim_nombre());
        if (q != null) {
            q.setTipo_peligrosidad(qq.getTipo_peligrosidad());
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Químico actualizado");
    }

    public void opUpdateTratamiento(Tipo_Tratamiento tt) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Tipo_Tratamiento t = em.find(Tipo_Tratamiento.class, tt.getTrat_descripcion());
        if (t != null) {
            // Se actualizan atributos escalares si los hubiera
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Tipo de Tratamiento actualizado");
    }

    public void opUpdateTransporte(Transporte tt) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Transporte t = em.find(Transporte.class, tt.getTrans_tipo());
        if (t != null) {
            if (tt.getTransportista() != null) {
                tt.getTransportista().formTrans_transportes(t);
            }
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Transporte actualizado");
    }

    public void opUpdateTransportista(Transportista tt) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Transportista t = em.find(Transportista.class, tt.getTrans_nombre());
        if (t != null) {
            t.setDireccion(tt.getDireccion());
            t.setTelefono(tt.getTelefono());
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
        System.out.println("Transportista actualizado");
    }

    // ==========================================
    // READ / BUSQUEDA
    // ==========================================

    public List opRead(String ent, String field, String crit) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        List results = null;

        String queryStr = crit.equals("") 
            ? "select x from " + ent + " x"
            : "select x from " + ent + " x where x." + field.toLowerCase() + " like '%" + crit + "%'";

        switch (ent) {
            case "Empresa":
                results = em.createQuery(queryStr, Empresa.class).getResultList();
                break;
            case "Residuo":
                results = em.createQuery(queryStr, Residuo.class).getResultList();
                break;
            case "Traslado":
                results = em.createQuery(queryStr, Traslado.class).getResultList();
                break;
            case "Centro_Tratamiento":
                results = em.createQuery(queryStr, Centro_Tratamiento.class).getResultList();
                break;
            case "Composicion_Quimico":
                results = em.createQuery(queryStr, Composicion_Quimico.class).getResultList();
                break;
            case "Envase":
                results = em.createQuery(queryStr, Envase.class).getResultList();
                break;
            case "Quimico":
                results = em.createQuery(queryStr, Quimico.class).getResultList();
                break;
            case "Tipo_Tratamiento":
                results = em.createQuery(queryStr, Tipo_Tratamiento.class).getResultList();
                break;
            case "Transporte":
                results = em.createQuery(queryStr, Transporte.class).getResultList();
                break;
            case "Transportista":
                results = em.createQuery(queryStr, Transportista.class).getResultList();
                break;
        }

        System.out.println("Objetos encontrados: " + (results != null ? results.size() : 0));
        em.close();
        emf.close();
        return results;
    }

    public TableModel listtoTM(List rs, String entit) {
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> rows = new Vector<>();

        if (rs == null) return new DefaultTableModel(rows, columnNames);

        switch (entit) {
            case "Empresa":
                columnNames.add("Nombre");
                columnNames.add("Ubicación");
                columnNames.add("Total Residuos");
                for (Object item : rs) {
                    Empresa e = (Empresa) item;
                    Vector<Object> row = new Vector<>();
                    row.add(e.getEmp_nombre());
                    row.add(e.getEmp_ubicacion());
                    row.add(e.getEmp_residuos() != null ? e.getEmp_residuos().size() : 0);
                    rows.add(row);
                }
                break;

            case "Residuo":
                columnNames.add("Nombre");
                columnNames.add("Cantidad Total");
                columnNames.add("Empresa");
                columnNames.add("Envase");
                for (Object item : rs) {
                    Residuo r = (Residuo) item;
                    Vector<Object> row = new Vector<>();
                    row.add(r.getRes_nombre());
                    row.add(r.getCantidad_total());
                    row.add(r.getEmpresa() != null ? r.getEmpresa().getEmp_nombre() : "N/A");
                    row.add(r.getEnvase() != null ? r.getEnvase().getEnv_descripcion() : "N/A");
                    rows.add(row);
                }
                break;

            case "Traslado":
                columnNames.add("Origen");
                columnNames.add("Cantidad");
                columnNames.add("Fecha Inicio");
                columnNames.add("Fecha Llegada");
                columnNames.add("Empresa");
                columnNames.add("Centro");
                for (Object item : rs) {
                    Traslado t = (Traslado) item;
                    Vector<Object> row = new Vector<>();
                    row.add(t.getTras_origen());
                    row.add(t.getCantidad_trasladada());
                    row.add(t.getFecha_inicio());
                    row.add(t.getFecha_llegada());
                    row.add(t.getEmpresa() != null ? t.getEmpresa().getEmp_nombre() : "N/A");
                    row.add(t.getCentro() != null ? t.getCentro().getCen_descripcion() : "N/A");
                    rows.add(row);
                }
                break;

            case "Centro_Tratamiento":
                columnNames.add("Descripción");
                columnNames.add("Ubicación");
                for (Object item : rs) {
                    Centro_Tratamiento c = (Centro_Tratamiento) item;
                    Vector<Object> row = new Vector<>();
                    row.add(c.getCen_descripcion());
                    row.add(c.getCen_ubicacion());
                    rows.add(row);
                }
                break;

            case "Composicion_Quimico":
                columnNames.add("Nombre");
                columnNames.add("Cantidad");
                columnNames.add("Residuo");
                columnNames.add("Químico");
                for (Object item : rs) {
                    Composicion_Quimico cq = (Composicion_Quimico) item;
                    Vector<Object> row = new Vector<>();
                    row.add(cq.getComp_nombre());
                    row.add(cq.getCantidad());
                    row.add(cq.getResiduo() != null ? cq.getResiduo().getRes_nombre() : "N/A");
                    row.add(cq.getQuimico() != null ? cq.getQuimico().getQuim_nombre() : "N/A");
                    rows.add(row);
                }
                break;

            case "Envase":
                columnNames.add("Descripción");
                columnNames.add("Categoría Material");
                for (Object item : rs) {
                    Envase e = (Envase) item;
                    Vector<Object> row = new Vector<>();
                    row.add(e.getEnv_descripcion());
                    row.add(e.getCategoria_material());
                    rows.add(row);
                }
                break;

            case "Quimico":
                columnNames.add("Nombre");
                columnNames.add("Peligrosidad");
                for (Object item : rs) {
                    Quimico q = (Quimico) item;
                    Vector<Object> row = new Vector<>();
                    row.add(q.getQuim_nombre());
                    row.add(q.getTipo_peligrosidad());
                    rows.add(row);
                }
                break;

            case "Tipo_Tratamiento":
                columnNames.add("Descripción");
                for (Object item : rs) {
                    Tipo_Tratamiento tt = (Tipo_Tratamiento) item;
                    Vector<Object> row = new Vector<>();
                    row.add(tt.getTrat_descripcion());
                    rows.add(row);
                }
                break;

            case "Transporte":
                columnNames.add("Tipo");
                columnNames.add("Transportista");
                for (Object item : rs) {
                    Transporte tr = (Transporte) item;
                    Vector<Object> row = new Vector<>();
                    row.add(tr.getTrans_tipo());
                    row.add(tr.getTransportista() != null ? tr.getTransportista().getTrans_nombre() : "N/A");
                    rows.add(row);
                }
                break;

            case "Transportista":
                columnNames.add("Nombre");
                columnNames.add("Dirección");
                columnNames.add("Teléfono");
                for (Object item : rs) {
                    Transportista tp = (Transportista) item;
                    Vector<Object> row = new Vector<>();
                    row.add(tp.getTrans_nombre());
                    row.add(tp.getDireccion());
                    row.add(tp.getTelefono());
                    rows.add(row);
                }
                break;
        }

        return new DefaultTableModel(rows, columnNames);
    }

    public TableModel opBuscar(String ent, String field, String crit) {
        List res = opRead(ent, field, crit);
        return listtoTM(res, ent);
    }

    public Object opBuscar1(String ent, String crit) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        EntityManager em = emf.createEntityManager();
        Object result = null;

        switch (ent) {
            case "Empresa":
                result = em.find(Empresa.class, crit);
                break;
            case "Residuo":
                result = em.find(Residuo.class, crit);
                break;
            case "Traslado":
                result = em.find(Traslado.class, crit);
                break;
            case "Centro_Tratamiento":
                result = em.find(Centro_Tratamiento.class, crit);
                break;
            case "Composicion_Quimico":
                result = em.find(Composicion_Quimico.class, crit);
                break;
            case "Envase":
                result = em.find(Envase.class, crit);
                break;
            case "Quimico":
                result = em.find(Quimico.class, crit);
                break;
            case "Tipo_Tratamiento":
                result = em.find(Tipo_Tratamiento.class, crit);
                break;
            case "Transporte":
                result = em.find(Transporte.class, crit);
                break;
            case "Transportista":
                result = em.find(Transportista.class, crit);
                break;
        }

        em.close();
        emf.close();
        return result;
    }
}