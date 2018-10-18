
# spark-glusterfs-example

This is a simple example of [Apache Spark](https://spark.apache.org/) working with [Gluster](https://www.gluster.org/), using [glusterfs-hadoop](https://github.com/gluster/glusterfs-hadoop).

## Build

To build the project, just run:

```
./mvnw package
```

The application jar will be written to `target/spark-gluster-example-<version>.jar`.

## Run

A working Gluster cluster is required. If you are looking for a simple way to test locally, we recommend using [carmstrong/multinode-glusterfs-vagrant](https://github.com/carmstrong/multinode-glusterfs-vagrant).

For this example, we will assume that:
* `SPARK_HOME` environment variable contains the path to an Apache Spark distribution.
* `HADOOP_CONF_DIR` points to a directory with a Hadoop `core-site.xml`, containing some Gluster configuration. In [`conf/core`](https://github.com/smola/spark-glusterfs-example/blob/master/conf/core-site.xml) you will find a minimal working example, assuming an existing Gluster volume named `gv0` and mounted in `/mnt/gv0`.

Now you can run:

```
$SPARK_HOME/bin/spark-submit \
    --master 'local[2]' \
    target/spark-gluster-example-0.1.0-SNAPSHOT.jar
```

This will generate numbers from 1 to 100000 and write them to Gluster in Parquet format. Then it will read them back and compare to the original. If the result is correct, it will print `OK` and exit with status 0, otherwise, it will print `KO` and exit with status 1.

## License

This example is released under the terms of the [Apache 2 License](LICENSE).
