package pl.com.sages.hadoop.mapreduce.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class WordCountRunnerTest {

    public static final String INPUT_PATH = "/home/sages/Sages/dane/lektury-100/";
    public static final String OUTPUT_PATH = "/tmp/wordcount-output";

    @Test
    public void shouldCountWords() throws Exception {
        //given
        FileSystem fs = FileSystem.get(new Configuration());
        fs.delete(new Path(OUTPUT_PATH), true);

        Job job = WordCountRunner.createJob(new Path(INPUT_PATH), new Path(OUTPUT_PATH));

        //when
        job.waitForCompletion(true);

        //then
        assertTrue(fs.exists(new Path(OUTPUT_PATH + "/_SUCCESS")));
        assertTrue(fs.exists(new Path(OUTPUT_PATH + "/part-r-00000")));
    }

}