# 1 Background Knowledge

1. [Setting up an SSH Client](https://www.ibm.com/docs/en/sanvolumecontroller/8.1.3?topic=interface-secure-shell): Authentication via Private Key
2. Hadoop Introduction

   * [in 10 minutes](https://www.guru99.com/learn-hadoop-in-10-minutes.html): Architecture; Rack&Node

   * [Introduction](https://www.edureka.co/blog/hadoop-tutorial/)
3. [Linux /home directory](https://www.linux.com/training-tutorials/linux-directory-structure-home-and-root-folders/)
4. [the use of /etc/hosts](https://askubuntu.com/questions/183176/what-is-the-use-of-etc-hosts)
5. Hadoop Configuration
   * [hadoop cluster setup](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/ClusterSetup.html)
   * [hadoop搭建四个配置文件的简单介绍](https://blog.csdn.net/Mr_LeeHY/article/details/77049800)
6. hadoop.tmp.dir
   * [stackoverflow](https://stackoverflow.com/questions/2354525/what-should-be-hadoop-tmp-dir)
   * [关于 hadoop.tmp.dir 理解及配置](https://www.jianshu.com/p/e50307229c68)
7. [Diff between hadoop.tmp.dir and mapred.tmp.dir](https://stackoverflow.com/questions/37909260/what-is-the-difference-between-hadoop-tmp-dir-and-mapred-temp-dir-and-mapreduce)
8. [What is Apache Hive? : Understanding Hive](https://www.youtube.com/watch?v=cMziv1iYt28)
9. [Theory: Map Reduce in Hadoop](https://www.geeksforgeeks.org/map-reduce-in-hadoop/) (NOT very good but intuitive)
10. [MapReduce - Combiners ](https://www.tutorialspoint.com/map_reduce/map_reduce_combiners.htm)(Not very accurate but intuitive)
    * it must implement the Reducer interface’s reduce() method
    * Hence same output key-value types as the Reducer
11. [Shuffle And Sort Phases in Hadoop MapReduce](https://www.netjstech.com/2018/07/shuffle-and-sort-phases-hadoop-mapreduce.html) (**! Accurate, Detailed & Illustrative**)
12. [HDFS Architecture](https://hadoop.apache.org/docs/r3.0.1/hadoop-project-dist/hadoop-hdfs/HdfsDesign.html) (**Official & Detailed**)
    * Files in HDFS are **write-once** (except for appends and truncates) and have strictly **one writer** at any time.
    * <u>Appending</u> the content to the <u>end of the files</u> is supported but <u>cannot be updated at arbitrary point</u>
    * Simplifies data coherency issues and enables high throughput data access
    * A MapReduce application or a web crawler application fits perfectly with this model
13. [Hadoop Performance Model](https://www.dropbox.com/s/05hgzm28933klj2/hadoop-models.pdf?dl=0)
14. [[Number of MapReduce tasks](https://stackoverflow.com/questions/42424642/number-of-mapreduce-tasks)](https://stackoverflow.com/questions/42424642/number-of-mapreduce-tasks)
15. Kill Job/Application
    * `$ hadoop job -kill <job-id>` 
    * `$ yarn application -kill <application-id>`
16. [Hadoop Intellij Maven](https://medium.com/analytics-vidhya/testing-your-hadoop-program-with-maven-on-intellij-42d534db7974)

# 2 Implementation Tutorials

## Video 

1. [Hadoop Multi Node Cluster Setup | Edureka](https://www.youtube.com/watch?v=-YEcJquYsFo&t=3980s)
2. [VI Editor](https://www.youtube.com/watch?v=pU2k776i2Zw&t=7s)

## Articles

1. [Hadoop - Multi-Node Cluster](https://www.tutorialspoint.com/hadoop/hadoop_multi_node_cluster.htm)
2. [Setting Up A Multi Node Cluster In Hadoop 2.X](https://www.edureka.co/blog/setting-up-a-multi-node-cluster-in-hadoop-2-x/)
3. [Hadoop Cluster Setup](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/ClusterSetup.html)
4. [Hadoop UIs](https://www.jianshu.com/p/fdc0727d9bab)
5. TUM 2021 Summer Semester DDM Lab Weekly Reports of Group 2 and Group 3

# 3 Implementation on Individual Account

## Tips: Linux Command Line Shortcut

### Move Cursor

* Go to the beginning of the Line: **Ctrl + A**
* Go to the end of the Line: **Ctrl + E**
* Go to the previous word: **Option + Left Arrow**
* Go to the next word: **Option + Right Arrow**

### Clear

* Clear the current line from *end* to *beginning*: **Ctrl + U**
* Clear the current line from *beginning* to *end*: **Ctrl + K**
* Clear the previous Word: **Ctrl + U**

### [VI Editor](https://www.cs.colostate.edu/helpdocs/vi.html)

* To the end and beginning of the line: **$** and **^**
* To the end of the file: **Esc** then **Shift + G**
* [Navigation to lines](https://kb.iu.edu/d/adxw#:~:text=If%20you're%20already%20in,last%20line%20in%20the%20file.)
* [Navigation in words](https://askubuntu.com/questions/831917/jump-to-some-specific-line-in-the-vi-editor)

## 3.1 Environment Setup

1. Basic: 1 master and 1 slave

2. SSH Connection to Master and slave

   Master `$ ssh -i ~/.ssh/lrz_private -l ubuntu 10.195.4.230`

   Slave1 `$ ssh -i ~/.ssh/lrz_private -l ubuntu 10.195.5.118`

3. Install Java 8 

* Update the repositories `$ sudo apt-get update`

* Install openJDK `$ sudo apt-get install openjdk-8-jdk`

* Verify the version `$ java -version`

* Java available to all users (/usr/local directory)

  `sudo mv /usr/lib/jvm /usr/local/jvm `

* Setup **Path** & **JAVA_HOME** variable ([Meaning](https://coderanch.com/t/600047/java/Difference-JAVA-HOME-JRE-HOME))

  `$ vi ~/.bashrc` (**Also edit after Adding User**)

  ```
  export JAVA_HOME=/usr/local/jvm/java-8-openjdk-amd64
  export PATH=$PATH:$JAVA_HOME/bin
  ```
  
  `source ~/.bashrc`
  
  `java -version`

4. Create User

   `$ sudo useradd -m hadoop -G sudo` (-m create /home/username if not exists)

   ​	check sudoers via `$ sudo whoami`

   `$ sudo passwd hadoop`

5. Mapping the nodes

   `$ sudo vi /etc/hosts`

   Add the following content

   ```
   192.168.2.117 master
   192.168.3.34 slave1
   ```

   Check via `$ ping master` & `$ ping slave1`

6. Configuring Key-based Login ([Meaning](https://www.ssh.com/academy/ssh/copy-id)) 

   on master:

   `$ su hadoop` 

   `$ ssh-keygen -t rsa `

   `$ cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys`

   `$ chmod 600 ~/.ssh/authorized_keys` ([Troubleshooting](https://superuser.com/questions/189376/ssh-copy-id-does-not-work))

* If `ssh-copy-id` doesn't work: ([Troubleshooting](https://www.digitalocean.com/community/questions/ssh-copy-id-not-working-permission-denied-publickey))

  ​	 on slave1:

  ​	`$ sudo vi /etc/ssh/sshd_config` 

  ​		change `PasswordAuthentication no` to `PasswordAuthentication yes`

  ​    `$ sudo systemctl restart sshd`

  ​	on master:

  ​	`$ ssh-copy-id -i ~/.ssh/id_rsa.pub hadoop@slave1` 

  ​	on slave1:

  ​	`$ vi /etc/ssh/sshd_config` 

  ​		change `PasswordAuthentication yes` to `PasswordAuthentication no`

    `$ sudo systemctl restart sshd`

* **Don't use ssh-copy-id:** ([Troubleshooting](https://unix.stackexchange.com/questions/29386/how-do-you-copy-the-public-key-to-a-ssh-server))

  ​	on master:

  ​	`$ scp ~/.ssh/id_rsa.pub hadoop@slave1:~/.ssh/id_rsa.pub`

  ​	`$ cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys`

  ​	on slave1:	
  
  ​	`$ cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys`
  
  Test via `$ ssh slave1`
  
* ssh-keyscan command (See Ansible Gitlab)

7. Install Hadoop 2.7.3 ([Hadoop Java Versions](https://cwiki.apache.org/confluence/display/HADOOP/Hadoop+Java+Versions))

   `$ wget https://archive.apache.org/dist/hadoop/common/hadoop-2.7.3/hadoop-2.7.3.tar.gz`

   `$ tar xvzf hadoop-2.7.3.tar.gz`

   `$ vi ~/.bashrc`

   ```
   export HADOOP_HOME=$HOME/hadoop-2.7.3
   export PATH=$PATH:$HOME/hadoop-2.7.3/bin:$HOME/hadoop-2.7.3/sbin
   ```

   `$ source ~/.bashrc`

   `$ hadoop version`

   create link for hadoop `$ ln -s ~/hadoop-2.7.3 ~/hadoop` 

## 3.2 Update Configuration Files

 `$ cd ~/hadoop/etc/hadoop`

1. Edit `core-site.xml`

   `$ vi core-site.xml`

   Add the following within *Configuration*


   ```
   <configuration>
     	<property>
      		<name>fs.default.name</name>
      		<value>hdfs://master:9000/</value>
    	 </property>
   </configuration>
   ```

2. Edit `hdfs-site.xml`

   `$ mkdir -p /home/hadoop/hadoop_store/hdfs/namenode`

   `$ mkdir -p /home/hadoop/hadoop_store/hdfs/datanode ` 

   ​	`$ chmod 755 /home/hadoop/hadoop_store/hdfs/datanode ` 

   `$ vi hdfs-site.xml ` 


```
   <configuration>
      <property> 
         <name>dfs.data.dir</name> 
         <value>/home/hadoop/hadoop_store/hdfs/datanode</value> 
      </property> 
   
      <property> 
         <name>dfs.name.dir</name> 
         <value>/home/hadoop/hadoop_store/hdfs/namenode</value> 
      </property> 
   
      <property> 
         <name>dfs.replication</name> 
         <value>1</value> 
      </property> 
   </configuration>
```

3. Create and copy `mapred-site.xml`

   `$ cp mapred-site.xml.template mapred-site.xml`

   `$ vi mapred-site.xml`

```
   <configuration>
      <property> 
         <name>mapred.job.tracker</name> 
         <value>master:9001</value> 
      </property> 
   </configuration>
```

4. Edit `yarn-site.xml`

   `$ vi yarn-site.xml `

```
   <configuration>
     <property>
             <name>yarn.nodemanager.aux-services</name>
             <value>mapreduce_shuffle</value>
     </property>
     <property>
             <name>yarn.nodemanager.aux-services.mapreduce.shuffle.class</name>
             <value>org.apache.hadoop.mapred.ShuffleHandler</value>
     </property>
     <property>
             <name>yarn.resourcemanager.resource-tracker.address</name>
             <value>master:8025</value>
     </property>
     <property>
             <name>yarn.resourcemanager.scheduler.address</name>
             <value>master:8030</value>
     </property>
     <property>
             <name>yarn.resourcemanager.address</name>
             <value>master:8050</value>
     </property>
     <property>
             <name>yarn.resourcemanager.webapp.address</name>
             <value>master:8088</value>
     </property>
   </configuration>
```

5. Edit `hadoop-env.sh`: ([Troubleshooting: JAVA_HOME not set](https://stackoverflow.com/questions/51643455/error-java-home-is-not-set-and-could-not-be-found-after-hadoop-installation))

   `$ vi hadoop-env.sh`

   `$ export JAVA_HOME=/usr/local/jvm/java-8-openjdk-amd64`

6. Slaves should have the same configuration files

   (based on the same soft link `hadoop`)

   `$ scp core-site.xml hdfs-site.xml mapred-site.xml yarn-site.xml hadoop-env.sh hadoop@slave1:~/hadoop/etc/hadoop/.`

6. Edit `slaves` on master

   `vi slaves`

   The file content should be `192.168.3.34` (`localhost` deleted)

7. Format namenode on master

   `$ hdfs namenode -format`

   ​	check whether `Storage directory xxx has been successfully formatted` 

## 3.3 Hadoop Running

### Start

* `$ cd ~/hadoop/etc/hadoop`

1. `$ start-dfs.sh`

   Namenode & SecondaryNameNode on master

   Datanode on slave1

2. `$ start-yarn.sh`

   ResourceManager on master

   NodeManager on slave1

### Demo Examples

* [Jar File](https://en.wikipedia.org/wiki/JAR_(file_format))

* All Examples with no parameter

  ` yarn jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.3.jar`

* Create Input and Output direcs ([hdfs commands](https://www.geeksforgeeks.org/hdfs-commands/))

  `$ cd ~/hadoop/bin `

  `$ hdfs dfs -mkdir /user/hadoop/input` 

  `$ hdfs dfs -mkdir /user/hadoop/output`

1. [Pi](https://www.informit.com/articles/article.aspx?p=2190194&seqNum=3)

   `$ cd ~/hadoop`

   `$ yarn jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.3.jar pi 16 1000`

2. [Wordcount](https://i12r-studfilesrv.informatik.tu-muenchen.de/sose21/ddmlab/index.php/Weekly_Report_1_(Group_2))

   Create local files in `~/` using `vi ~/wordcount.txt`

   Move from local to hfds `$ hdfs dfs -copyFromLocal ~/wordcount.txt /user/hadoop/input/`

   `$ hdfs dfs -ls /user/hadoop/input/`

   `$ cd ~/hadoop`

   `$ yarn jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.3.jar wordcount /user/hadoop/input/wordcount.txt /user/hadoop/output/rwordcount`

   `$ hdfs dfs -ls /user/hadoop/output/rwordcount`

   `$ hdfs dfs -cat /user/hadoop/output/rwordcount/part-r-00000`























