/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.oozie.example;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;

/**
 * Na wejściu <key: byte od początku strony value: strona badana [tab] linki na niej występujące oddzielone przecinkami>. W maperze produkujemy odwrotne
 * skierowania: strona wkazywana -> strona na której wystąpił link
 */

@SuppressWarnings("deprecation")
public class OozieMapper implements Mapper<LongWritable, Text, Text, Text> {

	public void configure(JobConf jobConf) {
	}

	public void map(LongWritable key, Text value, OutputCollector<Text, Text> collector, Reporter reporter) throws IOException {
		String[] row = value.toString().split("\t");

		if (row.length != 2)
			return;

		String[] siteCollection = row[1].split(",");
		for (String site : siteCollection) {
			collector.collect(new Text(site), new Text(row[0]));
		}
	}

	public void close() throws IOException {
	}

}
