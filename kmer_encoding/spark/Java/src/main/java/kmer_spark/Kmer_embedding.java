package kmer_spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Kmer_embedding {

    public static void main(String[] args) throws FileNotFoundException {
        SparkConf conf = new SparkConf().setAppName("Kmer Embedding for Java").setMaster("spark://master:7077");
//        SparkConf conf = new SparkConf().setAppName("Kmer Embedding for Java").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> textFile = sc.textFile(args[1]);
        ArrayList<String> random_kmers = new ArrayList<>();
        File file = new File(args[2]);
        Scanner in = new Scanner(file);
        while(in.hasNext())
            random_kmers.add(in.nextLine().replaceAll("[\\n ]", ""));

        JavaRDD<String> sequences = textFile.filter(i -> i.matches("^[A-Z].*$"));

        int k = Integer.parseInt(args[0]);
        JavaRDD<Collection<Integer>> embeddings = sequences.flatMap(i -> {
            ArrayList<Collection<Integer>> vecs = new ArrayList<>();
            HashMap<String, Integer> kmers = new HashMap<>();
            for(String kmer : random_kmers)
                kmers.put(kmer, 0);
            for(int j = 0; j <= i.length() - k; j++){
                String sub = i.substring(j, j+k);
                if(random_kmers.contains(sub))
                    kmers.put(sub, kmers.get(sub)+1);
            }
            vecs.add(kmers.values());
            return vecs.iterator();
        });

        embeddings.coalesce(1).saveAsTextFile(args[3]);
    }
}
