package com.sidbi.auth;

import java.io.InputStream;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;


public class ActiveDirectoryAuthentication {
	
	private static final String CONTEXT_FACTORY_CLASS = "com.sun.jndi.ldap.LdapCtxFactory";

	private String ldapServerUrls[];

	private int lastLdapUrlIndex;
	
	private final String domainName;

	public ActiveDirectoryAuthentication(String domainName) {
		this.domainName = domainName.toUpperCase();

		try {
			//ldapServerUrls = nsLookup(domainName);
			
			ldapServerUrls = new String[]{"ldap://172.16.100.24:389","ldap://172.16.100.11:389","ldap://172.20.99.11:389"};
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		lastLdapUrlIndex = 0;
	}

	public boolean authenticate(String username, String password) throws LoginException {
		if (ldapServerUrls == null || ldapServerUrls.length == 0) {
			throw new AccountException("Unable to find ldap servers");
		}
		boolean authResult = false;
		
		if (username == null || password == null || username.trim().length() == 0 || password.trim().length() == 0) {
		throw new FailedLoginException("Username or password is empty");
	  }
		
	    if (username == null  || username.trim().length() == 0 ) {
			throw new FailedLoginException("Username or password is empty");
		}
		
		int retryCount = 0;
		int currentLdapUrlIndex = lastLdapUrlIndex;
		do {
			retryCount++;
			InputStream inputStream=null;
			try {
				Hashtable<Object, Object> env = new Hashtable<Object, Object>();
				env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY_CLASS);
				env.put(Context.PROVIDER_URL, ldapServerUrls[currentLdapUrlIndex]);
				String str = username;
				
				 env.put(Context.SECURITY_PRINCIPAL, username + "@" + domainName);
			
			
				env.put(Context.SECURITY_CREDENTIALS, password);
				DirContext ctx = new InitialDirContext(env);
				ctx.close();
				lastLdapUrlIndex = currentLdapUrlIndex;
				return true;
				 
			} 	catch (Exception exp) {
				exp.printStackTrace();
				if (retryCount < ldapServerUrls.length) {
					currentLdapUrlIndex++;
					if (currentLdapUrlIndex == ldapServerUrls.length) {
						currentLdapUrlIndex = 0;
					}
					continue;
				}
				return false;
			} catch (Throwable throwable) {
				throwable.printStackTrace();
				return false;
			}
		} while (true);
	}

}
