package io.mola.spark.glusterfs.example

import org.apache.spark.sql.{Dataset, SparkSession}

case class MyRow(num: Int)

object App {
  val Name = "spark-glusterfs-example"
  val DefaultGlusterPath = "glusterfs://"

  def main(args: Array[String]) {
    val spark = SparkSession.builder.appName(Name).getOrCreate()
    import spark.implicits._

    val ds = (0 to 100000)
      .map(MyRow.apply)
      .toDS()

    val path = s"$DefaultGlusterPath/$Name/${System.currentTimeMillis}/example.parquet"
    ds.write.parquet(path)

    val areEqual = collectSorted(spark.read.parquet(path).as[MyRow]) == collectSorted(ds)
    if (areEqual) {
      println("OK")
    } else {
      println("KO")
    }

    spark.stop()

    if (!areEqual) {
      sys.exit(1)
    }
  }

  private[this] def collectSorted(ds: Dataset[MyRow]): Seq[MyRow] = ds.collect().sortBy(_.num)

}
