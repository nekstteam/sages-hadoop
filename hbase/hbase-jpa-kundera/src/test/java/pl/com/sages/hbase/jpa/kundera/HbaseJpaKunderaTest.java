package pl.com.sages.hbase.jpa.kundera;

import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HbaseJpaKunderaTest {

    @Test
    public void shouldSaveEntity() throws Exception {
        //given

        User user = new User();
        user.setUserId("0003");
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setCity("London");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sages");
        EntityManager em = emf.createEntityManager();

        //when
        em.persist(user);
        em.close();
        emf.close();

        //then

    }

}
