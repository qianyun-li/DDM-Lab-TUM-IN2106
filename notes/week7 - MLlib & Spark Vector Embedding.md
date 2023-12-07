# MLlib & Spark Vector Embedding

## Linux Command

* [AWK command in Unix/Linux with examples](https://www.geeksforgeeks.org/awk-command-unixlinux-examples/)

  * ```
    $ awk options 'selection _criteria {action}' input-file > output-file
    ```

  * ```
    $ awk 'script' filenames  
    # script in the following form
    /pattern/ { actions } 
    ```

* ```
  $ awk '/^>/ {printf("\n%s\n",$0);next; } { printf("%s",$0);} END {printf("\n");}' < test.fasta | tail -n +2 > linearized_squence.fa
  ```

* [Startup and Cleanup Actions](https://www.gnu.org/software/gawk/manual/html_node/Using-BEGIN_002fEND.html)
* [The next Statement](https://www.gnu.org/software/gawk/manual/html_node/Next-Statement.html)
* [How do multiple blocks work in AWK?](https://unix.stackexchange.com/questions/308869/how-do-multiple-blocks-work-in-awk)
* [Head command in Linux with examples](https://www.geeksforgeeks.org/head-command-linux-examples/)
* [Tail command in Linux with examples](https://www.geeksforgeeks.org/tail-command-linux-examples/)
  * **‘+’** option not in head
  *  start printing from line number ‘n’ till the end of the file specified
* [mv multiple files](https://askubuntu.com/questions/214560/how-to-move-multiple-files-at-once-to-a-specific-destination-directory)

## MLlib

* [PySpark MLlib Tutorial](https://www.youtube.com/watch?v=oDTJxEl95Go)
* [MLlib with Pyspark](https://www.datacamp.com/community/tutorials/apache-spark-tutorial-machine-learning)

## Data Processing

* `$ hdfs dfs -get /output_java_spark/part-00000 ~/threeMers`

* `$ shuf -n30000 threeMers | grep -o '[A-Z]*' >> random_3mers`
* [Linux > and >> and 2>](https://unix.stackexchange.com/questions/89386/what-is-symbol-and-in-unix-linux)

## Spark

* ```
  $ cd
  $ spark-submit --master spark://master:7077 --class kmer_spark.Kmer_embedding Kmer_spark-1.0-SNAPSHOT.jar 6 /data/test_set ~/kmers/random_300_kmers/randome_6mers /output_java_spark_6mer_embedding
  ```

## No SQL Database & Cassandra

* [Non-SQL DB & Hbase](https://www.youtube.com/watch?v=cEjDR3B_3cs)

