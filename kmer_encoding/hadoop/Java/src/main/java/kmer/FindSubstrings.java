package kmer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;

public class FindSubstrings extends Configured implements Tool {

    @Override
    public int run(String[] strings) throws Exception{
        Configuration conf = super.getConf();
        conf.set("mapred.jobtracker.adress","master:54311");
        conf.set("mapreduce.framework.name","yarn");
        conf.set("yarn.resourcemanager.address","master:8050");
        conf.set("mapreduce.input.fileinputformat.split.maxsize","100000000");
        conf.set("kmer_size",strings[2]);
        Job job = new Job(conf);
        job.setJarByClass(FindSubstrings.class);
        job.setMapperClass(FindSubstrings.KmerMapper.class);
        job.setCombinerClass(FindSubstrings.KmerReducer.class);
        job.setReducerClass(FindSubstrings.KmerReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        CustomFileInputFormat.addInputPath(job,new Path(strings[0]));
        job.setInputFormatClass(CustomFileInputFormat.class);
        FileOutputFormat.setOutputPath(job, new Path(strings[1]));
        job.waitForCompletion(true);
        return 0;
    }

    public static class KmerMapper extends Mapper<Object, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text kmer = new Text();
        private static int kmer_size;

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            kmer_size = Integer.parseInt(context.getConfiguration().get("kmer_size","0"));
            byte[] byte_array = value.getBytes();
            HashMap<ByteBuffer, Integer> map = new HashMap();
            if(byte_array.length>0){
                for(int i = 0; i <= byte_array.length-kmer_size; i++) {
                    //map.put(ByteBuffer.wrap(Arrays.copyOfRange(byte_array, i, i + kmer_size)),1);
                    kmer.set(Arrays.copyOfRange(byte_array, i, i + kmer_size));
                    context.write(kmer,one);
                }
                /*for(ByteBuffer elt: map.keySet()){
                    kmer.set(elt.array());
                    context.write(kmer,one);
                }*/
            }
        }
    }

    public static class KmerReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context
        ) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        Instant start = Instant.now();
        int res = ToolRunner.run(new Configuration(), new FindSubstrings(), args);
        Instant end = Instant.now();
        long timeElapsed = Duration.between(start,end).toMinutes();
        System.out.println("Time took: "+timeElapsed+" minutes");
        System.exit(res);
    }
}
