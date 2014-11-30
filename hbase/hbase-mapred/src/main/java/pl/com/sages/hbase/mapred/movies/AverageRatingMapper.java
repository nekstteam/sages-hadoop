package pl.com.sages.hbase.mapred.movies;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;

import java.io.IOException;

/**
 * Created by radek on 11/30/14.
 */
public class AverageRatingMapper extends TableMapper<ImmutableBytesWritable, Put> {

    public void map(ImmutableBytesWritable row, Result value, Context context) throws IOException, InterruptedException {
        context.write(row, resultToPut(row, value));
    }

    private static Put resultToPut(ImmutableBytesWritable key, Result result) throws IOException {
        Put put = new Put(key.get());
        for (KeyValue kv : result.raw()) {
            put.add(kv);
        }
        return put;
    }

}
