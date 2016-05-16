package com.foya.apache.http.nossl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test2 {

	private static final Logger mLogger = LoggerFactory.getLogger(Test2.class);

	public static void main(String[] args) throws Exception {
		FoyaHttpServer server = new FoyaHttpServer();
		server.setTimeout(5000);

		// Initialize the server-side request handler
		server.registerHandler("*", new HttpRequestHandler() {

			@Override
			public void handle(final HttpRequest request, final HttpResponse response, final HttpContext context) throws HttpException, IOException {

				String uri = request.getRequestLine().getUri();
				mLogger.debug("[LOG][uri]" + uri);
				if (uri.startsWith("/?")) {
					uri = uri.substring(2);
				}
			}
		});

		server.start();
		for (int i = 0; i < 10; i++) {
			Thread.sleep(1000);
			System.out.println(i);
			mLogger.debug("" + i);

			if (i > 2) {
				FoyaHttpClient client = new FoyaHttpClient();
				client.setTimeout(5000);
				DefaultBHttpClientConnection conn = client.createConnection();

				HttpHost host = new HttpHost("localhost", 9999);
				if (!conn.isOpen()) {
					client.connect(host, conn);
				}
				BasicHttpRequest get = new BasicHttpRequest("GET", "/?name=user" + i);
				HttpResponse response = client.execute(get, host, conn);
				HttpEntity entity = response.getEntity();
				InputStream input = entity.getContent();
				IOUtils.copy(input, System.out);
				if (client.keepAlive(response)) {
					conn.close();
				}
			}

		}
		server.shutdown();
		mLogger.debug("end");
	}
}
