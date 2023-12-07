# 1 Background Knowledge 

## Articles

* [Ansible Formal Documentation](https://docs.ansible.com/ansible/latest/user_guide/quickstart.html)
* [Set up a Hadoop Cluster Using Ansible](https://medium.com/analytics-vidhya/set-up-a-hadoop-cluster-using-ansible-b218bf4ce602)

## Videos

* **[Getting started with Ansible](https://www.youtube.com/watch?v=-Q4T9wLsvOQ&list=RDCMUCxQKHvKbmSzGMvUrVtJYnUA&index=1)**

# 2 Implementation

* Connection

  * Master: `$ ssh -i ~/.ssh/lrz_gp_private -l ubuntu  10.195.4.47   `
    * NameNode UI `$ ssh -i ~/.ssh/lrz_gp_private -L 3000:10.195.4.47:50070 ubuntu@10.195.4.47`
    * MapReduce UI `$ ssh -i ~/.ssh/lrz_gp_private -L 3001:10.195.4.47:19888 ubuntu@10.195.4.47`
    * Yarn UI `$ ssh -i ~/.ssh/lrz_gp_private -L 3002:10.195.4.47:8088 ubuntu@10.195.4.47 `
  * Worker1:`$ ssh -i ~/.ssh/lrz_gp_private -l ubuntu  10.195.4.67`
  * Worker2:`$ ssh -i ~/.ssh/lrz_gp_private -l ubuntu  10.195.5.73`
  * Worker3:`$ ssh -i ~/.ssh/lrz_gp_private -l ubuntu  10.195.4.218`
  * Worker4:`$ ssh -i ~/.ssh/lrz_gp_private -l ubuntu  10.195.6.130`
  * Worker5:`$ ssh -i ~/.ssh/lrz_gp_private -l ubuntu  10.195.4.206`

* [GitLab Repo](https://gitlab.lrz.de/ddm_lab_group2)

# 4 Wordcount

## Kaggle Data Downloading

* [Kaggle API](https://github.com/Kaggle/kaggle-api)

  `$ mkdir ~/.kaggle`

  `$ mv kaggle.json ~/.kaggle`

  `$ ln -s ~/.local/bin/kaggle /usr/local/bin`

* [Kaggle Data Subset Downloading](https://medium.com/@ankushchoubey/how-to-download-dataset-from-kaggle-7f700d7f9198)

  `$ kaggle datasets download -f link_annotated_text.jsonl kenshoresearch/kensho-derived-wikimedia-data`
  
* Data Mounting

  `sudo mkdir /mnt/data`

  `sudo mount -t auto -v /dev/vdb1 /mnt/data`

   `$ lsblk`

## Hadoop

### Background Knowledge

* [Linux 挂载](https://www.cnblogs.com/cangqinglang/p/12170828.html)

### Debugging

* [java.lang.IllegalArgumentException: Invalid URI for NameNode address](https://stackoverflow.com/questions/23646308/invalid-uri-for-namenode-address)

* [Hadoop job not showing in Resource Manager](https://stackoverflow.com/questions/21345022/hadoop-is-not-showing-my-job-in-the-job-tracker-even-though-it-is-running)

* [Error in shuffle in fetcher](https://www.huaweicloud.com/articles/7ca492e37a43c9e85a9a6a4dd5d77c6d.html)

* [Imcompatible Version Ids](https://stackoverflow.com/questions/35108445/java-io-ioexception-incompatible-clusterids)

  `$ hdfs namenode -format -clusterid <datanode_cluster_id>`

* `$ yarn logs -applicationId <application ID> `

````
$ ~/hadoop-2.7.3/sbin
$ stop-yarn.sh
$ start-yarn.sh
````

* [DiskChecker$DiskErrorException](https://www.cnblogs.com/codeOfLife/p/5940633.html) & [hadoop.tmp.dir](https://www.jianshu.com/p/e50307229c68)

* [hdfs dfs -setrep Command Not Found](https://stackoverflow.com/questions/24314257/hadoop-fs-put-unknown-command)

* [Change Replication Factor of Existing File](https://stackoverflow.com/questions/49384962/changing-replication-of-existing-files-in-hdfs)

  `$ hdfs dfs -setrep -R 1 <path_of_file>`

* [Hadoop Balancer](https://hadoop.apache.org/docs/r2.7.2/hadoop-project-dist/hadoop-hdfs/HDFSCommands.html)

  `$ hdfs balancer -policy datanode`

### Java

* [Word Count Program With MapReduce and Java](https://dzone.com/articles/word-count-hello-word-program-in-mapreduce)

* [Using Intellij for your Hadoop Application](https://tokluo.wordpress.com/2016/01/31/using-intellij-to-write-your-application/)

* [UnsupportedClassVersionError](https://www.baeldung.com/java-lang-unsupportedclassversion)

* [Remove pucs/digits from String](https://stackoverflow.com/questions/23332146/remove-punctuation-preserve-letters-and-white-space-java-regex)

* [Java Sort Map by Values](https://howtodoinjava.com/java/sort/java-sort-map-by-values/)

* ```
  $ hadoop jar Hadoop-Java-WordCount.jar Hadoop-Java-WordCount.WordCountHadoop /wikimedia/wikimedia_txt /wikimedia/hadoop-wc-java
  ```

### Python

* [Writing An Hadoop MapReduce Program In Python](https://www.michael-noll.com/tutorials/writing-an-hadoop-mapreduce-program-in-python/)
* [Python Json](https://www.programiz.com/python-programming/json)
* [Efficient Algorithm to check Prime](https://www.geeksforgeeks.org/analysis-different-methods-find-prime-number-python/)
* [Working with Large Data Sets using Pandas and JSON in Python](https://www.dataquest.io/blog/python-json-tutorial/)
* [Remove puncs/digits from String](https://stackoverflow.com/questions/265960/best-way-to-strip-punctuation-from-a-string)
* `$ chmod +x xxxx.py`

```
$ hadoop jar ~/hadoop-2.7.3/share/hadoop/tools/lib/hadoop-streaming-2.7.3.jar -jt master:8050 -file ./hadoop-mapper-wc.py -mapper ./hadoop-mapper-wc.py -file ./hadoop-reducer-wc.py -reducer ./hadoop-reducer-wc.py -input /wikimedia/wikimedia_txt -output /wikimedia/hadoop-wc-py

$ hdfs dfs -rm -r /wikimedia/hadoop-wc-py
```

```
hadoop jar ~/hadoop-2.7.3/share/hadoop/tools/lib/hadoop-streaming-2.7.3.jar -jt master:8050 -file ./hadoop-mapper-prime.py -mapper ./hadoop-mapper-prime.py -file ./hadoop-reducer-prime.py -reducer ./hadoop-reducer-prime.py -input /primes/prime.txt -output /primes/hadoop-prime-py

hdfs dfs -rm -r /primes/hadoop-prime-py
```

## Spark

* [RDD Programming Guide](https://spark.apache.org/docs/latest/rdd-programming-guide.html#resilient-distributed-datasets-rdds)
* [Submitting Applications](https://spark.apache.org/docs/latest/submitting-applications.html)





