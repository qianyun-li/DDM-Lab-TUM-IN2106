# Spark & Hadoop K-mer Counting

## Review of SSH

* [Beginners Guide to SSH Tutorial](https://www.youtube.com/watch?v=v45p_kJV9i4)
* [How Secure Shell Works (SSH)](https://www.youtube.com/watch?v=ORcvSkgdA58)

## Partition, Format, Mount a Disk

* [Linux /dev Directory](https://tldp.org/LDP/Linux-Filesystem-Hierarchy/html/dev.html)
* [fdisk command](https://www.geeksforgeeks.org/fdisk-command-in-linux-with-examples/#:~:text=fdisk%20also%20known%20as%20format,using%20the%20dialog%2Ddriven%20interface.)
* [Disk Partitioning](https://en.wikipedia.org/wiki/Disk_partitioning)
* [/etc/fstab](https://www.redhat.com/sysadmin/etc-fstab#:~:text=Your%20Linux%20system's%20filesystem%20table,Consider%20USB%20drives%2C%20for%20example.)
* [How to Partition, Format, Mount a disk on Linux](https://www.tecklyfe.com/how-to-partition-format-and-mount-a-disk-on-ubuntu-20-04/)

## Linux Command: Command in the Background & LPF

* [How to Run Linux Commands in Background](https://linuxize.com/post/how-to-run-linux-commands-in-background/)
  * `command > log 2>&1`
* [Redirect stdout & stderr](https://askubuntu.com/questions/625224/how-to-redirect-stderr-to-a-file)
* [Exiting the shell with background jobs running](https://www.ibm.com/docs/en/zos/2.2.0?topic=shells-exiting-shell-background-jobs-running)
* [nohup command in Linux](https://www.journaldev.com/27875/nohup-command-in-linux)
  * `nohup command arguments`
* [ssh -L Forward Multiple Ports](https://stackoverflow.com/questions/29936948/ssh-l-forward-multiple-ports)

## Cluster

* master Spark `$ ssh -i ~/.ssh/lrz_gp_private -l ubuntu  -L 3000:10.195.5.0:8080 -L 3010:10.195.5.0:18080 10.195.5.0`
  * hadoop `$ ssh -i ~/.ssh/lrz_gp_private -l ubuntu -L 4000:10.195.5.0:50070 -L 4010:10.195.5.0:8088 10.195.5.0 ` 
* worker4 `$ ssh -i ~/.ssh/lrz_gp_private -l ubuntu 10.195.5.73  `
* worker5 `$ ssh -i ~/.ssh/lrz_gp_private -l ubuntu 10.195.4.206`
* worker3 `$ ssh -i ~/.ssh/lrz_gp_private -l ubuntu 10.195.4.67`
* worker1 `$ ssh -i ~/.ssh/lrz_gp_private -l ubuntu 10.195.4.218`
* worker2 `$ ssh -i ~/.ssh/lrz_gp_private -l ubuntu 10.195.4.249`

## Data Processing

* [Multiline fasta to Singline fasta](https://www.biostars.org/p/9262/)
* `$ ps aux`

## Maven

* [Java Package](https://www.google.com.hk/search?q=package+in+java&newwindow=1&safe=strict&ei=n5u_YLXFI5zVmAWcm5K4Bw&oq=package+in+j&gs_lcp=Cgdnd3Mtd2l6EAMYADICCAAyAggAMgIIADICCAAyAggAMgIIADICCAAyAggAMgIIADICCAA6BQgAEJECOgUIABCxAzoLCC4QsQMQxwEQowI6BQguELEDOggILhCxAxCDAToOCC4QsQMQxwEQowIQkwI6AgguOggIABCxAxCDAToLCC4QsQMQgwEQkwI6BAgAEEM6BwgAELEDEEM6CAgAELEDEMkDOggILhDHARCjAjoFCAAQyQNQw8I0WKDqNGCI-TRoAHAAeACAAbcFiAHFJJIBCzAuNC4yLjIuMS40mAEAoAEBqgEHZ3dzLXdpesABAQ&sclient=gws-wiz)
* [Packages in Java](https://www.geeksforgeeks.org/packages-in-java/)
* [Maven Complet Tutorial with Intellij](https://www.youtube.com/watch?v=JhSBS2OpGdU)

## Hadoop

* `$ nohup hadoop jar hadoop-1.0-SNAPSHOT.jar kmer.FindSubstrings /data/linearized_squence.fasta /hadoop_output_3mer 3 > hadoop_java_3mer.log 2>&1 &`

## Spark

* [Apache Hadoop Yarn](https://hadoop.apache.org/docs/current/hadoop-yarn/hadoop-yarn-site/YARN.html)

* [Spark with Yarn](https://sujithjay.com/spark/with-yarn)

* [Monitoring and Instrumentation of Spark](https://spark.apache.org/docs/latest/monitoring.html)

  * `$ mkdir /tmp/spark-events`
  * [Enabling the Spark History service](https://www.ibm.com/docs/en/pasc/1.1.1?topic=ego-enabling-spark-history-service)
  * [Configuring the Spark history server](https://docs.datastax.com/en/dse/5.1/dse-dev/datastax_enterprise/spark/sparkConfiguringfHistoryServer.html)
  * Default port is 18080

* [RDD Programming Guide](https://spark.apache.org/docs/latest/rdd-programming-guide.html#resilient-distributed-datasets-rdds)

  * [sortByKey](https://spark.apache.org/docs/latest/api/python/reference/api/pyspark.RDD.sortByKey.html)

* [Submitting Applications](https://spark.apache.org/docs/latest/submitting-applications.html)

  ```
  # 3-mer
  $ spark-submit --master spark://master:7077 --class kmer_spark.Kmer_count Kmer_spark-1.0-SNAPSHOT.jar 3 /data/linearized_squence.fasta /output_java_spark
  
  # 4-mer
  $ spark-submit --master spark://master:7077 --class kmer_spark.Kmer_count Kmer_spark-1.0-SNAPSHOT.jar 4 /data/linearized_squence.fasta /output_java_spark_4mer
  
  # 5-mer
  $ spark-submit --master spark://master:7077 --class kmer_spark.Kmer_count Kmer_spark-1.0-SNAPSHOT.jar 5 /data/linearized_squence.fasta /output_java_spark_5mer
  
  # 6-mer, nohup and log file in spark_6mer_java.log
  $ nohup spark-submit --master spark://master:7077 --class kmer_spark.Kmer_count Kmer_spark-1.0-SNAPSHOT.jar 6 /data/linearized_squence.fasta /output_java_spark_6mer > spark_6mer_java.log 2>&1 &
  ```



