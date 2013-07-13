package com.iteye.wwwcomy.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class InputHBase extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
		long t = System.currentTimeMillis();
		int res = ToolRunner.run(new Configuration(), new InputHBase(), args);
		System.out.println(System.currentTimeMillis()-t);
//		JobClient.runJob(arg0)
		System.exit(res);
	}

	private static class MapClass extends TableMapper<Text, DoubleWritable> {
		public void map(ImmutableBytesWritable row, Result values,
				Context context) throws IOException, InterruptedException {
			Text key = new Text("sum");
			Double d = (double) 0;
			for (KeyValue kv : values.raw()) {
				if ("money".equalsIgnoreCase(Bytes.toString(kv.getFamily()))) {
					d = Bytes.toDouble(kv.getValue());
					DoubleWritable dw = new DoubleWritable(d);
					context.write(key, dw);
				}
			}
		}
	}

	private static class ReduceClass extends
			TableReducer<Text, DoubleWritable, Text> {
		public void reduce(Text key, Iterable<DoubleWritable> values,
				Context context) throws IOException, InterruptedException {
			double d = 0;
			for (DoubleWritable dw : values) {
				d += dw.get();
			}
			Put p = new Put(Bytes.toBytes("sum11"));
			System.out.println("reduce:"+d);
			Double dd = d;
			p.add(Bytes.toBytes("sum"), Bytes.toBytes("1000000"), Bytes.toBytes(dd.toString()));
			context.write(new Text("re"), p);
		}
	}

	public int run(String[] args) throws Exception {
		Configuration conf = null;
		conf = HBaseConfiguration.create();
		Job job = new Job(conf, "InputHBase");
		job.setJarByClass(InputHBase.class);
		Scan scan = new Scan();
		scan.addFamily(Bytes.toBytes("money"));
		TableMapReduceUtil.initTableMapperJob(Bytes.toBytes("salary"), scan,
				MapClass.class, Text.class, DoubleWritable.class, job);
		TableMapReduceUtil.initTableReducerJob("sum", ReduceClass.class, job);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		return 0;
	}

}
