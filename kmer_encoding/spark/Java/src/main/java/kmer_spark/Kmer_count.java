package kmer_spark;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.ArrayList;

public class Kmer_count {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("Kmer_Spark_Java").setMaster("spark://master:7077");
//        SparkConf conf = new SparkConf().setAppName("Kmer_Spark_Java").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> textFile = sc.textFile(args[1]);

        JavaRDD<String> sequences  = textFile.filter( i -> i.matches("^[A-Z].*$"));

        int k = Integer.parseInt(args[0]);
        JavaRDD<String> kMers = sequences.flatMap(i -> {
            ArrayList<String> list = new ArrayList<>();
            for(int j = 0; j <= i.length()-k; j++)
                list.add(i.substring(j, j+k));
            return list.iterator();
        });

        JavaPairRDD<String, Integer> counts = kMers
                .mapToPair(kMer -> new Tuple2<>(kMer,1))
                .reduceByKey((a,b) -> a+b);

        JavaPairRDD<Integer, String> swapped = counts.mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {
            @Override
            public Tuple2<Integer, String> call(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
                return stringIntegerTuple2.swap();
            }
        }).sortByKey(false);

        JavaPairRDD<String, Integer> res = swapped.mapToPair(new PairFunction<Tuple2<Integer, String>, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(Tuple2<Integer, String> integerStringTuple2) throws Exception {
                return integerStringTuple2.swap();
            }
        });

        res.coalesce(1).saveAsTextFile(args[2]);
    }
}

