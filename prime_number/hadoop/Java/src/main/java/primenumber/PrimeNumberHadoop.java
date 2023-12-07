package primenumber;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.stream.StreamSupport;


public class PrimeNumberHadoop extends Configured implements Tool {

    public final static int first_prime = 100;
    public final static int last_prime = 150;

    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf = super.getConf();
        conf.set("mapred.jobtracker.adress","master:54311");
        conf.set("mapreduce.framework.name","yarn");
        conf.set("yarn.resourcemanager.address","master:8050");
        Job job = new Job(conf);
        job.getConfiguration().setStrings("mapreduce.reduce.shuffle.memory.limit.percent", "0.1");
        job.setJarByClass(PrimeNumberHadoop.class);
        job.setMapperClass(PrimeNumberHadoop.TokenizerMapper.class);
        job.setReducerClass(PrimeNumberHadoop.PrimeReducer.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(IntWritable.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(strings[0]));
        FileOutputFormat.setOutputPath(job, new Path(strings[1]));
        Date startTime = new Date();
        job.waitForCompletion(true);
        Date endTime = new Date();
        System.out.println("The job took " +
                (endTime.getTime() - startTime.getTime()) / 1000 / 60 +
                " minutes.");
        return 0;
    }

    public static class TokenizerMapper extends Mapper<Object, Text, NullWritable, IntWritable> {

        final NullWritable null_writable = NullWritable.get();

        public final void map(final Object key, final Text value, final Context context) throws IOException, InterruptedException {
            final BigInteger number = new BigInteger(value.toString());
            if (number.isProbablePrime(10)) {
                context.write(null_writable, new IntWritable(number.intValue()));
            }
        }

    }

    public static class PrimeReducer extends Reducer<NullWritable, IntWritable, Text, IntWritable> {

        TreeSet<Integer> treeset;
        final NullWritable null_writable = NullWritable.get();

        @Override
        public void setup(Context context) {
            treeset = new TreeSet<Integer>();
        }

        @Override
        public void reduce(NullWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            for (IntWritable val : values) {
                treeset.add(val.get());
                while(treeset.size()>last_prime){
                    treeset.remove(treeset.last());
                }
            }

        }

        @Override
        public final void cleanup(Context context) throws IOException, InterruptedException {
            Iterator<Integer> itr = treeset.iterator();
            int i = 0;
            StringBuilder sb = new StringBuilder();
            while (itr.hasNext()) {
                if (i > last_prime)
                    break;
                if (i >= first_prime) {
                    sb.append(itr.next());
                    if (i < last_prime)
                        sb.append(", ");
                    continue;
                }
                i++;
                itr.next();
            }
            context.write(new Text(sb.toString()), null);
        }
    }

    public static void main(String[] args) throws Exception {
        Instant start = Instant.now();
        int res = ToolRunner.run(new Configuration(), new PrimeNumberHadoop(), args);
        Instant end = Instant.now();
        long timeElapsed = Duration.between(start,end).toMinutes();
        System.out.println("Time took: "+timeElapsed+" minutes");
        System.exit(res);

    }
}
