package pl.com.sages.hbase.api.dao;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTablePool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    @Test
    public void shouldInsertBulkData() throws Exception {
        //given
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("users/users.csv");

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String delimeter = ";";

        while ((line = br.readLine()) != null) {

            String[] userData = line.split(delimeter);

            User user = new User();
            user.setForename(userData[0]);
            user.setSurname(userData[1]);
            user.setEmail(userData[2]);
            user.setPassword(userData[3]);

            usersDao.save(user);

        }

        br.close();

        //when

        //then

    }

}