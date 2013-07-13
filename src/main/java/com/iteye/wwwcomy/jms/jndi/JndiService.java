package com.iteye.wwwcomy.jms.jndi;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;

public class JndiService implements Referenceable, ITest {
	String field;

	@Override
	public Reference getReference() throws NamingException {
		Reference ref = new Reference(getClass().getName(),
				JndiServiceFactory.class.getName(), null);
		ref.add(new StringRefAddr("field", field));
		return ref;
	}

	@Override
	public String test1() {
		System.out.println("调用Test1");
		return "调用test1";
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setProperty(int index, String property) {
		field = property;
	}
}
