package com.iteye.wwwcomy.lxn.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;

/**
 * @author Liuxn
 * 
 */
public class ByteUtil {

	/**
	 * Size of short in bytes
	 */
	public static final int SIZEOF_SHORT = Short.SIZE / Byte.SIZE;
	/**
	 * Size of int in bytes
	 */
	public static final int SIZEOF_INT = Integer.SIZE / Byte.SIZE;
	/**
	 * Size of long in bytes
	 */
	public static final int SIZEOF_LONG = Long.SIZE / Byte.SIZE;

	/**
	 * @param input
	 * @param output
	 * @param buffersize
	 * @throws IOException
	 */
	public static void copy(InputStream input, OutputStream output, int buffersize)
			throws IOException {
		byte buf[] = new byte[buffersize];
		int length = 0;
		while ((length = input.read(buf)) > 0) {
			output.write(buf, 0, length);
		}
	}

	public static byte[] toBytes(Serializable obj) throws Throwable {
		if (obj==null)
			return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream dos = new ObjectOutputStream(baos);
		byte[] bytes = null;
		try {
			dos.writeObject(obj);
			bytes = baos.toByteArray();
		}finally {
			try {
				dos.close();
			}catch(Throwable e){
				e.printStackTrace();
			}
			try {
				baos.close();
			}catch(Throwable e){
				e.printStackTrace();
			}
		}
		return bytes;
	}

	public static Serializable toObject(byte[] bytes) throws Throwable {
		if (bytes==null)
			return null;
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream dis = new ObjectInputStream(bis);
		Serializable s = null;
		try {
			s = (Serializable) dis.readObject();
		}finally {
			try {
				dis.close();
			}catch(Throwable e){
				e.printStackTrace();
			}
			try {
				bis.close();
			}catch(Throwable e){
				e.printStackTrace();
			}
		}
		return s;
	}
	
	/**
	 * @param is
	 * @throws Throwable
	 */
	public final static byte[] getBytes(InputStream is) throws IOException {
		// ByteBuffer bb = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 创建缓存
		byte[] bytes = new byte[1024];
		int cha = -1;
		try {
			while ((cha = is.read(bytes)) != -1) {
				baos.write(bytes, 0, cha);
			}
			return baos.toByteArray();
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (baos != null)
				baos.close();
		}
	}

	/**
	 * @param is
	 *            转化的inputstream源
	 * @param off
	 *            起始byte位置
	 * @param length
	 *            byte长度
	 * @param total
	 *            byte总数
	 * @return byte[] 指定开始位置off长度为length的byte数组
	 * @throws IOException
	 */
	public final static byte[] getBytes(InputStream is, int off, int length,
			int total) throws IOException {
		int endCount = (off - 1) / length;
		// DebugUtil.debug(">读取第" + endCount + "块数据");
		if (off >= total)
			return "".getBytes();
		//
		ByteBuffer bb = null;
		// int len = length < total - off ? length : total - off;
		// 创建缓存
		byte[] bytes = new byte[length];
		//
		int count = 0;
		int cha = -1;
		try {
			while ((cha = is.read(bytes, 0, length)) != -1) {
				// 读到第几块跳出返回
				if (count == endCount) {
					bb = ByteBuffer.allocate(cha);
					bb.put(bytes, 0, cha);
					// return ByteBuffer.wrap(bytes).array();
					break;
				}
				count++;
			}
			if (bb == null)
				return "".getBytes();
			return bb.array();
		} catch (IOException ex) {
			throw ex;
		}
	}

	/**
	 * @param info
	 * @param type
	 * @param pref
	 * @param latf
	 * @return String
	 * @throws NoSuchAlgorithmException
	 */
	public static final String security2HexString(byte[] info, String type,
			String pref, String latf) throws NoSuchAlgorithmException {
		try {
			// java.security.MessageDigest
			// alg = java.security.MessageDigest.getInstance("MD5");
			if (type == null)
				type = "SHA-1";
			java.security.MessageDigest alga = java.security.MessageDigest
					.getInstance(type); // 获取散列计算方式
			alga.update(info); // 计算散列
			byte[] digest = alga.digest(); // 散列转换
			//
			// DebugUtil.debug(">security:" + toHexString(digest, pref, latf));
			return toHexString(digest, pref, latf);
		} catch (NoSuchAlgorithmException ex) {
			// DebugUtil.debug("> ");
			throw ex;
		}
	}

	/**
	 * @param b
	 * @param pref
	 * @param latf
	 * @return String
	 */
	public static final String toHexString(byte[] b, String pref, String latf) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(pref).append(
					Integer.toHexString(b[i] < 0 ? b[i] + 256 : b[i])).append(
					latf);
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * @param info
	 * @param type
	 * @return String
	 * @throws NoSuchAlgorithmException
	 */
	public static final String security2HexString(byte[] info)
			throws NoSuchAlgorithmException {
		return security2HexString(info, "MD5", "", "");
	}

