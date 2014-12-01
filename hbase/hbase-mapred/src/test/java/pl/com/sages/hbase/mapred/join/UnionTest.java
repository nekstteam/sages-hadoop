package pl.com.sages.hbase.mapred.join;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.junit.Before;
import pl.com.sages.hbase.mapred.file.RatingExportReducer;
import pl.com.sages.hbase.mapred.filter.FilterMapper;
import pl.com.sages.hbase.mapred.movies.AverageRatingMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UnionTest {

    private Configuration configuration = HBaseConfiguration.create();

    @Before
    public void before() throws IOException {
//        TableFactory.recreateTable(configuration, TABLE_NAME, FAMILY_NAME);
    }

    @org.junit.Test
    public void shouldJoinTables() throws Exception {
        //given

        Job job = new Job(configuration, "Joins");
        job.setJarByClass(AverageRatingMapper.class);

        List scans = new ArrayList();

        Scan scan1 = new Scan();
        scan1.setAttribute(Scan.SCAN_ATTRIBUTES_TABLE_NAME, Bytes.toBytes("movies"));
        scans.add(scan1);

        Scan scan2 = new Scan();
        scan2.setAttribute(Scan.SCAN_ATTRIBUTES_TABLE_NAME, Bytes.toBytes("ratings"));
        scans.add(scan2);

        TableMapReduceUtil.initTableMapperJob(scans,
                FilterMapper.class,
                null,
                null,
                job);
//        job.setReducerClass(RatingExportReducer.class);
//        job.setNumReduceTasks(0);
//        FileOutputFormat.setOutputPath(job, new Path("/tmp/mr/mySummaryFile_union_" + System.currentTimeMillis()));
        TableMapReduceUtil.initTableReducerJob(
                "join_movies",
                null,
                job);
        job.setNumReduceTasks(0);

        //when
        boolean succeeded = job.waitForCompletion(true);

        //then
        assertThat(succeeded).isTrue();
    }

}
