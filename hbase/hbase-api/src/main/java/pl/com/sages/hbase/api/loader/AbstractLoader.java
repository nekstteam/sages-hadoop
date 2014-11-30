package pl.com.sages.hbase.api.loader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;

public abstract class AbstractLoader {

    protected void recreateTable(Configuration configuration, String tableName, String familyName) throws IOException {
        HBaseAdmin admin = new HBaseAdmin(configuration);

        boolean exists = admin.tableExists(tableName);
        if (exists) {
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
        }

        // tworzenie tabeli HBase
        HTableDescriptor table = new HTableDescriptor(tableName);
        HColumnDescriptor columnFamily = new HColumnDescriptor(familyName);
        columnFamily.setMaxVersions(1);
        table.addFamily(columnFamily);

        admin.createTable(table);
    }

}
