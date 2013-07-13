package com.iteye.wwwcomy.hbase;

import java.io.Closeable;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
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
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import com.iteye.wwwcomy.lxn.utils.ByteUtil;
import com.iteye.wwwcomy.lxn.utils.DebugUtil;

public class HBaseUtil {

	private static Configuration conf = null;

	static {
		String quorum = "dapp1,dapp2,dapp3";
				//SharedBundle.getProperties("hbase.zookeeper.quorum");
		String port = "2181";
		//SharedBundle.getProperties("hbase.zookeeper.property.clientPort");

		Configuration HBASE_CONFIG = new Configuration();
		// 与hbase/conf/hbase-site.xml中hbase.zookeeper.quorum配置相同
		HBASE_CONFIG.set("hbase.zookeeper.quorum", quorum);
		// 与hbase/conf/hbase-site.xml中hbase.zookeeper.property.clientPort配置相同
		HBASE_CONFIG.set("hbase.zookeeper.property.clientPort", port);

		HBASE_CONFIG.set("hbase.regionserver.restart.on.zk.expire", "true");
		// HBASE_CONFIG.set("hbase.zookeeper.session.timeout", "180000");
		HBASE_CONFIG.set("zookeeper.session.timeout", "180000");

		conf = HBaseConfiguration.create(HBASE_CONFIG);
	}

	public static void main(String[] agrs) throws Throwable {
//		Env env = new Env("clientid", "test");
//		putObject("S_MESSAGES", "row1", env);
		// Env env2 = (Env) getObject("test1", "row1");
		// DebugUtil.debug(env2);

		// List<Put> listPut = new LinkedList<Put>();
		// for(int i =0;i<100000;i++){
		// Put put = new Put(Bytes.toBytes("test100K"+i));
		// byte[] bytes;
		// bytes = ByteUtil2.toBytes((Serializable) env);
		// put.add(Bytes.toBytes(FAMILY), Bytes.toBytes(QULIFIER), bytes);
		// put.setWriteToWAL(false);
		// listPut.add(put);
		// }
		// long start = System.currentTimeMillis();
		// pAddRecords("S_MESSAGES", listPut);
		// long stop = System.currentTimeMillis();
		// System.out.println("Cost Time:"+(stop-start));

		// Student s = testGetStudentObject("studentObj", "row2");

		// DebugUtil.debug("Start output:");
		// DebugUtil.debug("id=" + s.getId());
		// DebugUtil.debug("name=" + s.getName());
		// DebugUtil.debug("gender=" + s.isGender());
		// DebugUtil.debug("Read complete");
		// testStudentString();
	}

	// --------------------------------------------------------------------------------

	private static HBaseAdmin admin = null;

	private static HBaseAdmin getHBaseAdmin(Configuration conf)
			throws MasterNotRunningException, ZooKeeperConnectionException {
		if (admin == null)
			synchronized(HBaseAdmin.class) {
				if (admin == null)
					admin = new HBaseAdmin(conf);
			}
		return admin;
	}

	/**
	 * @param tableName
	 * @param families
	 * @throws Throwable
	 */
	public static synchronized void createTable(String tableName, String[] families)
			throws Throwable {
		HBaseAdmin admin = null;
		try {
			admin = getHBaseAdmin(conf); // admin = new HBaseAdmin(conf);
			if (admin.tableExists(tableName)) {
				DebugUtil.debug("table already exists!");
			} else {
				HTableDescriptor tableDesc = new HTableDescriptor(tableName);
				for (int i = 0; i < families.length; i++) {
					tableDesc.addFamily(new HColumnDescriptor(families[i]));
				}
				admin.createTable(tableDesc);
				DebugUtil.debug("create table " + tableName + " ok.");
			}
		} finally {
		}
	}

	/**
	 * @param tableName
	 * @throws Throwable
	 */
	public static synchronized void deleteTable(String tableName) throws Throwable {
		HBaseAdmin admin = null;
		try {
			admin = getHBaseAdmin(conf); // admin = new HBaseAdmin(conf);
			disableTable(tableName);
			admin.deleteTable(tableName);
			DebugUtil.debug("delete table " + tableName + " ok.");
		} finally {
		}
	}

