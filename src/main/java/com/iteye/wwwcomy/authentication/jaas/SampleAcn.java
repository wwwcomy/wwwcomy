package com.iteye.wwwcomy.authentication.jaas;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

/**
 * <p>
 * This Sample application attempts to authenticate a user and reports whether
 * or not the authentication was successful.
 */
public class SampleAcn {

	/**
	 * Attempt to authenticate the user.
	 *
	 * 注意在启动参数中加入-Djava.security.auth.login.config=file://c:/sample_jaas.config
	 * 这个config参数是被当做一个URL来处理的
	 * ，所以不能使用相对路径，如果要使用的话，需要自己写个javax.security.auth.login
	 * .Configuration类，然后传到LoginContext里，见
	 * 
	 * http://stackoverflow.com/questions/28102350/no-loginmodules-configured-error-when-logincontext-is-created-jaas
	 * 
	 * http://stackoverflow.com/questions/7857416/file-uri-scheme-and-relative-files
	 * <p>
	 * 
	 * @param args
	 *            input arguments for this application. These are ignored.
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) {

		// Obtain a LoginContext, needed for authentication. Tell it
		// to use the LoginModule implementation specified by the
		// entry named "Sample" in the JAAS login configuration
		// file and to also use the specified CallbackHandler.
		LoginContext lc = null;
		try {
			lc = new LoginContext("Sample", new MyCallbackHandler());
		} catch (LoginException le) {
			System.err.println("Cannot create LoginContext. " + le.getMessage());
			System.exit(-1);
		} catch (SecurityException se) {

			se.printStackTrace();
			System.err.println("Cannot create LoginContext. " + se.getMessage());
			System.exit(-1);
		}

		// the user has 3 attempts to authenticate successfully
		int i;
		for (i = 0; i < 3; i++) {
			try {

				// attempt authentication
				lc.login();

				// if we return with no exception, authentication succeeded
				break;

			} catch (LoginException le) {
				System.err.println("Authentication failed:");
				System.err.println("  " + le.getMessage());
				try {
					Thread.currentThread().sleep(3000);
				} catch (Exception e) {
					// ignore
				}

			}
		}

		// did they fail three times?
		if (i == 3) {
			System.out.println("Sorry");
			System.exit(-1);
		}

		System.out.println("Authentication succeeded!");

	}
}
