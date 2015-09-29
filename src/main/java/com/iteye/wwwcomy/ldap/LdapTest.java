package com.iteye.wwwcomy.ldap;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author xingnan.liu
 *
 */
public class LdapTest {
	@Test
	public void canAdd() throws Exception {
		LdapContext ctx = LdapHelper.getCtx();
		LdapContext newCtx = ctx.newInstance(null);
		Attributes attrs = new BasicAttributes(true);
		Attribute objclass = new BasicAttribute("objectclass");
		// 添加ObjectClass
		String[] attrObjectClassPerson = { "organizationalPerson",
				"inetOrgPerson", "person", "top" };
		Arrays.sort(attrObjectClassPerson);
		for (String ocp : attrObjectClassPerson) {
			objclass.add(ocp);
		}

		attrs.put(objclass);
		String uid = "zhangsan2";
		// 这里加上root会报错
		String userDN = "uid=" + uid + "," + "ou=users";
		// 密码处理
		// attrs.put("uid", uid);
		attrs.put("cn", uid);
		attrs.put("sn", uid);
		attrs.put("uid", uid);
		attrs.put("displayName", "张三2");
		attrs.put("mail", "zhangsan@163.com");
		// attrs.put("description", ""); // 如果设置属性为空貌似会报错
		attrs.put("userPassword", "12345".getBytes("UTF-8"));
		newCtx.createSubcontext(userDN, attrs);
		newCtx.close();
	}

	@Test
	public void canDel() throws Exception {
		LdapContext ctx = LdapHelper.getCtx();
		LdapContext newCtx = ctx.newInstance(null);
		String uid = "zhangsan";
		String userDN = "uid=" + uid + "," + "o=org2";
		newCtx.destroySubcontext(userDN);
		newCtx.close();
	}

	@Test
	public void canModifyAttr() throws Exception {
		LdapContext ctx = LdapHelper.getCtx();
		LdapContext newCtx = ctx.newInstance(null);
		String uid = "zhangsan";
		String userDN = "uid=" + uid + "," + "o=org2";
		Attributes attrs = new BasicAttributes(true);
		attrs.put("mail", "zhangsanModify@163.com");
		// 使用 DirContext.REMOVE_ATTRIBUTE 或ADD_ATTRIBUTE 来添加、删除Attribute
		// 可以使用ModificationItem的数组来传递attributes
		// 可以通过这种方式来修改密码
		newCtx.modifyAttributes(userDN, DirContext.REPLACE_ATTRIBUTE, attrs);
		newCtx.close();
	}

	@Test
	public void canSearch() throws Exception {
		LdapContext ctx = LdapHelper.getCtx();
		LdapContext newCtx = ctx.newInstance(null);
		// 设置过滤条件
		String uid = "zhangsan";
		String filter = "(&(objectClass=top)(objectClass=organizationalPerson)(uid="
				+ uid + "))";
		filter = "objectClass=top";

		// 限制要查询的字段内容
		String[] attrPersonArray = { "uid", "userPassword", "displayName",
				"cn", "sn", "mail" };
		SearchControls searchControls = new SearchControls();

		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		searchControls.setSearchScope(SearchControls.OBJECT_SCOPE);
		// 设置将被返回的Attribute, 可以不设置
		searchControls.setReturningAttributes(attrPersonArray);
		// 三个参数分别为：
		// 上下文；
		// 要搜索的属性，如果为空或 null，则返回目标上下文中的所有对象；
		// 控制搜索的搜索控件，如果为 null，则使用默认的搜索控件
		NamingEnumeration<SearchResult> searchResults = newCtx.search("o=org2",
				filter, searchControls);
		// 输出查到的数据
		while (searchResults.hasMore()) {
			SearchResult result = searchResults.next();
			System.out.println(result.getName() + "'s attribute:");
			NamingEnumeration<? extends Attribute> attrs = result
					.getAttributes().getAll();
			while (attrs.hasMore()) {
				Attribute attr = attrs.next();
				System.out.println(attr.getID() + "=" + attr.get());
			}
			System.out.println("============");
		}
	}

	@Test
	public void canAuthenticate() {
		String usr = "zsorg1";
		String pwd = "1";
		LdapContext ctx = null;
		try {
			ctx = LdapHelper.getCtx();
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			// constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
			NamingEnumeration<SearchResult> en = ctx.search("", "cn=" + usr,
					constraints); //
			// 查询所有用户
			while (en != null && en.hasMoreElements()) {
				SearchResult si = en.nextElement();
				System.out.println("name:   " + si.getName());
				Attributes attrs = si.getAttributes();
				if (attrs == null) {
					System.out.println("No   attributes");
				} else {
					Attribute attr = attrs.get("userPassword");
					Object o = attr.get();
					byte[] s = (byte[]) o;
					String pwd2 = new String(s);
					boolean result = LdapHelper.verifySHA(pwd2, pwd);
					Assert.assertTrue(result);
					return;
				}
				Assert.assertTrue(false);
			}
			ctx.close();
		} catch (NoSuchAlgorithmException ex) {
			try {
				if (ctx != null) {
					ctx.close();
				}
			} catch (NamingException namingException) {
				namingException.printStackTrace();
			}
		} catch (NamingException ex) {
			try {
				if (ctx != null) {
					ctx.close();
				}
			} catch (NamingException namingException) {
				namingException.printStackTrace();
			}
		}
		return;
	}
}