	/**
	 * 将字符串进行MD5处理
	 * 
	 * @param info
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static final String security2HexString(String info)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return security2HexString(info.getBytes("UTF-8"), "MD5", "", "");
	}

	public static final String security2HexString(String info, boolean bw)
			throws NoSuchAlgorithmException {
		return security2HexString(info.getBytes(), "MD5", "", "", bw);
	}

	public static final String security2HexString(byte[] info, String type,
			String pref, String latf, boolean bw)
			throws NoSuchAlgorithmException {
		try {
			// java.security.MessageDigest
			// alg = java.security.MessageDigest.getInstance("MD5");
			if (type == null)
				type = "SHA-1";
			java.security.MessageDigest alga = java.security.MessageDigest
					.getInstance(type); // 获取散列计算方式
			alga.update(info); // 计算散列
			byte[] digest = alga.digest(); // 散列转换
			// DebugUtil.debug(">security:" + toHexString(digest, pref, latf));
			return toHexString(digest, pref, latf, bw);
		} catch (NoSuchAlgorithmException ex) {
			// DebugUtil.debug("> ");
			throw ex;
		}
	}

	public static final String toHexString(byte[] b, String pref, String latf,
			boolean bw) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] < 0 ? b[i] + 256 : b[i]);
			if (bw && hex.length() == 1)
				hex = "0" + hex;
			sb.append(pref).append(hex).append(latf);
		}
		return sb.toString().toUpperCase();
	}

	// /**
	// * @param bytes
	// */
	// public final static String debug(byte[] bytes) {
	// //
	// BKStringBuffer msb = new BKStringBuffer();
	// // MyStringBuffer msb2 = new MyStringBuffer();
	// for (int i = 0; i < bytes.length; i++) {
	// // DebugUtil.debug("" + bytes[i] + ";");
	// int temp = bytes[i] < 0 ? bytes[i] + 256 : bytes[i];
	// msb.append("" + temp).append(";");
	// // msb2.append("" + bytes[i]).appendln(";");
	// }
	// DebugUtil.debug(msb);
	// return msb.toString();
	// }

	/**
	 * @param key
	 * @return String
	 */
	public final static String toSafeKey(String key) {
		key = key.toLowerCase();
		while (key.startsWith("/"))
			key = key.substring(1);
		while (key.startsWith("\\"))
			key = key.substring(1);
		key = StringUtil.replaceAll(key, "/", "_");
		key = StringUtil.replaceAll(key, "\\", "_");
		return key;
	}

	/**
	 * Convert a boolean to a byte array. True becomes -1 and false becomes 0.
	 * 
	 * @param b
	 *            value
	 * @return <code>b</code> encoded in a byte array.
	 */
	public static byte[] toBytes(final boolean b) {
		return new byte[] { b ? (byte) -1 : (byte) 0 };
	}

	/**
	 * Reverses {@link #toBytes(boolean)}
	 * 
	 * @param b
	 *            array
	 * @return True or false.
	 */
	public static boolean toBoolean(final byte[] b) {
		if (b.length != 1) {
			throw new IllegalArgumentException("Array has wrong size: " + b.length);
		}
		return b[0] != (byte) 0;
	}

	/**
	 * Serialize a double as the IEEE 754 double format output. The resultant
	 * array will be 8 bytes long.
	 * 
	 * @param d
	 *            value
	 * @return the double represented as byte []
	 */
	public static byte[] toBytes(final double d) {
		// Encode it as a long
		return ByteUtil.toBytes(Double.doubleToRawLongBits(d));
	}

	 /**
	   * @param bytes byte array
	   * @return Return double made from passed bytes.
	   */
	public static double toDouble(final byte[] bytes) {
		return toDouble(bytes, 0);
	}

	  /**
	   * @param bytes byte array
	   * @param offset offset where double is
	   * @return Return double made from passed bytes.
	   */
	public static double toDouble(final byte[] bytes, final int offset) {
		return Double.longBitsToDouble(toLong(bytes, offset, SIZEOF_LONG));
	}

	/**
	 * Convert an int value to a byte array
	 * 
	 * @param val
	 *            value
	 * @return the byte array
	 */
	public static byte[] toBytes(int val) {
		byte[] b = new byte[4];
		for (int i = 3; i > 0; i--) {
			b[i] = (byte) val;
			val >>>= 8;
		}
		b[0] = (byte) val;
		return b;
	}

	/**
	 * Converts a byte array to an int value
	 * 
	 * @param bytes
	 *            byte array
	 * @return the int value
	 */
	public static int toInt(byte[] bytes) {
		return toInt(bytes, 0, SIZEOF_INT);
	}

