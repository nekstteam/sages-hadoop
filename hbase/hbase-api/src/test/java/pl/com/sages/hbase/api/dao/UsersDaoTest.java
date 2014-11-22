package pl.com.sages.hbase.api.dao;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTablePool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.com.sages.hbase.api.HbaseConfigurationFactory.getConfiguration;

public class UsersDaoTest {

    public static final String EMAIL = "jan@kowalski.pl";
    public static final String FORENAME = "Jan";
    public static final String SURNAME = "Kowalski";
    public static final String PASSWORD = "k12l3iu12313;k";
    private UsersDao usersDao;

    @Before
    public void before() {
        Configuration configuration = getConfiguration();
        HTablePool pool = new HTablePool(configuration, 10);
        usersDao = new UsersDao(pool);
    }

    @Test
    public void shouldSaveUser() throws Exception {
        //given
        User user = new User(FORENAME, SURNAME, EMAIL, PASSWORD);

        //when
        usersDao.save(user);

        //then
        User findedUser = usersDao.findByEmail(EMAIL);
        assertThat(findedUser).isNotNull();
        assertThat(findedUser.getEmail()).isEqualTo(EMAIL);
        assertThat(findedUser.getForename()).isEqualTo(FORENAME);
        assertThat(findedUser.getSurname()).isEqualTo(SURNAME);
        assertThat(findedUser.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    public void shouldFindAllUser() throws Exception {
        //given
        usersDao.save(FORENAME,SURNAME,"1",PASSWORD);
        usersDao.save(FORENAME,SURNAME,"2",PASSWORD);
        usersDao.save(FORENAME,SURNAME,"3",PASSWORD);

        //when
        List<User> users = usersDao.findAll();

        //then
        assertThat(users).isNotNull();
        assertThat(users.size()).isGreaterThan(3);
    }

}