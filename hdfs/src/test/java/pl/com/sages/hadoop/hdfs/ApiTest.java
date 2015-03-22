package pl.com.sages.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiTest {

    private FileSystem fs;

    @Before
    public void createRemoteFileSystem() throws IOException {
        Configuration conf = new Configuration(false);
        conf.addResource("/etc/hadoop/conf/core-site.xml");
        conf.addResource("/etc/hadoop/conf/hdfs-site.xml");

        conf.set("fs.default.name", "hdfs://sandbox.hortonworks.com:8020");
//        conf.set("fs.default.name","hdfs://0.0.0.0:8020");

        conf.setClass("fs.hdfs.impl", DistributedFileSystem.class, FileSystem.class);

        fs = FileSystem.get(conf);
    }

    @Test
    public void shouldListFiles() throws Exception {
        // given

        // when
        FileStatus[] fileStatuses = fs.listStatus(new Path("/"));

        List<String> files = new ArrayList<>();
        for (FileStatus fileStatuse : fileStatuses) {
            files.add(fileStatuse.getPath().getName());
        }

        // then
        assertThat(files).contains("mapred");
        assertThat(files).contains("mr-history");
        assertThat(files).contains("user");
    }

    @Test
    public void shouldReadFile() throws Exception {
        // given

        // when

        // then

    }
}
