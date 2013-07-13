package com.iteye.wwwcomy.json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONObjectSample {

	// ����JSONObject����
	private static JSONObject createJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", "kevin");
		jsonObject.put("Max.score", 100);
		jsonObject.put("Min.score", new Integer(50));
		jsonObject.put("nickname", "picglet");
		return jsonObject;
	}

	public static void main(String[] args) {
		JSONObject jsonObject = JSONObjectSample.createJSONObject();
		// ���jsonobject����
		System.out.println("jsonObject==>" + jsonObject);

		// �ж�������������
		boolean isArray = jsonObject.isArray();
		boolean isEmpty = jsonObject.isEmpty();
		boolean isNullObject = jsonObject.isNullObject();
		System.out.println("isArray:" + isArray + " isEmpty:" + isEmpty
				+ " isNullObject:" + isNullObject);

		// �������
		jsonObject.element("address", "swap lake");
		System.out.println("������Ժ�Ķ���==>" + jsonObject);

		// ����һ��JSONArray����
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(0, "this is a jsonArray value");
		jsonArray.add(1, "another jsonArray value");
		jsonObject.element("jsonArray", jsonArray);
		JSONArray array = jsonObject.getJSONArray("jsonArray");
		System.out.println("����һ��JSONArray����" + array);
		// ���JSONArray���ֵ
		// {"name":"kevin","Max.score":100,"Min.score":50,"nickname":"picglet","address":"swap
		// lake",
		// "jsonArray":["this is a jsonArray value","another jsonArray value"]}
		System.out.println(jsonObject);

		// ���key����һ���ַ�
		String jsonString = jsonObject.getString("name");
		System.out.println("jsonString==>" + jsonString);
	}
}