	/**
	 * @param tableName
	 * @throws Throwable
	 */
	public static void disableTable(String tableName) throws Throwable {
		HBaseAdmin admin = null;
		try {
			admin = getHBaseAdmin(conf);
			admin.disableTable(tableName);
			DebugUtil.debug("disable table " + tableName + " ok.");
		} finally {
		}
	}

	/**
	 * @param tableName
	 * @throws Throwable
	 */
	public static void enableTable(String tableName) throws Throwable {
		HBaseAdmin admin = null;
		try {
			admin = getHBaseAdmin(conf);
			admin.enableTable(tableName);
			DebugUtil.debug("enable table " + tableName + " ok.");
		} finally {
		}
	}

	/*
	// private static HashMap<String, HTable> TABLE_CACHE = new HashMap<String,
	// HTable>();
	private static HTable getHTable(Configuration conf, String tableName)
			throws IOException {
		HTable table = null;
		table = new HTable(conf, tableName);
		// if (!TABLE_CACHE.containsKey(tableName)) {
		// synchronized (TABLE_CACHE) {
		// if (!TABLE_CACHE.containsKey(tableName)) {
		// table = new HTable(conf, tableName);
		// TABLE_CACHE.put(tableName, table);
		// }
		// }
		// }
		// table = TABLE_CACHE.get(tableName);
		return table;
	}
	*/
	//private static HTablePool hTablePool = null;
	
	// private static int POOL_MAX_SIZE = 5;
	
	private static HTablePool myPool = null;
	
	/**        
	 * 返回htablepool连接池中的一个htable
	 * @param tableName
	 * @return
	 * @throws Throwable 
	 */
	public static HTable getHTable(Configuration config, String tableName) throws Throwable{
		// 2. init HTablePool  
		if (myPool==null)
			synchronized(HTablePool.class) {
				if (myPool==null)
					myPool = new HTablePool(config);  
			}
        // 3. create HTablePool for a table  
        myPool.createHTablePool(tableName, HTablePool.DEFAULT_POOL_SIZE, true);  
        // 4. get already exist HTable from HTablePool  
        HTable table = (HTable) myPool.getHTable(tableName); 
//        table.setAutoFlush(true);
        return table;
		//if (hTablePool!=null)
		//	return (HTable) hTablePool.getTable(tableName);//如果hTablePool对象已经存在，直接取出一个htable
		//else{ // hTablePool不存在则先new一个htablepool对象，然后再取
		//	hTablePool=new HTablePool(conf, POOL_MAX_SIZE);//注意这个值设置的是每个htable表在pool中的最大值
		//	return (HTable) hTablePool.getTable(tableName);
		//}
	}
	
	/**
	 * @param table
	 */
	public static void closeTable(HTable table) {
		//if (table!=null)
		//	try {
		//		table.close();
		//	} catch(Throwable ignore) {
		//	}
		if (table!=null)
			myPool.putHTableBack(table); 
	}
	
	/**
	 * @param c
	 */
	public static void close(Closeable c) {
		if (c!=null)
			try {
				c.close();
			} catch(Throwable ignore) {
			}
	}
	
	// ------------------------------------ 通用Yigo表结构 ------------------------------------ 

	private static final String FAMILY = "YIGO";

	private static final String QULIFIER = "QULIFIER";

	public static void putObject(String tableName, String rowKey, Serializable obj) throws Throwable {
		addRecord(tableName, rowKey, FAMILY, QULIFIER, obj);
	}

	public static Serializable getObject(String tableName, String rowKey) throws Throwable {
		Object b = getOneRecord(tableName, rowKey, FAMILY, QULIFIER);
		if (b != null) {
			Serializable o = ByteUtil.toObject((byte[]) b);
			return o;
		}
		return null;
	}

	public static Serializable delObject(String tableName, String rowKey) throws Throwable {
		Object b = delRecord(tableName, rowKey, FAMILY, QULIFIER);
		if (b != null) {
			Serializable o = ByteUtil.toObject((byte[]) b);
			return o;
		}
		return null;
	}

	// --------------------------------------------------------------------------------

	private static byte[] EMPTY_BYTES;

