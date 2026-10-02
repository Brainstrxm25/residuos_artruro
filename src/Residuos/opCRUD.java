package Residuos;

import clases.*;
import java.util.*;
import javax.persistence.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * Capa de acceso a datos del proyecto.
 * Centraliza las operaciones CRUD y consultas realizadas con ObjectDB/JPA.
 */
public class opCRUD {
    // Ruta central de la base ObjectDB.
    public static final String DB = "$objectdb/db/residuosdb.odb";

    // Abre una fábrica JPA para trabajar con ObjectDB.
    private EntityManagerFactory nuevoEMF() {
        return Persistence.createEntityManagerFactory(DB);
    }

    // Busca un registro usando su clase y clave primaria.
    public <T> T find(Class<T> tipo, Object id) {
        EntityManagerFactory emf = nuevoEMF();
        EntityManager em = emf.createEntityManager();
        try { return em.find(tipo, id); }
        finally { em.close(); emf.close(); }
    }

    // Indica si ya existe un registro con esa clave primaria.
    public boolean exists(Class<?> tipo, Object id) { return find(tipo, id) != null; }

    // Guarda un objeto nuevo dentro de una transacción.
    public void create(Object objeto) {
        if (objeto == null) throw new IllegalArgumentException("El objeto no puede ser nulo.");
        EntityManagerFactory emf = nuevoEMF();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(objeto);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally { em.close(); emf.close(); }
    }

    // Actualiza un objeto existente usando merge().
    public <T> T update(T objeto) {
        if (objeto == null) throw new IllegalArgumentException("El objeto no puede ser nulo.");
        EntityManagerFactory emf = nuevoEMF();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T actualizado = em.merge(objeto);
            em.getTransaction().commit();
            return actualizado;
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally { em.close(); emf.close(); }
    }

