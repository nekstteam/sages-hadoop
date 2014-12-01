package pl.com.sages.hbase.mapred.filter;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class FilterMapper extends TableMapper<ImmutableBytesWritable, Put> {

    public void map(ImmutableBytesWritable row, Result value, Context context) throws IOException, InterruptedException {
        context.write(row, resultToPut(row, value));
    }

    private static Put resultToPut(ImmutableBytesWritable key, Result result) throws IOException {
        Put put = new Put(key.get());
        for (KeyValue kv : result.raw()) {
//            System.out.println(Bytes.toString(kv.getKey()));
//            System.out.println(kv.getValue());
            put.add(kv);
        }
        return put;
    }

}