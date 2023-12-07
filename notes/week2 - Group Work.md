#  New Slave Node `spark-worker2` Added

## 1 New Node Added

### Connection & Configuration

* master `$ ssh -i ~/.ssh/lrz_gp_private -L 2000:10.195.4.47:8080 ubuntu@10.195.4.47 ` (Local Port Forwarding: Access via `localhost:2000`)
* worker `$ ssh -i ~/.ssh/lrz_gp_private -l ubuntu 10.195.4.67   `
* worker2 ` $ ssh -i ~/.ssh/lrz_gp_private -l ubuntu 10.195.5.160  `` 
* `$ ./start-slave.sh spark://192.168.2.85:7077`

### Debugging

* **[Source Command not found](https://askubuntu.com/questions/213599/source-command-not-found)**
* **Spark Version** of Master and Workers should be **the same**
* **[Is HDFS necessary for Spark workloads?](https://stackoverflow.com/questions/32669187/is-hdfs-necessary-for-spark-workloads)**
* **[Spark Hadoop Compatibility](https://data-flair.training/blogs/apache-spark-hadoop-compatibility/)**

## RDD API Example

### Word Count

* Create the text file
* `spark-shell`
* `val textFile = sc.textFile("wc_input.txt"`
* `val counts = textFile.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey(_ + _)`
* `counts.saveAsTextFile("wc_output")`

### Pi

```
val count = sc.parallelize(1 to 9999999).filter { _ =>
  val x = math.random
  val y = math.random
  x*x + y*y < 1
}.count()
println(s"Pi is roughly ${4.0 * count / 9999999}")
```

## DataFrame API

* Create the text file
* `spark-shell`

```
val textFile = sc.textFile("ts_input.txt")

// Creates a DataFrame having a single column named "line"
val df = textFile.toDF("line")
val errors = df.filter(col("line").like("%ERROR%"))
// Counts all the errors
errors.count()
// Counts errors mentioning MySQL
errors.filter(col("line").like("%MySQL%")).count()
// Fetches the MySQL errors as an array of strings
errors.filter(col("line").like("%MySQL%")).collect()
```





​		



