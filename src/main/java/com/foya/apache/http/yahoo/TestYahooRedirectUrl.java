package com.foya.apache.http.yahoo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestYahooRedirectUrl {

	String url = "http://www.yahoo.com/";

	@Test
	public void multipart() {

		try {

			SSLContextBuilder sslContextBuilder = new SSLContextBuilder();

			sslContextBuilder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			});
			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build(),
					new NoopHostnameVerifier());

			HttpClientBuilder httpClientBuilder = HttpClients.custom();
			httpClientBuilder.setSSLHostnameVerifier(new NoopHostnameVerifier());
			httpClientBuilder.setSSLSocketFactory(sslConnectionSocketFactory);
			// redirect
			httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
			CloseableHttpClient httpClient = httpClientBuilder.build();
			HttpPost httppost = new HttpPost(url);

			MultipartEntityBuilder create = MultipartEntityBuilder.create();

			//-----------------------
			create.setCharset(Charset.forName("UTF-8"));
			create.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			//-----------------------

			HttpEntity reqEntity = create.build();

			httppost.setEntity(reqEntity);

			System.out.println("executing request " + httppost.getRequestLine());

			final HttpClientContext context = HttpClientContext.create();
			final RequestConfig config = RequestConfig.custom().build();
			context.setRequestConfig(config);

			CloseableHttpResponse response = httpClient.execute(httppost, context);

			try {
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					InputStream content = resEntity.getContent();
					IOUtils.copy(content, System.out);

				}
				EntityUtils.consume(resEntity);
			} finally {
				response.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}
}
