package com.iteye.wwwcomy.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class TestHBase {

	private static Configuration conf = null;

	static {
		Configuration HBASE_CONFIG = new Configuration();
		// 与hbase/conf/hbase-site.xml中hbase.zookeeper.quorum配置的值相同
		HBASE_CONFIG.set("hbase.zookeeper.quorum", "1.1.7.63");
		// 与hbase/conf/hbase-site.xml中hbase.zookeeper.property.clientPort配置的值相同
		HBASE_CONFIG.set("hbase.zookeeper.property.clientPort", "2181");
		conf = HBaseConfiguration.create(HBASE_CONFIG);
	}

	public static void main(String[] agrs) throws Exception {
		testPutStudentObject();

		Student s = testGetStudentObject("studentObj", "row2");

		System.out.println("Start output:");
		System.out.println("id=" + s.getId());
		System.out.println("name=" + s.getName());
		System.out.println("gender=" + s.isGender());
		System.out.println("Read complete");
		// testStudentString();
	}

	private static void testPutStudentObject() {
		try {
			String tablename = "studentObj";
			String[] familys = { "value" };
			TestHBase.creatTable(tablename, familys);
			Student s1 = new Student();
			s1.setName("NameWhole");
			s1.setGender(true);
			s1.setId(555);

			try {
				HTable table = new HTable(conf, tablename);
				Put put = new Put(Bytes.toBytes("row2"));
				// put.add(Bytes.toBytes("value"), Bytes.toBytes(""),
				// TestBytes.changeObjectToBytes(s1));
				put.add(Bytes.toBytes("value"), Bytes.toBytes(""),
						TestBytes.changeToBytes(s1));
				table.put(put);
				System.out.println("insert recored name1 to table " + tablename
						+ " ok.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Student testGetStudentObject(String tableName, String rowKey)
			throws Exception {
		HTable table = new HTable(conf, tableName);
		Get get = new Get(rowKey.getBytes());
		Result rs = table.get(get);
		Student tmp = null;
		for (KeyValue kv : rs.raw()) {
			System.out.print(new String(kv.getRow()) + " ");
			System.out.print(new String(kv.getFamily()) + ":");
			System.out.print(new String(kv.getQualifier()) + " ");
			System.out.print(kv.getTimestamp() + " ");
			// tmp = TestBytes.changeBytesToObject(kv.getValue());
			tmp = (Student) TestBytes.changeToObject(kv.getValue());
			System.out.println(tmp);
		}
		return tmp;
	}

	private static void testStudentString() {
		try {
			String tablename = "student";
			String[] familys = { "id", "name", "score" };
			TestHBase.creatTable(tablename, familys);

			// add record zkb
			// TestHBase.addRecord(tablename,"row1","id","","095832");
			// TestHBase.addRecord(tablename,"row2","name","","zmac");
			// TestHBase.addRecord(tablename,"row3","score","math","97");
			// TestHBase.addRecord(tablename,"row4","score","chinese","87");
			// TestHBase.addRecord(tablename,"row5","score","english","85");
			// add record baoniu

			System.out.println("===========get one record========");
			TestHBase.getOneRecord(tablename, "2");

			System.out.println("===========show all record========");
			TestHBase.getAllRecord(tablename);

			// System.out.println("===========del one record========");
			// TestHBase.delRecord(tablename, "2");
			TestHBase.getAllRecord(tablename);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void creatTable(String tableName, String[] familys)
			throws Exception {
		HBaseAdmin admin = new HBaseAdmin(conf);
		if (admin.tableExists(tableName)) {
			System.out.println("table already exists!");
		} else {
			HTableDescriptor tableDesc = new HTableDescriptor(tableName);
			for (int i = 0; i < familys.length; i++) {
				tableDesc.addFamily(new HColumnDescriptor(familys[i]));
			}
			admin.createTable(tableDesc);
			System.out.println("create table " + tableName + " ok.");
		}
	}

	public static void deleteTable(String tableName) throws Exception {
		try {
			HBaseAdmin admin = new HBaseAdmin(conf);
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
			System.out.println("delete table " + tableName + " ok.");
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		}
	}

	public static void addRecord(String tableName, String rowKey,
			String family, String qualifier, String value) throws Exception {
		try {
			HTable table = new HTable(conf, tableName);
			Put put = new Put(Bytes.toBytes(rowKey));
			put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier),
					Bytes.toBytes(value));
			table.put(put);
			System.out.println("insert recored " + rowKey + " to table "
					+ tableName + " ok.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void delRecord(String tableName, String rowKey)
			throws IOException {
		HTable table = new HTable(conf, tableName);
		List list = new ArrayList();
		Delete del = new Delete(rowKey.getBytes());
		list.add(del);
		table.delete(list);
		System.out.println("del recored " + rowKey + " ok.");
	}

	public static void getOneRecord(String tableName, String rowKey)
			throws IOException {
		HTable table = new HTable(conf, tableName);
		Get get = new Get(rowKey.getBytes());
		Result rs = table.get(get);
		for (KeyValue kv : rs.raw()) {
			System.out.print(new String(kv.getRow()) + " ");
			System.out.print(new String(kv.getFamily()) + ":");
			System.out.print(new String(kv.getQualifier()) + " ");
			System.out.print(kv.getTimestamp() + " ");
			System.out.println(new String(kv.getValue()));
		}
	}

	public static void getAllRecord(String tableName) {
		try {
			HTable table = new HTable(conf, tableName);
			Scan s = new Scan();
			ResultScanner ss = table.getScanner(s);
			for (Result r : ss) {
				for (KeyValue kv : r.raw()) {
					System.out.print(new String(kv.getRow()) + " ");
					System.out.print(new String(kv.getFamily()) + ":");
					System.out.print(new String(kv.getQualifier()) + " ");
					System.out.print(kv.getTimestamp() + " ");
					System.out.println(new String(kv.getValue()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}