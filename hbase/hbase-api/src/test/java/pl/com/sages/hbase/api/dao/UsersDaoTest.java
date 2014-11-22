package pl.com.sages.hbase.api.dao;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTablePool;
import org.junit.Before;
import org.junit.Test;

import static pl.com.sages.hbase.api.HbaseConfigurationFactory.getConfiguration;

public class UsersDaoTest {

    public static final String EMAIL = "jan@kowalski.pl";
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
        User user = new User("Jan", "Kowalski", EMAIL, "k12l3iu12313;k");

        //when
        usersDao.save(user);

        //then
        usersDao.findByEmail(EMAIL);
    }

}