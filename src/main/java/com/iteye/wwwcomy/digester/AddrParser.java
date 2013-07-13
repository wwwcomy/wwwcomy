package com.iteye.wwwcomy.digester;

import org.apache.commons.digester3.Digester;

public class AddrParser {

	public void addAddr(Address Addr) {
		System.out.println(Addr);
	}

	public static void main(String[] args) throws Exception {
		Digester digester = new Digester();
		digester.setValidating(false);

		// instantiate AddressBookParser class
		digester.addObjectCreate("address-book", AddrParser.class);
		// instantiate Contact class
		digester.addObjectCreate("address-book/contact", Address.class);

		// set type property of Contact instance when 'type' attribute is found
		// 对有属性的值通过setProperties方法

		digester.addSetProperties("address-book/contact", "myType", "type");

		// set different properties of Contact instance using specified methods
		// addCallMethod与addBeanPropertySetter等价
		// 参数 0代表一个参数，默认就是当前读的数据

		digester.addCallMethod("address-book/contact/address", "setAddr", 0);
		digester.addCallMethod("address-book/contact/name", "setName", 0);
		digester.addCallMethod("address-book/contact/city", "setCity", 0);
		digester.addCallMethod("address-book/contact/country", "setCountry", 0);
		// 增加country的属性 : from
		digester.addSetProperties("address-book/contact/country", "short", "countryShort");

		// call 'addContact' method when the next 'address-book/contact' pattern
		// is seen
		digester.addSetNext("address-book/contact", "addAddr");

		// now that rules and actions are configured, start the parsing process
		AddrParser abp = (AddrParser) digester.parse(AddrParser.class.getClassLoader().getResourceAsStream("test/digester/address.xml"));
	}
}
