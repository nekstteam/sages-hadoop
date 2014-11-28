package org.datanucleus.samples.jdo.tutorial;

import org.junit.Test;

import javax.jdo.*;

public class HbaseDatanucleusJdoTest {

    @Test
    public void shouldSaveEntity() throws Exception {
        //given

        JDOEnhancer enhancer = JDOHelper.getEnhancer();
        enhancer.setVerbose(true);
        enhancer.addPersistenceUnit("Tutorial");
        enhancer.enhance();

        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Tutorial");
        PersistenceManager pm = pmf.getPersistenceManager();

        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();

            Users user = new Users();
            user.setBlob("kwjeow");
            user.setFirstName("Jan");
            user.setLastName("Kowalski");
            user.setId(System.currentTimeMillis());

            pm.makePersistent(user);
            tx.commit();

        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }


        //when

        //then

    }

//    @Test
    public void shouldSaveEntity2() throws Exception {
        //given

        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Tutorial");
        PersistenceManager pm = pmf.getPersistenceManager();

        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Inventory inv = new Inventory("My Inventory");
            Product product = new Product("Sony Discman", "A standard discman from Sony", 49.99);
            inv.getProducts().add(product);
            pm.makePersistent(inv);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }


        //when

        //then

    }
}
