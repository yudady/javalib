
package com.foya.apache.http.ssl.test;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.message.BasicHttpRequest;

import com.foya.apache.http.ssl.server.FoyaHttpsClient;

public class TestFoyaHttpsClient {

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 100; i++) {
			FoyaHttpsClient client = new FoyaHttpsClient();
			HttpResponse response = runClient(client);
			while (!client.keepAlive(response)) {
				break;
			}
		}
	}

	public static HttpResponse runClient(FoyaHttpsClient client) throws Exception {
		HttpResponse response = null;
		client.setTimeout(5000);
		DefaultBHttpClientConnection conn = client.createConnection();

		HttpHost host = new HttpHost("127.0.0.1", 9999);
		if (!conn.isOpen()) {
			client.connect(host, conn);
		}
		BasicHttpRequest get = new BasicHttpRequest("GET", "/?name=user");

		response = client.execute(get, host, conn);
		StatusLine statusLine = response.getStatusLine();
		while (statusLine.getStatusCode() == 200) {
			System.out.println(statusLine.getStatusCode());
			HttpEntity entity = response.getEntity();
			InputStream input = entity.getContent();
			IOUtils.copy(input, System.out);
			break;
		}
		if (client.keepAlive(response)) {
			conn.close();
		}
		return response;

	}

}
