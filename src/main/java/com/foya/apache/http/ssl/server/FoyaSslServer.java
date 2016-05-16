/* ==================================================================== Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. ====================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the Apache Software Foundation. For more information on the Apache Software Foundation, please see <http://www.apache.org/>. */

package com.foya.apache.http.ssl.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import org.apache.commons.lang3.ClassPathUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.ConnectionClosedException;
import org.apache.http.ExceptionLogger;
import org.apache.http.HttpConnectionFactory;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.bootstrap.SSLServerSetupHandler;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.HttpExpectationVerifier;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.UriHttpRequestHandlerMapper;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foya.apache.http.core.LoggingBHttpServerConnection;

public class FoyaSslServer {

	private static final Logger mLogger = LoggerFactory.getLogger(FoyaSslServer.class);

	private final UriHttpRequestHandlerMapper reqistry;
	private volatile HttpExpectationVerifier expectationVerifier;
	private volatile int timeout;
	private int port;
	private String ip;

	private volatile org.apache.http.impl.bootstrap.HttpServer server;

	public FoyaSslServer(String ip, int port) throws IOException {
		super();
		this.ip = ip;
		this.port = port;
		this.reqistry = new UriHttpRequestHandlerMapper();
	}

	public int getTimeout() {
		return this.timeout;
	}

	public void setTimeout(final int timeout) {
		this.timeout = timeout;
	}

	public void registerHandler(final String pattern, final HttpRequestHandler handler) {
		this.reqistry.register(pattern, handler);
	}

	public void setExpectationVerifier(final HttpExpectationVerifier expectationVerifier) {
		this.expectationVerifier = expectationVerifier;
	}

	public int getPort() {
		final org.apache.http.impl.bootstrap.HttpServer local = this.server;
		if (local != null) {
			return this.server.getLocalPort();
		} else {
			throw new IllegalStateException("Server not running");
		}
	}

	public InetAddress getInetAddress() {
		final org.apache.http.impl.bootstrap.HttpServer local = this.server;
		if (local != null) {
			return local.getInetAddress();
		} else {
			throw new IllegalStateException("Server not running");
		}
	}

	public void start()
			throws IOException, KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException {
		Asserts.check(this.server == null, "Server already running");

		String keystore = ClassPathUtils.toFullyQualifiedPath(FoyaSslServer.class, "test.keystore");
		mLogger.debug("[LOG][keystore]" + keystore);
		final URL resource1 = getClass().getResource("/" + keystore);
		final String storePassword = "nopassword";
		final String keyPassword = "nopassword";
		final SSLContext sslContext = SSLContextBuilder.create().loadKeyMaterial(resource1, storePassword.toCharArray(), keyPassword.toCharArray())
				.build();
		final SSLServerSocketFactory serverSocketFactory = sslContext.getServerSocketFactory();

		final SSLServerSetupHandler sSLServerSetupHandler = new SSLServerSetupHandler() {
			@Override
			public void initialize(SSLServerSocket socket) throws SSLException {
				socket.setNeedClientAuth(true);
				socket.setWantClientAuth(true);
			}

		};

		this.server = ServerBootstrap.bootstrap().setListenerPort(port).setLocalAddress(InetAddress.getByName(ip)).setSslContext(sslContext)
				.setServerSocketFactory(serverSocketFactory).setSslSetupHandler(sSLServerSetupHandler)

				.setSocketConfig(SocketConfig.custom().setSoTimeout(this.timeout).build()).setServerInfo("FOYA-SERVER/1.1")
				.setConnectionFactory(new LoggingConnFactory()).setExceptionLogger(new SimpleExceptionLogger())
				.setExpectationVerifier(this.expectationVerifier).setHandlerMapper(this.reqistry).create();
		this.server.start();
	}

	public void shutdown() {
		final org.apache.http.impl.bootstrap.HttpServer local = this.server;
		this.server = null;
		if (local != null) {
			local.shutdown(5, TimeUnit.SECONDS);
		}
	}

	static class LoggingConnFactory implements HttpConnectionFactory<LoggingBHttpServerConnection> {

		@Override
		public LoggingBHttpServerConnection createConnection(final Socket socket) throws IOException {
			final LoggingBHttpServerConnection conn = new LoggingBHttpServerConnection(8 * 1024);
			conn.bind(socket);
			return conn;
		}
	}

	static class SimpleExceptionLogger implements ExceptionLogger {

		private final Log log = LogFactory.getLog(FoyaSslServer.class);

		@Override
		public void log(final Exception ex) {
			if (ex instanceof ConnectionClosedException) {
				this.log.debug(ex.getMessage());
			} else if (ex instanceof SocketException) {
				this.log.debug(ex.getMessage());
			} else {
				this.log.error(ex.getMessage(), ex);
			}
		}
	}

}
