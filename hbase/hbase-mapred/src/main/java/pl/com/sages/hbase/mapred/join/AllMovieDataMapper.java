package pl.com.sages.hbase.mapred.join;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import pl.com.sages.hbase.api.loader.LoadMovieRatingData;

import java.io.IOException;

public class AllMovieDataMapper extends TableMapper<ImmutableBytesWritable, Put> {

    public void map(ImmutableBytesWritable row, Result value, Context context) throws IOException, InterruptedException {
        context.write(row, resultToPut(row, value));
    }

    private static Put resultToPut(ImmutableBytesWritable key, Result result) throws IOException {
        Put put = new Put(key.get());

        System.out.println("------------------------------");

        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {

            String family = Bytes.toString(CellUtil.cloneFamily(cell));
            System.out.println(family);

            if (family.equals("movies")) {

                String movieId = Bytes.toString(cell.getRow());
                String title = Bytes.toString(CellUtil.cloneValue(cell));
                System.out.println(movieId + "::" + title);

            } else if (family.equals("ratings")){

                String column = Bytes.toString(CellUtil.cloneQualifier(cell));

                int ratingId = Bytes.toInt(cell.getRow());
                if(column.equals(LoadMovieRatingData.MOVIE_ID)){

                    String movieId = Bytes.toString(CellUtil.cloneValue(cell));
                    System.out.println(ratingId + "::" + movieId);

                } else if (column.equals(LoadMovieRatingData.RATING)){

                    Double rating = Bytes.toDouble(CellUtil.cloneValue(cell));
                    System.out.println(ratingId + "::" + rating);

                }

            }

            put.add(cell);
        }

        return put;
    }

}
