package pl.com.sages.hbase.mapred.movies;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.junit.Before;
import org.junit.Test;
import pl.com.sages.hbase.mapred.users.count.CountUsersMapper;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class AverageRatingTest {

    public static final String TABLE_NAME = "ratingaverage";
    public static final String FAMILY_NAME = "ratingaverage";

    private Configuration configuration = HBaseConfiguration.create();

    @Before
    public void before() throws IOException {
        HBaseAdmin admin = new HBaseAdmin(configuration);

        boolean exists = admin.tableExists(TABLE_NAME);
        if (exists) {
            admin.disableTable(TABLE_NAME);
            admin.deleteTable(TABLE_NAME);
        }

        // tworzenie tabeli HBase
        HTableDescriptor table = new HTableDescriptor(TABLE_NAME);
        HColumnDescriptor columnFamily = new HColumnDescriptor(FAMILY_NAME);
        columnFamily.setMaxVersions(1);
        table.addFamily(columnFamily);

        admin.createTable(table);
    }

    @Test
    public void shouldRunMapReduce() throws Exception {
        //given
        Job job = new Job(configuration, "Average Rating");
        job.setJarByClass(MyMapper.class);

        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes("ratings"));
//        scan.addColumn(, UsersDao.FORENAME_COL);

        TableMapReduceUtil.initTableMapperJob(
                "ratings",
                scan,
                MyMapper.class,
                Text.class,
                DoubleWritable.class,
                job);
        TableMapReduceUtil.initTableReducerJob(
                "ratingaverage",
                MyTableReducer.class,
                job);
//        job.setNumReduceTasks(0);

        //when
        boolean succeeded = job.waitForCompletion(true);

        //then
        assertThat(succeeded).isTrue();
//        assertThat(job.getCounters().findCounter(CountUsersMapper.Counters.USER_COUNT).getValue()).isGreaterThan(100);
    }

}