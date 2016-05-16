package com.foya.apache.http.v5;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.DnsResolver;
import org.apache.hc.client5.http.HttpConnectionFactory;
import org.apache.hc.client5.http.HttpRoute;
import org.apache.hc.client5.http.SystemDefaultDnsResolver;
import org.apache.hc.client5.http.auth.CredentialsProvider;
import org.apache.hc.client5.http.config.AuthSchemes;
import org.apache.hc.client5.http.config.CookieSpecs;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.impl.io.DefaultHttpResponseParserFactory;
import org.apache.hc.client5.http.impl.io.LenientHttpResponseParser;
import org.apache.hc.client5.http.impl.io.ManagedHttpClientConnectionFactory;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.sync.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.sync.CloseableHttpClient;
import org.apache.hc.client5.http.impl.sync.HttpClients;
import org.apache.hc.client5.http.io.ManagedHttpClientConnection;
import org.apache.hc.client5.http.methods.CloseableHttpResponse;
import org.apache.hc.client5.http.methods.HttpGet;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.config.ConnectionConfig;
import org.apache.hc.core5.http.config.MessageConstraints;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.config.SocketConfig;
import org.apache.hc.core5.http.entity.EntityUtils;
import org.apache.hc.core5.http.impl.DefaultHttpResponseFactory;
import org.apache.hc.core5.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.hc.core5.http.io.HttpMessageParser;
import org.apache.hc.core5.http.io.HttpMessageParserFactory;
import org.apache.hc.core5.http.io.HttpMessageWriterFactory;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.BasicLineParser;
import org.apache.hc.core5.http.message.LineParser;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.CharArrayBuffer;

/**
 * This example demonstrates how to customize and configure the most common aspects of HTTP request execution and connection management.
 */
public class ClientConfiguration {

	public final static void main(String[] args) throws Exception {

		// Use custom message parser / writer to customize the way HTTP
		// messages are parsed from and written out to the data stream.
		HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {

			@Override
			public HttpMessageParser<HttpResponse> create(MessageConstraints constraints) {
				LineParser lineParser = new BasicLineParser() {

					@Override
					public Header parseHeader(final CharArrayBuffer buffer) {
						try {
							return super.parseHeader(buffer);
						} catch (ParseException ex) {
							return new BasicHeader(buffer.toString(), null);
						}
					}

				};
				return new LenientHttpResponseParser(lineParser, DefaultHttpResponseFactory.INSTANCE, constraints);
			}

		};
		HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();

		// Use a custom connection factory to customize the process of
		// initialization of outgoing HTTP connections. Beside standard connection
		// configuration parameters HTTP connection factory can define message
		// parser / writer routines to be employed by individual connections.
		HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(requestWriterFactory,
				responseParserFactory);

		// Client HTTP connection objects when fully initialized can be bound to
		// an arbitrary network socket. The process of network socket initialization,
		// its connection to a remote address and binding to a local one is controlled
		// by a connection socket factory.

		// SSL context for secure connections can be created either based on
		// system or application specific properties.
		SSLContext sslcontext = SSLContexts.createSystemDefault();

		// Create a registry of custom connection socket factories for supported
		// protocol schemes.
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", new SSLConnectionSocketFactory(sslcontext)).build();

		// Use custom DNS resolver to override the system DNS resolution.
		DnsResolver dnsResolver = new SystemDefaultDnsResolver() {

			@Override
			public InetAddress[] resolve(final String host) throws UnknownHostException {
				if (host.equalsIgnoreCase("myhost")) {
					return new InetAddress[] { InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 }) };
				} else {
					return super.resolve(host);
				}
			}

		};

		// Create a connection manager with custom configuration.
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connFactory, dnsResolver);

		// Create socket configuration
		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
		// Configure the connection manager to use socket configuration either
		// by default or for a specific host.
		connManager.setDefaultSocketConfig(socketConfig);
		connManager.setSocketConfig(new HttpHost("somehost", 80), socketConfig);
		// Validate connections after 1 sec of inactivity
		connManager.setValidateAfterInactivity(1000);

		// Create message constraints
		MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200).setMaxLineLength(2000).build();
		// Create connection configuration
		ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
				.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(StandardCharsets.UTF_8).setMessageConstraints(messageConstraints)
				.build();
		// Configure the connection manager to use connection configuration either
		// by default or for a specific host.
		connManager.setDefaultConnectionConfig(connectionConfig);
		connManager.setConnectionConfig(new HttpHost("somehost", 80), ConnectionConfig.DEFAULT);

		// Configure total max or per route limits for persistent connections
		// that can be kept in the pool or leased by the connection manager.
		connManager.setMaxTotal(100);
		connManager.setDefaultMaxPerRoute(10);
		connManager.setMaxPerRoute(new HttpRoute(new HttpHost("somehost", 80)), 20);

		// Use custom cookie store if necessary.
		CookieStore cookieStore = new BasicCookieStore();
		// Use custom credentials provider if necessary.
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		// Create global request configuration
		RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();

		// Create an HttpClient with the given custom dependencies and configuration.

		try (CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connManager).setDefaultCookieStore(cookieStore)
				.setDefaultCredentialsProvider(credentialsProvider).setProxy(new HttpHost("myproxy", 8080))
				.setDefaultRequestConfig(defaultRequestConfig).build()) {
			HttpGet httpget = new HttpGet("http://httpbin.org/get");
			// Request configuration can be overridden at the request level.
			// They will take precedence over the one set at the client level.
			RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig).setSocketTimeout(5000).setConnectTimeout(5000)
					.setConnectionRequestTimeout(5000).setProxy(new HttpHost("myotherproxy", 8080)).build();
			httpget.setConfig(requestConfig);

			// Execution context can be customized locally.
			HttpClientContext context = HttpClientContext.create();
			// Contextual attributes set the local context level will take
			// precedence over those set at the client level.
			context.setCookieStore(cookieStore);
			context.setCredentialsProvider(credentialsProvider);

			System.out.println("executing request " + httpget.getURI());
			try (CloseableHttpResponse response = httpclient.execute(httpget, context)) {
				HttpEntity entity = response.getEntity();

				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				System.out.println(EntityUtils.toString(response.getEntity()));

				// Once the request has been executed the local context can
				// be used to examine updated state and various objects affected
				// by the request execution.

				// Last executed request
				context.getRequest();
				// Execution route
				context.getHttpRoute();
				// Auth exchanges
				context.getAuthExchanges();
				// Cookie origin
				context.getCookieOrigin();
				// Cookie spec used
				context.getCookieSpec();
				// User security token
				context.getUserToken();

			}
		}
	}

}
