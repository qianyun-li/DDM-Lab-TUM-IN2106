# 1 Background Knowledge

1. [Batch vs. Real Processing](https://data-flair.training/blogs/batch-processing-vs-real-time-processing/#:~:text=Real%2DTime%20Processing%20involves%20continuous,of%20Sale%20(POS)%20Systems.)
2. [In-memory Computing](https://www.gridgain.com/resources/blog/in-memory-computing-in-plain-english#:~:text=In%2Dmemory%20computing%20means%20using,connected%E2%80%9D%20RAM%20across%20multiple%20computers.)
3. [Hadoop vs. Spark](https://www.youtube.com/watch?v=2PVzOHA3ktE)
4. [Spark Cluster Mode Overview](https://spark.apache.org/docs/latest/cluster-overview.html) (Ipt)
5. [Spark Overview](https://spark.apache.org/docs/latest/index.html) (Ipt)
6. [SSH Server: Local Port Forwarding & Remote Port Forwarding](https://www.youtube.com/watch?v=N8f5zv9UUMI)

# 2 Tutorial

## Articles 

* [Spark Installation](https://www.tutorialkart.com/apache-spark/install-latest-apache-spark-on-ubuntu-16/)
* [Apache Spark Cluster Setup](https://www.tutorialkart.com/apache-spark/how-to-setup-an-apache-spark-cluster/)

# 3 Implementation

## Environment Setup

1. Basic: 1 master and 1 slave

2. SSH Connection to Master and slave

   LPF `$ ssh -i ~/.ssh/lrz_private -L 5000:10.195.4.230:8080 ubuntu@10.195.4.230 ` 

   Slave1 `$ ssh -i ~/.ssh/lrz_private -l ubuntu 10.195.5.118`

3. Java Installation (Refer to week1, already installed in machines)

4. Create User `Spark` (Refer to week1)

5. Mapping the Nodes  (Refer to week1)

6. Configuring Key-based Login

7. Spark Download in the [website]([http://spark.apache.org/downloads.html) and Installation

   `$ wget https://mirror-hk.koddos.net/apache/spark/spark-3.1.1/spark-3.1.1-bin-hadoop2.7.tgz`

   `$ tar xzvf spark-3.1.1-bin-hadoop2.7.tgz`

   Move spark to `/usr/lib` via `$ sudo mv spark-3.1.1-bin-hadoop2.7 /usr/lib/`

   Create soft link `$ cd /usr/lib` & `$ sudo ln -s spark-3.1.1-bin-hadoop2.7 spark ` 

    `$ vi ~/.bashrc` & add the following                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      

   ```
   export SPARK_HOME=/usr/lib/spark
   export PATH=$PATH:$SPARK_HOME/bin
   ```

   `$ source ~/.bashrc`

   Verify via`spark-shell` 

## Cluster Setup

### Master Node Setup

​		  `$ cd $SPARK_HOME/conf/`

1. Copy the template and Edit `spark-env.sh`

   `cp spark-env.sh.template spark-env.sh`

   `vi spark-env.sh ` and Add

   ​	`SPARK_MASTER_HOST='192.168.2.117'` 

2. Start Spark as master

   `$ cd $SPARK_HOME/sbin`

   `$ ./start-master.sh`

4. Verify the log file (Specified IP & port of Spark and Web UI)

   `$ cat /usr/lib/spark/logs/spark-spark-org.apache.spark.deploy.master.Master-1-master.out`

### Slave Node Setup

​		  `$ cd $SPARK_HOME/conf/`	

1. Copy the template and Edit `spark-env.sh`

   `cp spark-env.sh.template spark-env.sh`

   `vi spark-env.sh ` and Add

   ​	`SPARK_MASTER_HOST='192.168.2.117'` 

2. Start Spark as slave

   `$ cd $SPARK_HOME/sbin`

   `$ ./start-slave.sh spark://192.168.2.117:7077`

4. Verify the log file (Worker node sucessfully registered with specified master ip)

   `cat /usr/lib/spark/logs/spark-spark-org.apache.spark.deploy.worker.Worker-1-slave1.out`

### Master WEB UI

* Access via `http://<your.master.ip.address>:<web-ui-port-number>/`
* **Local Port Forwarding**



​	