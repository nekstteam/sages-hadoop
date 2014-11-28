package org.datanucleus.samples.jdo.tutorial;

import org.junit.Test;
import pl.com.sages.hbase.jdo.datanucleus.Inventory;
import pl.com.sages.hbase.jdo.datanucleus.Product;
import pl.com.sages.hbase.jdo.datanucleus.Users;

import javax.jdo.*;

public class HbaseDatanucleusJdoTest {

    @Test
    public void shouldSaveEntity() throws Exception {
        //given

        JDOEnhancer enhancer = JDOHelper.getEnhancer();
        enhancer.setVerbose(true);
        enhancer.addPersistenceUnit("Sages");
        enhancer.enhance();

        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Sages");
        PersistenceManager pm = pmf.getPersistenceManager();

        Transaction tx = pm.currentTransaction();

        //when
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
