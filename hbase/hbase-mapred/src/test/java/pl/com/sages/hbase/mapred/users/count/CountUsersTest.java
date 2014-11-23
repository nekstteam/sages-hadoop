package pl.com.sages.hbase.mapred.users.count;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.junit.Before;
import org.junit.Test;
import pl.com.sages.hbase.api.dao.User;
import pl.com.sages.hbase.api.dao.UsersDao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.com.sages.hbase.api.HbaseConfigurationFactory.getConfiguration;

public class CountUsersTest {

    private Configuration configuration;
    private UsersDao usersDao;

    @Before
    public void before() {
        configuration = getConfiguration();
        HTablePool pool = new HTablePool(configuration, 10);
        usersDao = new UsersDao(pool);
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
        System.out.println("Done");
    }

    @Test
    public void shouldRunMapReduce() throws Exception {
        //given
        Job job = new Job(configuration, "Count Users");
        job.setJarByClass(CountUsersMapper.class);

        Scan scan = new Scan();
        scan.addColumn(UsersDao.FAMILY_NAME, UsersDao.FORENAME_COL);

        TableMapReduceUtil.initTableMapperJob(
                Bytes.toString(UsersDao.TABLE_NAME),
                scan,
                CountUsersMapper.class,
                ImmutableBytesWritable.class,
                Result.class,
                job);
        job.setOutputFormatClass(NullOutputFormat.class);
        job.setNumReduceTasks(0);

        //when
        boolean succeeded = job.waitForCompletion(true);

        //then
        assertThat(succeeded).isTrue();
        assertThat(job.getCounters().findCounter(CountUsersMapper.Counters.USER_COUNT).getValue()).isGreaterThan(100);
    }

}