	/**
	 * Converts a byte array to an int value
	 * 
	 * @param bytes
	 *            byte array
	 * @param offset
	 *            offset into array
	 * @return the int value
	 */
	public static int toInt(byte[] bytes, int offset) {
		return toInt(bytes, offset, SIZEOF_INT);
	}

	/**
	 * Converts a byte array to an int value
	 * 
	 * @param bytes
	 *            byte array
	 * @param offset
	 *            offset into array
	 * @param length
	 *            length of int (has to be {@link #SIZEOF_INT})
	 * @return the int value
	 * @throws IllegalArgumentException
	 *             if length is not {@link #SIZEOF_INT} or if there's not enough
	 *             room in the array at the offset indicated.
	 */
	public static int toInt(byte[] bytes, int offset, final int length) {
		if (length != SIZEOF_INT || offset + length > bytes.length) {
			throw new RuntimeException("toInt转换失败");
		}
		int n = 0;
		for (int i = offset; i < (offset + length); i++) {
			n <<= 8;
			n ^= bytes[i] & 0xFF;
		}
		return n;
	}

	/**
	 * @param f
	 *            float value
	 * @return the float represented as byte []
	 */
	public static byte[] toBytes(final float f) {
		// Encode it as int
		return ByteUtil.toBytes(Float.floatToRawIntBits(f));
	}

	/**
	 * Presumes float encoded as IEEE 754 floating-point "single format"
	 * 
	 * @param bytes
	 *            byte array
	 * @return Float made from passed byte array.
	 */
	public static float toFloat(byte[] bytes) {
		return toFloat(bytes, 0);
	}

	/**
	 * Presumes float encoded as IEEE 754 floating-point "single format"
	 * 
	 * @param bytes
	 *            array to convert
	 * @param offset
	 *            offset into array
	 * @return Float made from passed byte array.
	 */
	public static float toFloat(byte[] bytes, int offset) {
		return Float.intBitsToFloat(toInt(bytes, offset, SIZEOF_INT));
	}

	/**
	 * Converts a string to a UTF-8 byte array.
	 * 
	 * @param s
	 *            string
	 * @return the byte array
	 * @throws Throwable
	 */
	public static byte[] toBytes(String s) throws Throwable {
		try {
			return s.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw e;
		}
	}

	/**
	 * @param b
	 *            Presumed UTF-8 encoded byte array.
	 * @return String made from <code>b</code>
	 * @throws Throwable
	 */
	public static String toString(final byte[] b) throws Throwable {
		if (b == null) {
			return null;
		}
		return toString(b, 0, b.length);
	}

	/**
	 * Joins two byte arrays together using a separator.
	 * 
	 * @param b1
	 *            The first byte array.
	 * @param sep
	 *            The separator to use.
	 * @param b2
	 *            The second byte array.
	 * @throws Throwable
	 */
	public static String toString(final byte[] b1, String sep, final byte[] b2) throws Throwable {
		return toString(b1, 0, b1.length) + sep + toString(b2, 0, b2.length);
	}

