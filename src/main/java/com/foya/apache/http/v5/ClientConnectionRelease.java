package com.foya.apache.http.v5;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hc.client5.http.impl.sync.CloseableHttpClient;
import org.apache.hc.client5.http.impl.sync.HttpClients;
import org.apache.hc.client5.http.methods.CloseableHttpResponse;
import org.apache.hc.client5.http.methods.HttpGet;
import org.apache.hc.core5.http.HttpEntity;

/**
 * This example demonstrates the recommended way of using API to make sure the underlying connection gets released back to the connection manager.
 */
public class ClientConnectionRelease {

	public final static void main(String[] args) throws Exception {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpGet httpget = new HttpGet("http://httpbin.org/get");

			System.out.println("Executing request " + httpget.getRequestLine());
			try (CloseableHttpResponse response = httpclient.execute(httpget)) {
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());

				// Get hold of the response entity
				HttpEntity entity = response.getEntity();

				// If the response does not enclose an entity, there is no need
				// to bother about connection release
				if (entity != null) {
					try (InputStream instream = entity.getContent()) {
						instream.read();
						// do something useful with the response
					} catch (IOException ex) {
						// In case of an IOException the connection will be released
						// back to the connection manager automatically
						throw ex;
					}
				}
			}
		}
	}

}
