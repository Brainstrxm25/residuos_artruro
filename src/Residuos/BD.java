package Residuos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/** Utilidad para crear conexiones JPA/ObjectDB usando la misma ruta de base de datos. */
public final class BD {
    public static final String DB = opCRUD.DB;

    private BD() {}

    public static EntityManagerFactory factory() {
        return Persistence.createEntityManagerFactory(DB);
    }

    public static EntityManager entityManager(EntityManagerFactory emf) {
        return emf.createEntityManager();
    }
}