	/**
	 * This method will convert utf8 encoded bytes into a string. If an
	 * UnsupportedEncodingException occurs, this method will eat it and return
	 * null instead.
	 * 
	 * @param b
	 *            Presumed UTF-8 encoded byte array.
	 * @param off
	 *            offset into array
	 * @param len
	 *            length of utf-8 sequence
	 * @return String made from <code>b</code> or null
	 * @throws Throwable
	 */
	public static String toString(final byte[] b, int off, int len) throws Throwable {
		if (b == null) {
			return null;
		}
		if (len == 0) {
			return "";
		}
		try {
			return new String(b, off, len, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw e;
		}
	}

	/**
	 * Convert a long value to a byte array using big-endian.
	 * 
	 * @param val
	 *            value to convert
	 * @return the byte array
	 */
	public static byte[] toBytes(long val) {
		byte[] b = new byte[8];
		for (int i = 7; i > 0; i--) {
			b[i] = (byte) val;
			val >>>= 8;
		}
		b[0] = (byte) val;
		return b;
	}

	/**
	 * Converts a byte array to a long value. Reverses {@link #toBytes(long)}
	 * 
	 * @param bytes
	 *            array
	 * @return the long value
	 */
	public static long toLong(byte[] bytes) {
		return toLong(bytes, 0, SIZEOF_LONG);
	}

	/**
	 * Converts a byte array to a long value. Assumes there will be
	 * {@link #SIZEOF_LONG} bytes available.
	 * 
	 * @param bytes
	 *            bytes
	 * @param offset
	 *            offset
	 * @return the long value
	 */
	public static long toLong(byte[] bytes, int offset) {
		return toLong(bytes, offset, SIZEOF_LONG);
	}

	/**
	 * Converts a byte array to a long value.
	 * 
	 * @param bytes
	 *            array of bytes
	 * @param offset
	 *            offset into array
	 * @param length
	 *            length of data (must be {@link #SIZEOF_LONG})
	 * @return the long value
	 * @throws IllegalArgumentException
	 *             if length is not {@link #SIZEOF_LONG} or if there's not
	 *             enough room in the array at the offset indicated.
	 */
	public static long toLong(byte[] bytes, int offset, final int length) {
		if (length != SIZEOF_LONG || offset + length > bytes.length) {
			throw new RuntimeException("toLong转换失败");
		}
		long l = 0;
		for (int i = offset; i < offset + length; i++) {
			l <<= 8;
			l ^= bytes[i] & 0xFF;
		}
		return l;
	}

	/**
	 * Convert a short value to a byte array of {@link #SIZEOF_SHORT} bytes
	 * long.
	 * 
	 * @param val
	 *            value
	 * @return the byte array
	 */
	public static byte[] toBytes(short val) {
		byte[] b = new byte[SIZEOF_SHORT];
		b[1] = (byte) val;
		val >>= 8;
		b[0] = (byte) val;
		return b;
	}

	/**
	 * Converts a byte array to a short value
	 * 
	 * @param bytes
	 *            byte array
	 * @return the short value
	 */
	public static short toShort(byte[] bytes) {
		return toShort(bytes, 0, SIZEOF_SHORT);
	}

	/**
	 * Converts a byte array to a short value
	 * 
	 * @param bytes
	 *            byte array
	 * @param offset
	 *            offset into array
	 * @return the short value
	 */
	public static short toShort(byte[] bytes, int offset) {
		return toShort(bytes, offset, SIZEOF_SHORT);
	}

	/**
	 * Converts a byte array to a short value
	 * 
	 * @param bytes
	 *            byte array
	 * @param offset
	 *            offset into array
	 * @param length
	 *            length, has to be {@link #SIZEOF_SHORT}
	 * @return the short value
	 * @throws IllegalArgumentException
	 *             if length is not {@link #SIZEOF_SHORT} or if there's not
	 *             enough room in the array at the offset indicated.
	 */
	public static short toShort(byte[] bytes, int offset, final int length) {
		if (length != SIZEOF_SHORT || offset + length > bytes.length) {
			throw new RuntimeException("toShort转换失败");
		}
		short n = 0;
		n ^= bytes[offset] & 0xFF;
		n <<= 8;
		n ^= bytes[offset + 1] & 0xFF;
		return n;
	}

	public static void main(String[] args) throws Throwable {
		String sArg = "test1";
		int iArg = 47;
		short shArg = (short) 47;
		long lArg = 47L;
		boolean bArg = false;
		float fArg = (float)47.0;
		double dArg = (double)47.0;
		System.out.println("TestString:\t"+toBytes(sArg)+"\tCastBack:\t"+toString(toBytes(sArg)));
		System.out.println("TestInt:\t"+toBytes(iArg)+"\tCastBack:\t"+toInt(toBytes(iArg)));
		System.out.println("TestShort:\t"+toBytes(shArg)+"\tCastBack:\t"+toShort(toBytes(shArg)));
		System.out.println("TestLong:\t"+toBytes(lArg)+"\tCastBack:\t"+toLong(toBytes(lArg)));
		System.out.println("TestBoolean:\t"+toBytes(bArg)+"\tCastBack:\t"+toBoolean(toBytes(bArg)));
		System.out.println("TestFloat:\t"+toBytes(fArg)+"\tCastBack:\t"+toFloat(toBytes(fArg)));
		System.out.println("TestDouble:\t"+toBytes(dArg)+"\tCastBack:\t"+toDouble(toBytes(dArg)));
	}
}

// /**
// * 已经做过处理，vb客户端可认
// *
// * @param is
// * @throws Throwable
// */
// public static byte[] getBytes(InputStream is) throws IOException {
// //
// ByteBuffer bb = null;
// 创建缓存
// byte[] inData = new byte[1024];
// int count = 0;
// int bytes = -1;
// try {
// // bytes = is.read(inData);
// while ((bytes = is.read(inData)) != -1) {
// ByteBuffer newBuffer = ByteBuffer.allocate(count + bytes);
// if (bb != null) {
// newBuffer.put(bb.array(), 0, count);
// newBuffer.put(inData, 0, bytes);
// } else {
// newBuffer.put(inData, 0, bytes);
// }
// // DebugUtil.debug(">>>");
// bb = newBuffer;
// count += bytes;
// // bytes = is.read(inData);
// }
// return bb.array();
// } catch (IOException ex) {
// throw ex;
// }
// // DebugUtil.debug(">init()=" + this.bb);
// }
