package pl.com.sages.hbase.jdo.datanucleus;

import javax.jdo.annotations.*;

//@PersistenceCapable(schema = "users", catalog = "users", table = "users")
@PersistenceCapable(table = "test", catalog = "test", schema = "test")
@Extensions({
        @Extension(vendorName = "datanucleus", key = "hbase.columnFamily.meta.bloomFilter", value = "ROWKEY"),
        @Extension(vendorName = "datanucleus", key = "hbase.columnFamily.meta.inMemory", value = "true")
})
public class Users {

    @PrimaryKey
    private long id;

    // column family data, name of attribute blob 
    @Column(name = "test:blob")
    private String blob;

    // column family meta, name of attribute firstName 
    @Column(name = "test:firstName")
    private String firstName;

    // column family meta, name of attribute firstName 
    @Column(name = "test:lastName")
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