	static {
		try {
			EMPTY_BYTES = "".getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param value
	 * @return
	 * @throws Throwable
	 */
	public static byte[] toHBaseByte(Object value) throws Throwable {
		byte[] bytes;
		if (value instanceof byte[]) {
			bytes = (byte[]) value;
		} else if (value instanceof Serializable) {
			bytes = value == null ? EMPTY_BYTES : ByteUtil.toBytes((Serializable) value);
		} else if (value instanceof Date) {
			bytes = value == null ? EMPTY_BYTES : Bytes.toBytes(((Date) value).getTime());
		} else {
			bytes = value == null ? EMPTY_BYTES : Bytes.toBytes(value.toString());
		}
		return bytes;
	}

	// org.apache.hadoop.hbase.TableNotFoundException
	private static void pAddRecord(String tableName, String rowKey, String family, String qualifier, Object value, boolean autoCreate) throws Throwable {
		Put put = new Put(Bytes.toBytes(rowKey));
		put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), toHBaseByte(value));
		HTable table = null;
		try {
			table = getHTable(conf, tableName);
			table.put(put);
			table.flushCommits();
			DebugUtil.debug("insert record " + rowKey + " to table " + tableName + " ok.");
		} catch (org.apache.hadoop.hbase.TableNotFoundException e) {
			if (autoCreate) {
				createTable(tableName, new String[] { family });
				pAddRecord(tableName, rowKey, family, qualifier, value, false);
				return;
			}
			throw e;
		} finally {
			closeTable(table);
		}
	}

	/**
	 * @param tableName
	 * @param rowKey
	 * @param family
	 * @param qualifier
	 * @param value
	 * @throws Throwable
	 */
	public static void addRecord(String tableName, String rowKey, String family, String qualifier, Object value) throws Throwable {
		pAddRecord(tableName, rowKey, family, qualifier, value, true);
	}

	/**
	 * @param tableName
	 * @param listPut
	 * @param families
	 * @throws Throwable
	 */
	public static void addRecords(String tableName, List<Put> listPut, String[] families) throws Throwable {
		HTable table = null;
		try {
			table = getHTable(conf, tableName);
			table.setAutoFlush(false);
			table.setWriteBufferSize(1024 * 1024 * 10);
			table.put(listPut);
			table.flushCommits();
			DebugUtil.debug("insert records to table " + tableName + " ok.");
		} catch (org.apache.hadoop.hbase.TableNotFoundException e) {
			if (families!=null && families.length>0) {
				createTable(tableName, families);
				addRecords(tableName, listPut, null);
				return;
			}
			throw e;
		} finally {
			table.setAutoFlush(true);
			// TODO 需不需要关闭listPut
			closeTable(table);
		}
	}

	/**
	 * @param tableName
	 * @param listGet
	 * @param families
	 * @return
	 * @throws Throwable
	 */
	public static Result[] getRecords(String tableName, List<Get> listGet, String[] families) throws Throwable {
		HTable table = null;
		try {
			table = getHTable(conf, tableName);
			Result[] rs = table.get(listGet);
			//table.flushCommits();
			DebugUtil.debug("get records from table " + tableName + " ok.");
			return rs;
		} catch (org.apache.hadoop.hbase.TableNotFoundException e) {
			throw e;
		} finally {
			//table.setAutoFlush(true);
			closeTable(table);
		}
	}

	private static Object pDelRecord(String tableName, String rowKey, String family, String qualifier) throws Throwable {
		Object o = getOneRecord(tableName, rowKey, family, qualifier);
		Delete del = new Delete(rowKey.getBytes());
		HTable table = null;
		try {
			table = getHTable(conf, tableName);
			if (o != null) {
				table.delete(del);
				table.flushCommits();
			}
			DebugUtil.debug("del record " + rowKey + " ok.");
			return o;
		} catch (org.apache.hadoop.hbase.TableNotFoundException e) {
			if (family!=null && family.length()>0) {
				createTable(tableName, new String[] {family});
				return pDelRecord(tableName, rowKey, null, null);
			}
			throw e;
		} finally {
			closeTable(table);
		}
	}

	/**
	 * @param tableName
	 * @param rowKey
	 * @param family
	 * @param qualifier
	 * @return
	 * @throws Throwable
	 */
	public static Object delRecord(String tableName, String rowKey, String family, String qualifier) throws Throwable {
		return pDelRecord(tableName, rowKey, family, qualifier);
	}

