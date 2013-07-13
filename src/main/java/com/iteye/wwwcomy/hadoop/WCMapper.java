package com.iteye.wwwcomy.hadoop;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class WCMapper extends MapReduceBase implements
		Mapper<LongWritable, Text, Text, IntWritable> {

	private Text word = new Text();
	
	private static final IntWritable one = new IntWritable(1);
	
	@Override
	public void map(LongWritable key, Text value,
			OutputCollector<Text, IntWritable> output, Reporter reporter)
			throws IOException {
		String line = value.toString();
		StringTokenizer tokr = new StringTokenizer(line);
		while(tokr.hasMoreTokens()) {
			word.set(tokr.nextToken());
			output.collect(word, one);
		}
	}

}