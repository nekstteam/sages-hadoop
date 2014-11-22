package pl.com.sages.hbase.api;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public class HbaseConfigurationFactory {

    public static Configuration getConfiguration() {
        Configuration configuration = HBaseConfiguration.create();

        configuration.set("hbase.zookeeper.quorum", "127.0.0.1");
        configuration.set("hbase.zookeeper.property.clientport", "2181");
        configuration.set("zookeeper.znode.parent", "/hbase-unsecure");

        return configuration;
    }

}
