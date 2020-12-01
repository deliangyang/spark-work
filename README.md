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