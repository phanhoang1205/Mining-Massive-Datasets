import java.io.IOException;
import java.util.StringTokenizer;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class ASEANCaseCount {
    public static class TokenizerMapper
        extends Mapper<Object, Text, Text, LongWritable>{

        private final static LongWritable cases = new LongWritable();

        public void map(Object key, Text value, Context context
                        ) throws IOException, InterruptedException {

            String line = value.toString();                
            String[] columns = line.split("\t");
            
            String region = columns[1].trim();

            if(region.equals("South-East Asia")) {

                String numberCases = columns[2].trim();
                numberCases = numberCases.replace(",", "");
                double numberDouble = Double.parseDouble(numberCases);
                long cumulativeCases = (long) numberDouble;

                cases.set(cumulativeCases);
                context.write(new Text("ASEAN"), cases);
            }

        }
    }

    public static class IntSumReducer
        extends Reducer<Text, LongWritable, Text, LongWritable> {
        private LongWritable result = new LongWritable();


        public void reduce(Text key, Iterable<LongWritable> values,
                            Context context
                            ) throws IOException, InterruptedException {
            long sum = 0;
            for (LongWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "ASEAN Case Count");
        job.setJarByClass(ASEANCaseCount.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}