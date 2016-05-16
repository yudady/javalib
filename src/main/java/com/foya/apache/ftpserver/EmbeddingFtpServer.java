package com.foya.apache.ftpserver;

import java.io.File;

import org.apache.ftpserver.ConnectionConfigFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.SaltedPasswordEncryptor;
import org.apache.ftpserver.usermanager.UserFactory;

public class EmbeddingFtpServer {
	public static void main(String[] args) throws Exception {

		FtpServerFactory serverFactory = new FtpServerFactory();

		ConnectionConfigFactory connectionConfigFactory = new ConnectionConfigFactory();
		connectionConfigFactory.setMaxThreads(50);
		connectionConfigFactory.setMaxLogins(50);
		connectionConfigFactory.setAnonymousLoginEnabled(true);
		serverFactory.setConnectionConfig(connectionConfigFactory.createConnectionConfig());


		ListenerFactory factory = new ListenerFactory();
		// set the port of the listener
		factory.setPort(2221);
		// replace the default listener
		serverFactory.addListener("default", factory.createListener());

		//---------------------------------
		// define SSL configuration
		SslConfigurationFactory ssl = new SslConfigurationFactory();
		ssl.setKeystoreFile(new File("src/main/resources/ftpserver/ftpserver.jks"));


		ssl.setKeystorePassword("password");
		// set the SSL configuration for the listener
		factory.setSslConfiguration(ssl.createSslConfiguration());
		factory.setImplicitSsl(true);

		//---------------------------------
		System.out.println("Adding Users Now");
		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
		userManagerFactory.setFile(new File("ftpserver/users.properties"));
		UserManager userManagement = userManagerFactory.createUserManager();

		//---------------------------------
		userManagerFactory.setPasswordEncryptor(new SaltedPasswordEncryptor());
		UserFactory userFact = new UserFactory();
		userFact.setName("tomcat");
		userFact.setPassword("tomcat");
		userFact.setHomeDirectory("F:/");
		User user = userFact.createUser();
		userManagement.save(user);
		serverFactory.setUserManager(userManagement);
		//--------------------------------

		String[] allUserNames = userManagement.getAllUserNames();
		for (String userName : allUserNames) {
			User u = userManagement.getUserByName(userName);
			System.out.println(u.getName() + "   " + u.getPassword() + "  " + u.getAuthorities());

		}

		// start the server
		FtpServer server = serverFactory.createServer();

		System.out.println("Server Starting Port " + factory.getPort());
		server.start();

	}
}