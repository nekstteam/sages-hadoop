package pl.com.sages.hbase.mapred.join;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import pl.com.sages.hbase.api.loader.LoadMovieData;
import pl.com.sages.hbase.api.loader.LoadMovieRatingData;

import java.io.IOException;

public class AllMovieDataMapper extends TableMapper<ImmutableBytesWritable, Put> {

    public static final String TABLE_NAME = "movies_data";
    public static final String FAMILY_NAME = "movies_data";

    public void map(ImmutableBytesWritable row, Result value, Context context) throws IOException, InterruptedException {
        context.write(row, resultToPut(row, value));
    }

    private static Put resultToPut(ImmutableBytesWritable key, Result result) throws IOException {


        System.out.println("------------------------------");

        String movieId = null;
        String movieTitle = null;
        String movieGenres = null;
        String movieTags = null;
        Double movieRating = null;

        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {

            String family = Bytes.toString(CellUtil.cloneFamily(cell));
            System.out.println(family);

            if (family.equals(LoadMovieData.FAMILY_NAME)) {

                movieId = Bytes.toString(cell.getRow());
                movieTitle = Bytes.toString(CellUtil.cloneValue(cell));
                System.out.println(movieId + "::" + movieTitle);

            } else if (family.equals(LoadMovieRatingData.FAMILY_NAME)) {

                String column = Bytes.toString(CellUtil.cloneQualifier(cell));

                int ratingId = Bytes.toInt(cell.getRow());
                if (column.equals(LoadMovieRatingData.MOVIE_ID)) {

                    movieId = Bytes.toString(CellUtil.cloneValue(cell));
                    System.out.println(ratingId + "::" + movieId);

                } else if (column.equals(LoadMovieRatingData.RATING)) {

                    movieRating = Bytes.toDouble(CellUtil.cloneValue(cell));
                    System.out.println(ratingId + "::" + movieRating);

                }

            }

        }

        Put put = new Put(Bytes.toBytes(movieId));

        if (movieTitle != null) {
            put.add(Bytes.toBytes(FAMILY_NAME), Bytes.toBytes("title"), Bytes.toBytes(movieTitle));
        } else if(movieRating != null){
            put.add(Bytes.toBytes(FAMILY_NAME), Bytes.toBytes("rating"), Bytes.toBytes(movieRating));
        } else {
            throw new RuntimeException("Brak danych!");
        }

        return put;
    }

}