	/**
	 * @param tableName
	 * @param listDelete
	 * @param families
	 * @throws Throwable
	 */
	public static void delRecords(String tableName, List<Delete> listDelete, String[] families) throws Throwable {
		HTable table = null;
		try {
			table = getHTable(conf, tableName);
			table.delete(listDelete);
			table.flushCommits();
			DebugUtil.debug("del records from table " + tableName + " ok.");
		} catch (org.apache.hadoop.hbase.TableNotFoundException e) {
			if (families!=null && families.length>0) {
				createTable(tableName, families);
				delRecords(tableName, listDelete, null);
				return;
			}
			throw e;
		} finally {
			// TODO 需不需要关闭listDelete
			closeTable(table);
		}
	}
	
	/**
	 * @param tableName
	 * @param rowKeys
	 * @param family
	 * @param qualifier
	 * @param autoCreate
	 * @return
	 * @throws Throwable
	 */
	public static List<Object> getRecords(String tableName, String[] rowKeys, String family, String qualifier, boolean autoCreate) throws Throwable {
		List<Object> records = new ArrayList<Object>();
		List<Get> gets = new ArrayList<Get>();
		for (String k:rowKeys) {
			gets.add(new Get(k.getBytes()));
		}
		byte[] b = null;
		HTable table = null;
		try {
			table = getHTable(conf, tableName);
			Result[] rss = table.get(gets);
			for (Result rs:rss) {
				if (rs != null) {
					b = rs.getValue(Bytes.toBytes(family), Bytes.toBytes(qualifier));
					records.add(b);
				} else 
					records.add(null);
			}
			return records;
		} catch (org.apache.hadoop.hbase.TableNotFoundException e) {
			if (autoCreate) {
				createTable(tableName, new String[] { family });
				return getRecords(tableName, rowKeys, family, qualifier, false);
			}
			throw e;
		} finally {
			//close(rs);
			closeTable(table);
		}
	}

	private static Object pGetOneRecord(String tableName, String rowKey, String family, String qualifier, boolean autoCreate) throws Throwable {
		Get get = new Get(rowKey.getBytes());
		byte[] b = null;
		HTable table = null;
		try {
			table = getHTable(conf, tableName);
			Result rs = table.get(get);
			if (rs != null) {
				b = rs.getValue(Bytes.toBytes(family), Bytes.toBytes(qualifier));
				return b;
			}
			return null;
		} catch (org.apache.hadoop.hbase.TableNotFoundException e) {
			if (autoCreate) {
				createTable(tableName, new String[] { family });
				return pGetOneRecord(tableName, rowKey, family, qualifier, false);
			}
			throw e;
		} finally {
			//close(rs);
			closeTable(table);
		}
	}

	/**
	 * @param tableName
	 * @param rowKey
	 * @param family
	 * @param qualifier
	 * @return
	 * @throws Throwable
	 */
	public static Object getOneRecord(String tableName, String rowKey, String family, String qualifier) throws Throwable {
		return pGetOneRecord(tableName, rowKey, family, qualifier, true);
	}

	/**
	 * @param tableName
	 * @param rowKey
	 * @param allMap
	 */
	public static void setOneRow(String tableName, String rowKey, Map<String, Map<String, byte[]>> allMap) throws Throwable {
		HTable table = null;
		try {
			if (allMap == null) 
				return;
			table = getHTable(conf, tableName);
			Put put = new Put(Bytes.toBytes(rowKey));
			Iterator<String> iKey = allMap.keySet().iterator(), iColumnKey;
			String family, qualifier;
			Map<String, byte[]> valueMap;
			while (iKey.hasNext()) {
				family = (String) iKey.next();
				valueMap = (Map<String, byte[]>) allMap.get(family);
				iColumnKey = valueMap.keySet().iterator();
				while (iColumnKey.hasNext()) {
					qualifier = (String) iColumnKey.next();
					put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), (byte[]) valueMap.get(qualifier));
				}
			}
			table.put(put);
			table.flushCommits();
			DebugUtil.debug("insert records of one row " + rowKey + " to table " + tableName + " ok.");
		} 
