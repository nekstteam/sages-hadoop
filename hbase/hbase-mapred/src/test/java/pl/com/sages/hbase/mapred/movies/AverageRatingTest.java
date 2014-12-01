package pl.com.sages.hbase.mapred.movies;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.junit.Before;
import org.junit.Test;
import pl.com.sages.hbase.api.model.User;

import java.io.IOException;
import java.util.ArrayList;

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

        HTableInterface ratingaverage = new HTable(configuration, TABLE_NAME);

        scan = new Scan();
        scan.addFamily(Bytes.toBytes(FAMILY_NAME));

        ResultScanner results = ratingaverage.getScanner(scan);
        ArrayList<User> list = new ArrayList<>();
        for (Result result : results) {
            byte[] id = result.getRow();
            byte[] average = result.getValue(Bytes.toBytes(FAMILY_NAME), Bytes.toBytes("average"));
            System.out.println(Bytes.toString(id) + " " + Bytes.toDouble(average));
        }

        ratingaverage.close();
    }

}