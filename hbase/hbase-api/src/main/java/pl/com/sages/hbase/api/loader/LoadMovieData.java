package pl.com.sages.hbase.api.loader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by radek on 11/30/14.
 */
public class LoadMovieData {

    public static final String TABLE_NAME = "movies";
    public static final String FAMILY_NAME = "movies";

    public static void main(String[] args) throws IOException {

        Configuration configuration = HBaseConfiguration.create();

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

        // wrzucanie danych do HBase
        HTableInterface movies = new HTable(configuration, TABLE_NAME);

        BufferedReader br = new BufferedReader(new FileReader(new File("/home/radek/Sages/ml-10M100K/movies.dat")));
        String line = "";
        String delimeter = "::";

        while ((line = br.readLine()) != null) {

            String[] movieData = line.split(delimeter);
            String id = movieData[0];
            String title = movieData[1];
            String genres = movieData[2];
            System.out.println(id + "::" + title + "::" + genres);

            Put put = new Put(Bytes.toBytes(id));
            put.add(Bytes.toBytes(FAMILY_NAME), Bytes.toBytes("title"), Bytes.toBytes(title));
            put.add(Bytes.toBytes(FAMILY_NAME), Bytes.toBytes("genres"), Bytes.toBytes(genres));

            movies.put(put);
        }

        br.close();

    }

}
