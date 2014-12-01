package pl.com.sages.hbase.jpa.kundera;

import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

public class HbaseJpaKunderaTest {

    @Test
    public void shouldSaveEntity() throws Exception {
        //given

        User user = new User();
        user.setUserId("0001");
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

    @Test
    public void shouldFindEntity() throws Exception {
        //given

        User user = new User();
        user.setUserId("0003");
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setCity("London");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sages");
        EntityManager em = emf.createEntityManager();

        //when
        em.persist(user);
        User findedUser = em.find(User.class, "0003");
        em.close();
        emf.close();

        //then
        assertThat(findedUser).isNotNull();
        assertThat(findedUser.getFirstName()).isEqualTo("Jan");
        assertThat(findedUser.getLastName()).isEqualTo("Kowalski");
    }

}
