package com.iteye.wwwcomy.hbase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TestBytes {
	// public static byte[] changeObjectToBytes(Student student) {
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// DataOutputStream dos = new DataOutputStream(baos);
	// byte[] userBytes = null;
	// try {
	// dos.writeInt(student.getId());
	// dos.writeUTF(student.getName());
	// dos.writeBoolean(student.isGender());
	// dos.close();
	// baos.close();
	// userBytes = baos.toByteArray();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return userBytes;
	// }
	//
	// public static Student changeBytesToObject(byte[] bytes) {
	// ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
	// DataInputStream dis = new DataInputStream(bis);
	// Student s = new Student();
	// try {
	// s.setId(dis.readInt());
	// s.setName(dis.readUTF());
	// s.setGender(dis.readBoolean());
	// dis.close();
	// bis.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return s;
	// }

	public static byte[] changeToBytes(Serializable obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream dos = new ObjectOutputStream(baos);
		byte[] userBytes = null;
		try {
			dos.writeObject(obj);
			dos.close();
			baos.close();
			userBytes = baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userBytes;
	}

	public static Serializable changeToObject(byte[] bytes) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream dis = new ObjectInputStream(bis);
		Serializable s = null;
		try {
			s = (Serializable) dis.readObject();
			dis.close();
			bis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
}
