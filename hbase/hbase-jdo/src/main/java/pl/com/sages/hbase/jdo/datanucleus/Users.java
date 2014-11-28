package pl.com.sages.hbase.jdo.datanucleus;

import javax.jdo.annotations.*;

@PersistenceCapable(table = "users", schema = "info", catalog = "info")
@Extensions({
        @Extension(vendorName = "datanucleus", key = "hbase.columnFamily.meta.bloomFilter", value = "ROWKEY"),
        @Extension(vendorName = "datanucleus", key = "hbase.columnFamily.meta.inMemory", value = "true"),
        @Extension(vendorName = "datanucleus", key = "hbase.table.sanity.checks", value = "false"),
})
public class Users {

    @PrimaryKey(column = "info",
            name = "info:id")
    private long id;

    @Column(name = "info:blob")
    private String blob;

    @Column(name = "info:firstName")
    private String firstName;

    @Column(name = "info:lastName")
    private String lastName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBlob() {
        return blob;
    }

    public void setBlob(String blob) {
        this.blob = blob;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}