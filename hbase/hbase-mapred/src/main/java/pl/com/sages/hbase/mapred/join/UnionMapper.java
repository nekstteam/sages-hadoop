package pl.com.sages.hbase.mapred.join;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

public class UnionMapper extends TableMapper<Text, DoubleWritable> {

    public static final byte[] CF = "ratings".getBytes();
    public static final byte[] ATTR1 = "movieId".getBytes();
    public static final byte[] ATTR2 = "rating".getBytes();

    private final DoubleWritable ONE = new DoubleWritable(1);
    private Text text = new Text();

    public void map(ImmutableBytesWritable row, Result value, Context context) throws IOException, InterruptedException {

//        System.out.println(value.getValue(CF, ATTR1));
//        System.out.println(value.getValue(CF, ATTR2));

        for (KeyValue kv : value.raw()) {
            System.out.println(Bytes.toString(kv.getKey()));
            System.out.println(kv.getValue());
        }

//        String movieId = new String(value.getValue(CF, ATTR1));
//        text.set(movieId);
//
//        double rating = Bytes.toDouble(value.getValue(CF, ATTR2));
//        ONE.set(rating);

        text.set("test");
        context.write(text, ONE);
    }

}