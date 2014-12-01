package org.datanucleus.samples.jdo.tutorial;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;
import pl.com.sages.hbase.api.dao.UsersDao;
import pl.com.sages.hbase.api.loader.TableFactory;
import pl.com.sages.hbase.api.loader.UserDataFactory;
import pl.com.sages.hbase.jdo.datanucleus.Inventory;
import pl.com.sages.hbase.jdo.datanucleus.Product;
import pl.com.sages.hbase.jdo.datanucleus.User;

import javax.jdo.*;
import java.io.IOException;

import static pl.com.sages.hbase.api.conf.HbaseConfigurationFactory.getConfiguration;

public class HbaseDatanucleusJdoTest {

    @Before
    public void before() throws IOException {
        Configuration configuration = getConfiguration();
        TableFactory.recreateTable(configuration, Bytes.toString(UsersDao.TABLE_NAME), Bytes.toString(UsersDao.FAMILY_NAME));
        UserDataFactory.insertTestData();
        TableFactory.recreateTable(configuration, Inventory.INVETORY, Inventory.INVETORY);
        TableFactory.recreateTable(configuration, Product.PRODUCT, Product.PRODUCT);
    }

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

            User user = new User();
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

    @Test
    public void shouldSaveEntity2() throws Exception {
        //given

        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Sages");
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