    // Devuelve todos los registros de una entidad.
    public <T> List<T> readAll(Class<T> tipo) {
        EntityManagerFactory emf = nuevoEMF();
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("select e from " + tipo.getSimpleName() + " e", tipo).getResultList();
        } finally { em.close(); emf.close(); }
    }

    // Busca por campo; compara números exactamente y texto con LIKE.
    public List<?> opRead(String entidad, String campo, String criterio) {
        Class<?> tipo = clase(entidad);
        String field = campoReal(entidad, campo);
        EntityManagerFactory emf = nuevoEMF();
        EntityManager em = emf.createEntityManager();
        try {
            String all = "select e from " + tipo.getSimpleName() + " e";
            if (criterio == null || criterio.trim().isEmpty()) return em.createQuery(all, tipo).getResultList();
            String c = criterio.trim();
            List<?> result;
            if (esNumerico(entidad, field)) {
                try {
                    double valor = Double.parseDouble(c);
                    TypedQuery<?> q = em.createQuery(all + " where e." + field + " = :valor", tipo);
                    q.setParameter("valor", valor);
                    result = q.getResultList();
                } catch (NumberFormatException ex) {
                    result = Collections.emptyList();
                }
            } else {
                TypedQuery<?> q = em.createQuery(all + " where lower(e." + field + ") like :valor", tipo);
                q.setParameter("valor", "%" + c.toLowerCase(Locale.ROOT) + "%");
                result = q.getResultList();
            }
            return result.isEmpty() ? em.createQuery(all, tipo).getResultList() : result;
        } finally { em.close(); emf.close(); }
    }

    public Object opBuscar1(String entidad, String criterio) {
        return find(clase(entidad), criterio);
    }

    // Busca registros y los convierte a un modelo para JTable.
    public TableModel opBuscar(String entidad, String campo, String criterio) {
        return listToTable(opRead(entidad, campo, criterio), entidad);
    }

    public TableModel listtoTM(List<?> lista, String entidad) { return listToTable(lista, entidad); }

    // Convierte una lista de entidades en columnas/filas para la interfaz.
    public TableModel listToTable(List<?> lista, String entidad) {
        List<String> columnas = new ArrayList<>();
        List<Object[]> filas = new ArrayList<>();
        switch (entidad) {
            case "Empresa":
                columnas.addAll(Arrays.asList("Nombre", "Ubicación"));
                for (Object o : lista) { Empresa e=(Empresa)o; filas.add(new Object[]{e.getEmp_nombre(),e.getEmp_ubicacion()}); }
                break;
            case "Residuo":
                columnas.addAll(Arrays.asList("Nombre", "Cantidad", "Empresa", "Envase"));
                for (Object o : lista) { Residuo r=(Residuo)o; filas.add(new Object[]{r.getRes_nombre(),r.getCantidad_total(),r.getEmpresa()!=null?r.getEmpresa().getEmp_nombre():"N/A",r.getEnvase()!=null?r.getEnvase().getEnv_descripcion():"N/A"}); }
                break;
            case "Envase":
                columnas.addAll(Arrays.asList("Descripción", "Categoría"));
                for (Object o : lista) { Envase e=(Envase)o; filas.add(new Object[]{e.getEnv_descripcion(),e.getCategoria_material()}); }
                break;
            case "Quimico":
                columnas.addAll(Arrays.asList("Nombre", "Peligrosidad"));
                for (Object o : lista) { Quimico q=(Quimico)o; filas.add(new Object[]{q.getQuim_nombre(),q.getTipo_peligrosidad()}); }
                break;
            case "Tipo_Tratamiento":
                columnas.add("Descripción");
                for (Object o : lista) { Tipo_Tratamiento t=(Tipo_Tratamiento)o; filas.add(new Object[]{t.getTrat_descripcion()}); }
                break;
            case "Transporte":
                columnas.addAll(Arrays.asList("Tipo", "Transportista"));
                for (Object o : lista) { Transporte t=(Transporte)o; filas.add(new Object[]{t.getTrans_tipo(),t.getTransportista()!=null?t.getTransportista().getTrans_nombre():"N/A"}); }
                break;
            case "Transportista":
                columnas.addAll(Arrays.asList("Nombre", "Dirección", "Teléfono"));
                for (Object o : lista) { Transportista t=(Transportista)o; filas.add(new Object[]{t.getTrans_nombre(),t.getDireccion(),t.getTelefono()}); }
                break;
            case "Traslado":
                columnas.addAll(Arrays.asList("Origen","Cantidad","Inicio","Llegada","Costo","Km","Empresa","Residuo","Centro","Tratamiento","Transporte"));
                for (Object o : lista) { Traslado t=(Traslado)o; filas.add(new Object[]{t.getTras_origen(),t.getCantidad_trasladada(),t.getFecha_inicio(),t.getFecha_llegada(),t.getCosto(),t.getKm_recorridos(),t.getEmpresa()!=null?t.getEmpresa().getEmp_nombre():"N/A",t.getResiduo()!=null?t.getResiduo().getRes_nombre():"N/A",t.getCentro()!=null?t.getCentro().getCen_descripcion():"N/A",t.getTratamiento()!=null?t.getTratamiento().getTrat_descripcion():"N/A",t.getTransporte()!=null?t.getTransporte().getTrans_tipo():"N/A"}); }
                break;
            case "Centro_Tratamiento":
                columnas.addAll(Arrays.asList("Descripción", "Ubicación"));
                for (Object o : lista) { Centro_Tratamiento c=(Centro_Tratamiento)o; filas.add(new Object[]{c.getCen_descripcion(),c.getCen_ubicacion()}); }
                break;
            case "Composicion_Quimico":
                columnas.addAll(Arrays.asList("Nombre","Cantidad","Residuo","Químico"));
                for (Object o : lista) { Composicion_Quimico c=(Composicion_Quimico)o; filas.add(new Object[]{c.getComp_nombre(),c.getCantidad(),c.getResiduo()!=null?c.getResiduo().getRes_nombre():"N/A",c.getQuimico()!=null?c.getQuimico().getQuim_nombre():"N/A"}); }
                break;
        }
        DefaultTableModel m = new DefaultTableModel(columnas.toArray(),0);
        for (Object[] f: filas) m.addRow(f);
        return m;
    }

    // Elimina solo si el registro no tiene relaciones que lo bloqueen.
    public void opDelete(String entidad, String id) {
        EntityManagerFactory emf = nuevoEMF();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Object obj = em.find(clase(entidad), id);
            if (obj == null) throw new IllegalArgumentException("No existe un registro con esa clave primaria.");
            String relacion = restriccionBorrado(em, entidad, id);
            if (relacion != null) throw new IllegalStateException("No se puede borrar: " + relacion);
            em.remove(obj);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally { em.close(); emf.close(); }
    }

    private long count(EntityManager em, String jpql, String id) {
        TypedQuery<Long> q=em.createQuery(jpql,Long.class); q.setParameter("id",id); return q.getSingleResult();
    }

    // Comprueba dependencias antes de permitir una eliminación.
    private String restriccionBorrado(EntityManager em, String entidad, String id) {
        long n;
        switch(entidad) {
            case "Empresa":
                n=count(em,"select count(r) from Residuo r where r.empresa.emp_nombre=:id",id); if(n>0)return "la empresa tiene "+n+" residuo(s) asociado(s).";
                n=count(em,"select count(t) from Traslado t where t.empresa.emp_nombre=:id",id); if(n>0)return "la empresa tiene "+n+" traslado(s) asociado(s)."; break;
            case "Residuo":
                n=count(em,"select count(c) from Composicion_Quimico c where c.residuo.res_nombre=:id",id); if(n>0)return "el residuo tiene "+n+" composición(es) asociada(s).";
                n=count(em,"select count(t) from Traslado t where t.residuo.res_nombre=:id",id); if(n>0)return "el residuo tiene "+n+" traslado(s) asociado(s)."; break;
            case "Envase":
                n=count(em,"select count(r) from Residuo r where r.envase.env_descripcion=:id",id); if(n>0)return "el envase está asociado a "+n+" residuo(s)."; break;
            case "Quimico":
                n=count(em,"select count(c) from Composicion_Quimico c where c.quimico.quim_nombre=:id",id); if(n>0)return "el químico está asociado a "+n+" composición(es)."; break;
            case "Centro_Tratamiento":
                n=count(em,"select count(t) from Traslado t where t.centro.cen_descripcion=:id",id); if(n>0)return "el centro está asociado a "+n+" traslado(s)."; break;
            case "Tipo_Tratamiento":
                n=count(em,"select count(t) from Traslado t where t.tratamiento.trat_descripcion=:id",id); if(n>0)return "el tratamiento está asociado a "+n+" traslado(s)."; break;
            case "Transporte":
                n=count(em,"select count(t) from Traslado t where t.transporte.trans_tipo=:id",id); if(n>0)return "el transporte está asociado a "+n+" traslado(s)."; break;
            case "Transportista":
                n=count(em,"select count(t) from Transporte t where t.transportista.trans_nombre=:id",id); if(n>0)return "el transportista está asociado a "+n+" transporte(s)."; break;
        }
        return null;
    }

    // Traduce el nombre textual de la entidad a su clase Java.
    private Class<?> clase(String e) {
        switch(e){
            case "Empresa":return Empresa.class; case "Residuo":return Residuo.class; case "Envase":return Envase.class;
            case "Quimico":return Quimico.class; case "Tipo_Tratamiento":return Tipo_Tratamiento.class; case "Transporte":return Transporte.class;
            case "Transportista":return Transportista.class; case "Traslado":return Traslado.class; case "Centro_Tratamiento":return Centro_Tratamiento.class;
            case "Composicion_Quimico":return Composicion_Quimico.class; default:throw new IllegalArgumentException("Entidad no válida: "+e);
        }
    }

    // Traduce nombres amigables de campos a sus atributos reales.
    private String campoReal(String entidad,String campo){
        if(campo==null||campo.trim().isEmpty()) return pk(entidad);
        String f=campo.trim().toLowerCase(Locale.ROOT);
        switch(entidad){
            case "Empresa": if(f.equals("nombre")||f.equals("emp_nombre"))return "emp_nombre"; if(f.equals("ubicacion")||f.equals("emp_ubicacion"))return "emp_ubicacion"; break;
            case "Residuo": if(f.equals("nombre")||f.equals("res_nombre"))return "res_nombre"; if(f.equals("cantidad")||f.equals("cantidad_total"))return "cantidad_total"; break;
            case "Envase": if(f.equals("descripcion")||f.equals("env_descripcion"))return "env_descripcion"; if(f.equals("categoria")||f.equals("categoria_material"))return "categoria_material"; break;
            case "Quimico": if(f.equals("nombre")||f.equals("quim_nombre"))return "quim_nombre"; if(f.equals("peligrosidad")||f.equals("tipo_peligrosidad"))return "tipo_peligrosidad"; break;
            case "Tipo_Tratamiento": if(f.equals("descripcion")||f.equals("trat_descripcion"))return "trat_descripcion"; break;
            case "Transporte": if(f.equals("tipo")||f.equals("trans_tipo"))return "trans_tipo"; break;
            case "Transportista": if(f.equals("nombre")||f.equals("trans_nombre"))return "trans_nombre"; if(f.equals("direccion"))return "direccion"; if(f.equals("telefono"))return "telefono"; break;
            case "Traslado": if(f.equals("origen")||f.equals("tras_origen"))return "tras_origen"; if(f.equals("cantidad")||f.equals("cantidad_trasladada"))return "cantidad_trasladada"; if(f.equals("inicio")||f.equals("fecha_inicio"))return "fecha_inicio"; if(f.equals("llegada")||f.equals("fecha_llegada"))return "fecha_llegada"; if(f.equals("costo"))return "costo"; if(f.equals("km")||f.equals("km_recorridos"))return "km_recorridos"; break;
        }
        throw new IllegalArgumentException("Campo de búsqueda no válido: "+campo);
    }
    private boolean esNumerico(String e,String f){return (e.equals("Residuo")&&f.equals("cantidad_total"))||(e.equals("Traslado")&&(f.equals("cantidad_trasladada")||f.equals("costo")||f.equals("km_recorridos")));}
    private String pk(String e){switch(e){case "Empresa":return "emp_nombre";case "Residuo":return "res_nombre";case "Envase":return "env_descripcion";case "Quimico":return "quim_nombre";case "Tipo_Tratamiento":return "trat_descripcion";case "Transporte":return "trans_tipo";case "Transportista":return "trans_nombre";case "Traslado":return "tras_origen";case "Centro_Tratamiento":return "cen_descripcion";case "Composicion_Quimico":return "comp_nombre";default:throw new IllegalArgumentException();}}

    // Compatibilidad con llamadas del código existente.
    // Métodos de compatibilidad por entidad; reutilizan create() y update().
    public void opCreateEmpresa(Empresa e){create(e);} public void opUpdateEmpresa(Empresa e){update(e);}
    public void opCreateResiduo(Residuo e){create(e);} public void opUpdateResiduo(Residuo e){update(e);}
    public void opCreateEnvase(Envase e){create(e);} public void opUpdateEnvase(Envase e){update(e);}
    public void opCreateQuimico(Quimico e){create(e);} public void opUpdateQuimico(Quimico e){update(e);}
    public void opCreateTratamiento(Tipo_Tratamiento e){create(e);} public void opUpdateTratamiento(Tipo_Tratamiento e){update(e);}
    public void opCreateTransporte(Transporte e){create(e);} public void opUpdateTransporte(Transporte e){update(e);}
    public void opCreateTransportista(Transportista e){create(e);} public void opUpdateTransportista(Transportista e){update(e);}
    public void opCreateTraslado(Traslado e){create(e);} public void opUpdateTraslado(Traslado e){update(e);}
    public void opCreateCentro(Centro_Tratamiento e){create(e);} public void opUpdateCentro(Centro_Tratamiento e){update(e);}
    public void opCreateComposicion(Composicion_Quimico e){create(e);} public void opUpdateComposicion(Composicion_Quimico e){update(e);}
}
