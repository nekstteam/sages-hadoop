
# Przygotowanie mapreduce'a (na przykładzie oozie-examples)

cd /usr/hdp/2.2.0.0-2041/oozie/doc/
tar -zxf oozie-examples.tar.gz
vim examples/apps/map-reduce/job.properties

#jobTracker=sandbox:8050
#nameNode=hdfs://sandbox:8020

# jako użytkownik oozie
su oozie
cd /usr/hdp/2.2.0.0-2041/oozie/doc/

# wrzucenie danych na hdfs'a
hadoop fs -rm -f -r -skipTrash examples
hadoop fs -put examples examples

#uruchomienia zadania
oozie job -oozie http://localhost:11000/oozie -config examples/apps/map-reduce/job.properties -run

#sprawdzenie statusu zadania
oozie job -oozie http://localhost:11000/oozie -info 0000002-150802122823913-oozie-oozi-W

# zabicie zadania
oozie job -oozie http://localhost:11000/oozie -kill 0000002-150802084145323-oozie-oozi-W

# lista zadań ooziego
oozie jobs -oozie http://localhost:11000/oozie

# zatrzymanie i uruchomienie ooziego
/usr/hdp/2.2.0.0-2041/oozie/bin/oozie-stop.sh
/usr/hdp/2.2.0.0-2041/oozie/bin/oozie-start.sh