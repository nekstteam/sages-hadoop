package pl.com.sages.hbase.phoenix;

import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Ignore
public class PhoenixSqlTest {

    @Test
    public void shouldConnectToHBase() throws Exception {
        // given
//        Connection conn = DriverManager.getConnection("jdbc:phoenix:localhost:3333");
        Connection conn = DriverManager.getConnection("jdbc:phoenix:sandbox.hortonworks.com:2181");

        // when
        Statement statement = conn.createStatement();
        statement.executeQuery("CREATE TABLE IF NOT EXISTS us_population (  state CHAR(2) NOT NULL,  city VARCHAR NOT NULL,  population BIGINT  CONSTRAINT my_pk PRIMARY KEY (state, city));");


        // then

    }

}
