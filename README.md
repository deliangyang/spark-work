```bash
bin/spark-submit --jars /tmp/mysql-connector-java-8.0.22.jar --master local[4] /tmp/party-stat.jar

bin/spark-submit --master local[4] /tmp/party-log-last-requestjar

```

```

url	
JDBC database url of the form 'jdbc:subprotocol:subname'

tableName	
the name of the table in the external database

partitionColumn	
the name of a column of integral type that will be used for partitioning

lowerBound	
the minimum value of 'partitionColumn' used to decide partition stride

upperBound	
the maximum value of 'partitionColumn' used to decide partition stride

numPartitions	
the number of partitions, This, along with 'lowerBound' (inclusive), 'upperBound' (exclusive), form partition strides for generated WHERE clause expressions used to split the column 'partitionColumn' evenly. This defaults to SparkContext.defaultParallelism when unset.

predicates	
a list of conditions in the where clause; each one defines one partition
```

```bash
mvn clean scala:compile compile package
```

### Run it
```bash
rm -rf /tmp/4xx && \
 spark-s --jars \
 /mnt/party-stat/target/lib/spark-redis_2.12-2.4.2.jar,/mnt/party-stat/target/lib/jedis-2.8.0.jar,/mnt/party-stat/target/lib/commons-pool2-2.0.jar \
 /mnt/party-stat/target/redis-demo-jar-with-dependencies.jar \
 /tmp/tt.log /tmp/4xx
```

```text
[INFO] --- maven-dependency-plugin:2.8:copy-dependencies (copy-dependencies) @ untitled ---
[INFO] Copying spark-redis_2.12-2.4.2.jar to /mnt/party-stat/target/lib/spark-redis_2.12-2.4.2.jar
[INFO] Copying commons-pool2-2.0.jar to /mnt/party-stat/target/lib/commons-pool2-2.0.jar
[INFO] Copying scala-compiler-2.12.7.jar to /mnt/party-stat/target/lib/scala-compiler-2.12.7.jar
[INFO] Copying scalap-2.12.9.jar to /mnt/party-stat/target/lib/scalap-2.12.9.jar
[INFO] Copying scala-reflect-2.12.7.jar to /mnt/party-stat/target/lib/scala-reflect-2.12.7.jar
[INFO] Copying scala-xml_2.12-1.2.0.jar to /mnt/party-stat/target/lib/scala-xml_2.12-1.2.0.jar
[INFO] Copying jedis-2.8.0.jar to /mnt/party-stat/target/lib/jedis-2.8.0.jar
[INFO]
```