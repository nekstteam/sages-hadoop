package pl.com.sages.hadoop.mapreduce.invertedindex;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.junit.Test;
import pl.com.sages.hadoop.mapreduce.wordcount.WordCountReducer;
import pl.com.sages.hadoop.mapreduce.wordcount.WordCountRunner;

import static org.junit.Assert.*;

public class InvertedIndexRunnerTest {

    public static final String INPUT_PATH = "/home/sages/Sages/dane/lektury-100/";
    public static final String OUTPUT_PATH = "/tmp/invertedindex-output";

    @Test
    public void shouldGenerateInvertedIndex() throws Exception {
        //given
        FileSystem fs = FileSystem.get(new Configuration());
        fs.delete(new Path(OUTPUT_PATH), true);

        Job job = InvertedIndexRunner.createJob(new Path(INPUT_PATH), new Path(OUTPUT_PATH));

        //when
        job.waitForCompletion(true);

        //then

    }

}