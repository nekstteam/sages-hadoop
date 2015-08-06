package pl.com.sages.hbase.api;

import junit.framework.Assert;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static pl.com.sages.hbase.api.conf.HbaseConfigurationFactory.getConfiguration;

public class HbaseApiTest {

    public static final String TABLE_NAME = "test_" + System.currentTimeMillis();
    public static final String FAMILY_NAME = "info";

    private HBaseAdmin admin;
    private Configuration configuration;

    @Before
    public void createTable() throws Exception {
        configuration = getConfiguration();
        admin = new HBaseAdmin(configuration);

        HTableDescriptor table = new HTableDescriptor(TABLE_NAME);
        HColumnDescriptor columnFamily = new HColumnDescriptor(FAMILY_NAME);
        columnFamily.setMaxVersions(1);
        table.addFamily(columnFamily);

        admin.createTable(table);
    }

    @After
    public void deleteTable() throws Exception {
        admin.disableTable(TABLE_NAME);
        admin.deleteTable(TABLE_NAME);
    }

    @Test
    public void shouldPutDataOnHbase() throws Exception {
        //given
        String id = "id";
        String cell = "cell";
        String text = "nasza testowa wartość";

        HTableInterface users = new HTable(configuration, TABLE_NAME);
        Put put = new Put(Bytes.toBytes(id));
        put.add(Bytes.toBytes(FAMILY_NAME),
                Bytes.toBytes(cell),
                Bytes.toBytes(text));
        users.put(put);

        //when
        Get get = new Get(Bytes.toBytes(id));
        get.addColumn(Bytes.toBytes(FAMILY_NAME), Bytes.toBytes(cell));

        Result result = users.get(get);
        byte[] value = result.getValue(Bytes.toBytes(FAMILY_NAME), Bytes.toBytes(cell));

        //then
        assertEquals(text, new String(value));
    }

}
