/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Residuos;

import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

public class Activi {

    public static void main(String[] args) {
        com.objectdb.jdo.PMF pmf = new com.objectdb.jdo.PMF();
        String version = pmf.getProperties().getProperty("VersionNumber");
        pmf.close();
        System.out.println("ODB VERSION = " + version);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/residuosdb.odb");
        boolean isRegistered = emf.createEntityManager().createQuery("objectdb activation", boolean.class).getSingleResult();
        System.out.println("ACTIVACIÓN = " + isRegistered);
    }
}


//CHECAR que sea el el archivo CONFIG del JAR agregado al proyecto !!!
