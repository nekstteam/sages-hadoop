import org.datanucleus.samples.jpa.tutorial.Inventory;
import org.datanucleus.samples.jpa.tutorial.Product;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class HbaseDatanucleusJpaTest {

    @Test
    public void shouldSaveEntity() throws Exception {
        //given

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Tutorial");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Inventory inv = new Inventory("My Inventory");
            Product product = new Product("Sony Discman", "A standard discman from Sony", 49.99);
            inv.getProducts().add(product);
            em.persist(inv);

            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }

            em.close();
        }


        //when

        //then

    }
}
