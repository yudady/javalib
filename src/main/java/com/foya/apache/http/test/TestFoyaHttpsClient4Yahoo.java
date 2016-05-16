
package com.foya.apache.http.test;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.message.BasicHttpRequest;

import com.foya.apache.http.ssl.server.FoyaHttpsClient;

public class TestFoyaHttpsClient4Yahoo {

	public static void main(String[] args) throws Exception {
		FoyaHttpsClient client = new FoyaHttpsClient();
		client.setTimeout(5000);
		DefaultBHttpClientConnection conn = client.createConnection();

		HttpHost host = new HttpHost("www.google.com", 80);
		if (!conn.isOpen()) {
			client.connect(host, conn);
		}
		BasicHttpRequest get = new BasicHttpRequest("GET", "/?");
		HttpResponse response = client.execute(get, host, conn);
		HttpEntity entity = response.getEntity();
		InputStream input = entity.getContent();
		IOUtils.copy(input, System.out);

		if (client.keepAlive(response)) {
			conn.close();
		}

		System.out.println("end");
	}

}
