package com.iteye.wwwcomy.designpattern.factory.abstractfactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ClassPathApplicationContext implements BeanFactory {

	private String fileName;

	Map<String, String> map = new HashMap<String, String>();

	private String id;

	private String className;

	public static void main(String[] args) throws IOException {
		ClassPathApplicationContext c = new ClassPathApplicationContext(
				"applicationContext.xml");
		BufferedReader br = new BufferedReader(new InputStreamReader(c
				.getClass().getClassLoader()
				.getResourceAsStream("applicationContext.xml")));
		String x;
		while ((x = br.readLine()) != null)
			System.out.println(x);
		c.getBean("v");
	}

	public ClassPathApplicationContext(String fileName) {
		this.fileName = fileName;
		init();
	}

	public void init() {
		SAXReader sr = new SAXReader();
		try {
			Document document = sr.read(this.getClass().getClassLoader()
					.getResourceAsStream(this.fileName));
			List l = document.selectNodes("/beans/bean");
			Iterator j = l.iterator();
			while (j.hasNext()) {
				Element n = (Element) j.next();
				this.id = n.attributeValue("id");
				this.className = n.attributeValue("class");
				map.put(id, className);
			}
			Iterator i = document.getRootElement().elementIterator();
			// while (i.hasNext()) {
			// Element n = (Element) i.next();
			// if (n.getName().equalsIgnoreCase("bean")) {
			// this.id = n.attributeValue("id");
			// this.className = n.attributeValue("class");
			// return;
			// }
			// }
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getBean(String id) {
		Iterator<String> i = map.keySet().iterator();
		while (i.hasNext()) {
			String key = i.next();
			if (id.equals(key)) {
				Object o = null;
				try {
					o = Class.forName(map.get(key)).newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return o;
			}
		}
		return null;
	}

}
