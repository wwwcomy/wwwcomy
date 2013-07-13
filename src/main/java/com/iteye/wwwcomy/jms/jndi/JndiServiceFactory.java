package com.iteye.wwwcomy.jms.jndi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

public class JndiServiceFactory implements ObjectFactory {

	@Override
	public Object getObjectInstance(Object obj, Name paramName,
			Context paramContext, Hashtable<?, ?> paramHashtable)
			throws Exception {
		if (obj instanceof Reference) {
			JndiService jService = new JndiService();
			Reference ref = (Reference) obj;
			String field = (String) ref.get("field").getContent();
			jService.setProperty(0, field);
			return jService;
		}

		return null;
	}
}
