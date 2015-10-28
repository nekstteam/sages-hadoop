
CREATE EXTERNAL TABLE usershb (
id STRING,
forename STRING,
password STRING,
surname STRING)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ('hbase.columns.mapping' = ':key,users:forename,users:password,users:surname')
TBLPROPERTIES ('hbase.table.name' = 'users');


CREATE TABLE hive4hbase (
id STRING,
forename STRING,
password STRING,
surname STRING)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ('hbase.columns.mapping' = ':key,hive:forename,hive:password,hive:surname')
TBLPROPERTIES ('hbase.table.name' = 'hive');