//		catch (org.apache.hadoop.hbase.TableNotFoundException e) {
//			DebugUtil.debug(e);
//			throw e;
//		} 
		finally {
			closeTable(table);
		}
	}

	/**
	 * @param tableName
	 * @param rowKey
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("rawtypes")
	public static Map<?, ?> getOneRow(String tableName, String rowKey) throws Throwable {
		Get get = new Get(rowKey.getBytes());
		HTable table = null;
		try {
			table = getHTable(conf, tableName);
			Result rs = table.get(get);
			if (rs != null) {
				return cloneMap((TreeMap) rs.getNoVersionMap());
				// return rs.getNoVersionMap();
			}
			return new HashMap();
		} 
//		catch (org.apache.hadoop.hbase.TableNotFoundException e) {
//			DebugUtil.debug(e);
//			return new HashMap();
//		} 
		finally {
			closeTable(table);
		}
	}

	/**
	 * @param table
	 * @param row
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<?, ?> delOneRow(String tableName, String rowKey) throws Throwable {
		HTable table = null;
		try {
			table = getHTable(conf, tableName);
			Map map = (HashMap) getOneRow(tableName, rowKey);
			if (map.size() > 0) {
				Delete del = new Delete(rowKey.getBytes());
				table.delete(del);
				table.flushCommits();
			}
			DebugUtil.debug("del row " + rowKey + " ok.");
			return map;
		} finally {
			closeTable(table);
		}
	}

	/**
	 * TODO 这里这样做是否合适？ TreeMap中的Comparator无法序列化。。
	 * 一方面是为了序列化，另一方面是为了做byte数组到String的转换，因为set时候传进来的CF和CK都是String，传回去的也应当保持一致
	 * 
	 * @param srcMap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map cloneMap(TreeMap srcMap) {
		if (srcMap == null)
			return null;
		Map tgtMap = new HashMap(), valueMap, ckMap;
		Iterator iKey = srcMap.keySet().iterator(), iColumnKey;
		byte[] columnFamily, columnKey;
		while (iKey.hasNext()) {
			columnFamily = (byte[]) iKey.next();
			valueMap = (Map) srcMap.get(columnFamily);
			iColumnKey = valueMap.keySet().iterator();
			ckMap = new HashMap();
			while (iColumnKey.hasNext()) {
				columnKey = (byte[]) iColumnKey.next();
				ckMap.put(Bytes.toString(columnKey), valueMap.get(columnKey));
			}
			tgtMap.put(Bytes.toString(columnFamily), ckMap);
		}
		return tgtMap;
	}
	
	/**
	 * 批量添加多条记录
	 * @param tableName
	 * @param allMap
	 * @throws Throwable
	 */
	public static void addMultiData(String tableName, Map<String, Map<String, Map<String, byte[]>>> allMap) throws Throwable {
		if (allMap.size() == 1) {
			String rowKey = allMap.keySet().iterator().next();
			setOneRow(tableName, rowKey, allMap.get(rowKey));
			return;
		}
		if (allMap.size() > 1) {
			Iterator<String> rowIter = allMap.keySet().iterator();
			String rowKey = null;
			Put put = null;
			List<Put> listPut = new ArrayList<Put>();
			while (rowIter.hasNext()) {
				rowKey = rowIter.next();
				put = new Put(Bytes.toBytes(rowKey));
				Map<String, Map<String, byte[]>> familyMap = allMap.get(rowKey);
				Iterator<String> familyIter = familyMap.keySet().iterator();
				String colFamily = null;
				while (familyIter.hasNext()) {
					colFamily = familyIter.next();
					Map<String, byte[]> keyMap = familyMap.get(colFamily);
					Iterator<String> keyIter = keyMap.keySet().iterator();
					while (keyIter.hasNext()) {
						String colKey = keyIter.next();
						put.add(Bytes.toBytes(colFamily), Bytes.toBytes(colKey), keyMap.get(colKey));
					}
				}
				listPut.add(put);
			}
			addRecords(tableName, listPut, null);
		}
	}

	/**
	 * @param tableName
	 * @param row
	 * @param family
	 * @param qualifier
	 * @return
	 * @throws Throwable
	 */
	public static List<Object> delMultiData(String tableName, String[] row, String[] family, String[] qualifier) throws Throwable {
		if (row.length == 1)
			return delMultiData(tableName, row[0], family, qualifier);
		if (row.length > 0 && row.length == family.length && row.length == qualifier.length) {
			int i = 0;
			ArrayList<Delete> delList = new ArrayList<Delete>();
			Delete del = null;
			for (; i < row.length; i++) {
				del = new Delete(row[i].getBytes());
				del.deleteColumn(family[i].getBytes(), qualifier[i].getBytes());
				delList.add(del);
			}
			delRecords(tableName, delList, family);
		}
		return null;
	}

	private static List<Object> delMultiData(String tableName, String row, String[] family, String[] qualifier) throws Throwable {
		Delete del = new Delete(row.getBytes());
		int i = 0;
		for (; i < family.length; i++) {
			del.deleteColumn(family[i].getBytes(), qualifier[i].getBytes());
		}
		HTable table = null;
		try {
			table = getHTable(conf, tableName);
			table.delete(del);
			table.flushCommits();
			DebugUtil.debug("del records from table " + tableName + " ok.");
		} catch (org.apache.hadoop.hbase.TableNotFoundException e) {
			if (family!=null && family.length>0) {
				createTable(tableName, family);
				return null;
			}
			throw e;
		} finally {
			closeTable(table);
		}
		return null;
	}

	/**
	 * 获取多个Cell中的值，并按照顺序返回，注意数组大小要一致
	 * 
	 * @param tableName
	 * @param row
	 * @param family
	 * @param qualifier
	 * @return
	 * @throws Throwable
	 */
	public static List<Object> getMultiData(String tableName, String[] row, String[] family, String[] qualifier) throws Throwable {
		List<Object> resultList = null;
		if (row.length > 0 && family.length == 1 && qualifier.length == 1) {
			resultList = getRecords(tableName, row, family[0], qualifier[0], true);
		} else if (row.length > 0 && row.length == family.length && row.length == qualifier.length) {
			int i = 0;
			ArrayList<Get> getList = new ArrayList<Get>();
			Get get = null;
			for (; i < row.length; i++) {
				get = new Get(row[i].getBytes());
				get.addColumn(family[i].getBytes(), qualifier[i].getBytes());
				getList.add(get);
			}
			Result[] rs = getRecords(tableName, getList, null);
			resultList = new ArrayList<Object>();
			for (Result result : rs) {
				TreeMap<byte[], NavigableMap<byte[], byte[]>> map 
					= (TreeMap<byte[], NavigableMap<byte[], byte[]>>) result.getNoVersionMap();
				Iterator<byte[]> i1 = map.keySet().iterator();
				while (i1.hasNext()) {
					byte[] ckb = i1.next();
					TreeMap<byte[], byte[]> cMap = (TreeMap<byte[], byte[]>) map.get(ckb);
					Iterator<byte[]> ki = cMap.keySet().iterator();
					while (ki.hasNext()) {
						byte[] kb = ki.next();
						resultList.add(cMap.get(kb));
					}
				}
			}
		}
		return resultList;
	}

	/**
	 * 通过输入条件返回RowKey，现在的是通过相等的方式过滤的 TODO 灵活的Filter处理方式待补充
	 * 
	 * @param tableName
	 * @param family
	 * @param qualifier
	 * @param val
	 * @return
	 * @throws Throwable
	 */
	public static List<String> findRowKey(String tableName, String[] family, String[] qualifier, Object[] val) throws Throwable {
		if (family.length != qualifier.length || family.length != val.length)
			return null;
		FilterList filterList = new FilterList();
		int i = 0;
		for (; i < family.length; i++) {
			filterList.addFilter(new SingleColumnValueFilter(Bytes.toBytes(family[i]), Bytes.toBytes(qualifier[i]), 
					CompareOp.EQUAL, (byte[]) val[i]));
		}
		HTable table = null;
		ArrayList<String> result = new ArrayList<String>();
		ResultScanner rs = null;
		try {
			table = getHTable(conf, tableName);
			Scan s = new Scan();
			s.setFilter(filterList);
			rs = table.getScanner(s);
			rs = table.getScanner(s);
			for (Result rr = rs.next(); rr != null; rr = rs.next()) {
				result.add(new String(rr.getRow()));
			}
			DebugUtil.debug("Filter Complete.");
			return result;
		} catch (org.apache.hadoop.hbase.TableNotFoundException e) {
			if (family!=null && family.length>0) {
				createTable(tableName, family);
				return findRowKey(tableName, family, qualifier, val);
			}
			throw e;
		} finally {
			close(rs);
			closeTable(table);
		}
	}
	
}
