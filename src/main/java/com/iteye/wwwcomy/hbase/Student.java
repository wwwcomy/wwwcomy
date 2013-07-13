package com.iteye.wwwcomy.hbase;

import java.io.Serializable;

public class Student implements Serializable {
	private int id;
	private String name;
	private boolean gender;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Student Information: name=" + name + "\n";
	}
}
