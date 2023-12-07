package wordcount;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Iterator;

public class WordCountSpark {

    static class SplitFunction implements FlatMapFunction<String, String>
    {
        private static final long serialVersionUID = 1L;
        @Override
        public Iterator<String> call(String s) {
            return Arrays.asList(s.split(" ")).iterator();
        }

    }


    private static void wordCount(String fileName, String outputName) {

        SparkConf sparkConf = new SparkConf().setAppName("Word Counter");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        JavaRDD<String> inputFile = sparkContext.textFile(fileName);

        JavaRDD<String> words = inputFile.flatMap(new SplitFunction());

        /*Below code generates Pair of Word with count as one
         *similar to Mapper in Hadoop MapReduce*/
        JavaPairRDD<String, Integer> pairs = words
                .mapToPair(new PairFunction<String, String, Integer>() {
                    public Tuple2<String, Integer> call(String s) {
                        return new Tuple2<String, Integer>(s, 1);
                    }
                });

        /*Below code aggregates Pairs of Same Words with count
         *similar to Reducer in Hadoop MapReduce
         */
        JavaPairRDD<String, Integer> counts = pairs.reduceByKey(
                new Function2<Integer, Integer, Integer>() {
                    public Integer call(Integer a, Integer b) {
                        return a + b;
                    }
                });

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

        res.coalesce(1).saveAsTextFile(outputName);
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
        if (args.length < 2) {
            System.out.println("No input or output file provided.");
            System.exit(0);
        }
        wordCount(args[0], args[1]);
        Instant end = Instant.now();
        long elapsedTime = Duration.between(start,end).toMinutes();
        System.out.println("Time took: "+elapsedTime+" minutes");
    }
}