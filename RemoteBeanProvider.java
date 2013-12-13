package com.sovzond.idspace.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Класс - удаленный поставщик бинов - умеет доставать бины с сервера по JNDI
 * 
 * @author pavel-c
 * 
 */
public class RemoteBeanProvider {
	public Context getContext() throws NamingException {
		Context ret = null;
		Properties props = new Properties();
		props.put("java.naming.factory.initial",
				"org.jboss.naming.remote.client.InitialContextFactory");
		props.put(Context.PROVIDER_URL, "remote://localhost:4447");
		props.put(Context.SECURITY_PRINCIPAL, "login");
		props.put(Context.SECURITY_CREDENTIALS, "password");
		props.put("java.naming.factory.url.pkgs",
				"org.jboss.naming:org.jnp.interfaces");
		props.put("jboss.naming.client.ejb.context", true);
		return (Context) new InitialContext(props);
	}

	public Object getBean(String jndiName) {
		
		try {
			Context ctx = getContext();
			Object bean;
			bean = ctx.lookup(jndiName);
			return bean;
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
