
package Test;

import clases.*;
import javax.persistence.*;
import java.io.File;

/** Programa de prueba que crea datos y verifica entidades, relaciones y consultas. */
public class Test {

    private static final String RUTA_DB = "$objectdb/db/residuosdb.odb";

    public static void main(String[] args) {

        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {

            /*
             * =========================================================
             * 1. ELIMINAR LA BASE DE DATOS ANTERIOR
             * =========================================================
             *
             * Esto permite ejecutar Test.java varias veces sin obtener
             * errores de clave primaria duplicada.
             *
             * ObjectDB reemplaza $objectdb por su directorio de
             * instalación.
             */
            eliminarBaseAnterior();

            /*
             * =========================================================
             * 2. CREAR UNA BASE DE DATOS NUEVA
             * =========================================================
             */
            emf = Persistence.createEntityManagerFactory(RUTA_DB);
            em = emf.createEntityManager();

            em.getTransaction().begin();

            /*
             * =========================================================
             * 3. ENTIDADES PRINCIPALES
             * =========================================================
             */

            // EMPRESAS
            Empresa e1 = new Empresa(
                    "EcoPachuca",
                    "Pachuca"
            );

            Empresa e2 = new Empresa(
                    "ReciclaHidalgo",
                    "Tepeji"
            );

            em.persist(e1);
            em.persist(e2);


            // ENVASES
            Envase env1 = new Envase(
                    "Bidón 50L",
                    "Plástico"
            );

            Envase env2 = new Envase(
                    "Tambor metálico",
                    "Metal"
            );

            em.persist(env1);
            em.persist(env2);


            // QUÍMICOS
            Quimico q1 = new Quimico(
                    "Mercurio",
                    "Alta"
            );

            Quimico q2 = new Quimico(
                    "Plomo",
                    "Media"
            );

            em.persist(q1);
            em.persist(q2);


            // CENTROS DE TRATAMIENTO
            Centro_Tratamiento ct1 = new Centro_Tratamiento(
                    "Planta Pachuca",
                    "Zona Industrial"
            );

            Centro_Tratamiento ct2 = new Centro_Tratamiento(
                    "Planta Tepeji",
                    "Zona Sur"
            );

            em.persist(ct1);
            em.persist(ct2);


            // TIPOS DE TRATAMIENTO
            Tipo_Tratamiento tt1 = new Tipo_Tratamiento(
                    "Incineración"
            );

            Tipo_Tratamiento tt2 = new Tipo_Tratamiento(
                    "Reciclaje"
            );

            em.persist(tt1);
            em.persist(tt2);


            // TRANSPORTISTAS
            Transportista tp1 = new Transportista(
                    "TransHidalgo",
                    "Av. Reforma",
                    "7719876543"
            );

            Transportista tp2 = new Transportista(
                    "TransTepeji",
                    "Centro",
                    "7731234567"
            );

            em.persist(tp1);
            em.persist(tp2);


            /*
             * =========================================================
             * 4. RESIDUOS
             * =========================================================
             */

            Residuo r1 = new Residuo(
                    "Plástico",
                    200
            );

            r1.setEmpresa(e1);
            r1.setEnvase(env1);

            e1.formEmp_residuo(r1);
            env1.formEnv_residuo(r1);

            em.persist(r1);


            Residuo r2 = new Residuo(
                    "Aceite usado",
                    50
            );

            r2.setEmpresa(e2);
            r2.setEnvase(env2);

            e2.formEmp_residuo(r2);
            env2.formEnv_residuo(r2);

            em.persist(r2);


            /*
             * =========================================================
             * 5. TRANSPORTES
             * =========================================================
             */

            Transporte tr1 = new Transporte(
                    "Camión cisterna"
            );

            tr1.setTransportista(tp1);
            tp1.formTrans_transportes(tr1);

            em.persist(tr1);


            Transporte tr2 = new Transporte(
                    "Camión caja"
            );

            tr2.setTransportista(tp2);
            tp2.formTrans_transportes(tr2);

            em.persist(tr2);


            /*
             * =========================================================
             * 6. COMPOSICIONES QUÍMICAS
             * =========================================================
             */

            Composicion_Quimico cq1 = new Composicion_Quimico(
                    "Comp1",
                    10
            );

            cq1.setResiduo(r2);
            cq1.setQuimico(q1);

            r2.formRes_composicion(cq1);
            q1.formQuim_composicion(cq1);

            em.persist(cq1);


            Composicion_Quimico cq2 = new Composicion_Quimico(
                    "Comp2",
                    5
            );

            cq2.setResiduo(r2);
            cq2.setQuimico(q2);

            r2.formRes_composicion(cq2);
            q2.formQuim_composicion(cq2);

            em.persist(cq2);


            /*
             * =========================================================
             * 7. TRASLADOS
             * =========================================================
             */

            Traslado t1 = new Traslado(
                    "EcoPachuca",
                    50,
                    "2026-09-25",
                    "2026-09-26",
                    5000,
                    120
            );

            t1.setEmpresa(e1);
            t1.setResiduo(r1);
            t1.setCentro(ct1);
            t1.setTratamiento(tt1);
            t1.setTransporte(tr1);

            ct1.formCen_traslado(t1);
            tt1.formTrat_traslado(t1);
            tr1.formTrans_traslado(t1);

            em.persist(t1);


            Traslado t2 = new Traslado(
                    "ReciclaHidalgo",
                    100,
                    "2026-09-27",
                    "2026-09-28",
                    8000,
                    200
            );

            t2.setEmpresa(e2);
            t2.setResiduo(r2);
            t2.setCentro(ct2);
            t2.setTratamiento(tt2);
            t2.setTransporte(tr2);

            ct2.formCen_traslado(t2);
            tt2.formTrat_traslado(t2);
            tr2.formTrans_traslado(t2);

            em.persist(t2);


            /*
             * =========================================================
             * 8. GUARDAR TODO
             * =========================================================
             */

            em.getTransaction().commit();

            System.out.println();
            System.out.println("==============================================");
            System.out.println(" BASE DE DATOS CREADA CORRECTAMENTE");
            System.out.println("==============================================");
            System.out.println();
            System.out.println("Se insertaron:");
            System.out.println("- 2 Empresas");
            System.out.println("- 2 Envases");
            System.out.println("- 2 Químicos");
            System.out.println("- 2 Centros de tratamiento");
            System.out.println("- 2 Tipos de tratamiento");
            System.out.println("- 2 Transportistas");
            System.out.println("- 2 Residuos");
            System.out.println("- 2 Transportes");
            System.out.println("- 2 Composiciones químicas");
            System.out.println("- 2 Traslados");
            System.out.println();
            System.out.println("Todas las relaciones fueron creadas.");
            System.out.println("==============================================");

        } catch (Exception ex) {

            /*
             * Si ocurre un error, deshacer la transacción.
             */
            if (em != null
                    && em.getTransaction().isActive()) {

                em.getTransaction().rollback();
            }

            System.err.println();
            System.err.println("==============================================");
            System.err.println(" ERROR AL CREAR LA BASE DE DATOS");
            System.err.println("==============================================");

            ex.printStackTrace();

        } finally {

            /*
             * Cerrar EntityManager.
             */
            if (em != null && em.isOpen()) {
                em.close();
            }

            /*
             * Cerrar EntityManagerFactory.
             */
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }


    /**
     * Elimina la base de datos anterior para que Test.java
     * siempre cree una base limpia.
     */
    private static void eliminarBaseAnterior() {

        /*
         * Obtenemos el directorio de ObjectDB.
         * $objectdb normalmente apunta al directorio de instalación.
         */
        String objectdbHome = System.getProperty("objectdb.home");

        if (objectdbHome == null || objectdbHome.trim().isEmpty()) {
            try {
                File jar = new File(
                        com.objectdb.jpa.EMImpl.class
                                .getProtectionDomain()
                                .getCodeSource()
                                .getLocation()
                                .toURI()
                );

                File directorio = jar;

                // Si apunta al JAR, primero obtenemos su carpeta.
                if (directorio.isFile()) {
                    directorio = directorio.getParentFile();
                }

                // objectdb.jar suele estar en bin/ o lib/.
                if (directorio.getName().equalsIgnoreCase("bin")
                        || directorio.getName().equalsIgnoreCase("lib")
                        || directorio.getName().equalsIgnoreCase("build")) {
                    directorio = directorio.getParentFile();
                }

                objectdbHome = directorio.getAbsolutePath();

            } catch (Exception ex) {
                throw new IllegalStateException(
                        "No se pudo determinar el directorio de ObjectDB.", ex
                );
            }
        }

        File archivoDB = new File(
                objectdbHome,
                "db" + File.separator + "residuosdb.odb"
        );

        /*
         * ObjectDB usa un archivo de recuperación con el mismo nombre
         * y un '$' al final. Si borramos la .odb pero dejamos una .odb$
         * antigua, al crear la nueva base aparece:
         *
         *   Recovery file ... does not match db file (error 145)
         *
         * Como este Test reconstruye la base desde cero, eliminamos
         * primero el archivo de recuperación y después la base anterior.
         */
        File archivoRecovery = new File(archivoDB.getAbsolutePath() + "$");

        eliminarArchivo(archivoRecovery, "archivo de recuperación");
        eliminarArchivo(archivoDB, "base de datos");

        System.out.println(
                "Base anterior limpiada. Se creará una nueva en: "
                + archivoDB.getAbsolutePath()
        );
    }

    /**
     * Elimina un archivo si existe. Si Windows/ObjectDB lo tiene bloqueado,
     * detiene el proceso en lugar de continuar con archivos inconsistentes.
     */
    private static void eliminarArchivo(File archivo, String descripcion) {
        if (!archivo.exists()) {
            return;
        }

        if (!archivo.delete()) {
            throw new IllegalStateException(
                    "No se pudo eliminar el " + descripcion + ":\n"
                    + archivo.getAbsolutePath()
                    + "\nCierra ObjectDB, Database Explorer o cualquier "
                    + "programa que esté usando la base y vuelve a ejecutar."
            );
        }

        System.out.println(
                "Eliminado " + descripcion + ": " + archivo.getAbsolutePath()
        );
    }
}
