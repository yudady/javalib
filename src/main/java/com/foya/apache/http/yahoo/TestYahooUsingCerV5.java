package com.foya.apache.http.yahoo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Iterator;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.hc.client5.http.impl.sync.CloseableHttpClient;
import org.apache.hc.client5.http.impl.sync.HttpClients;
import org.apache.hc.client5.http.methods.CloseableHttpResponse;
import org.apache.hc.client5.http.methods.HttpGet;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.entity.EntityUtils;
import org.junit.Test;

/**
 * This example demonstrates how to create secure connections with a custom SSL context.
 */
public class TestYahooUsingCerV5 {

	String url = "https://www.yahoo.com/";

	File cer = new File(System.getProperty("user.dir") + "/src/main/resources/yahoo.cer");

	@Test
	public void multipart() throws Exception {

		{
			FileInputStream fis = new FileInputStream(cer);
			BufferedInputStream bis = new BufferedInputStream(fis);

			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

			while (bis.available() > 0) {
				Certificate cert = certificateFactory.generateCertificate(bis);
				System.out.println(cert.toString());
			}
		}
		System.out.println("[LOG]--------------------------------");
		{

			FileInputStream fis = new FileInputStream(cer);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			Collection c = cf.generateCertificates(fis);
			Iterator i = c.iterator();
			while (i.hasNext()) {
				Certificate cert = (Certificate) i.next();
				System.out.println(cert);
			}

		}
		System.out.println("[LOG]--------------------------------");

		InputStream is = new FileInputStream(cer);
		// You could get a resource as a stream instead.

		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate caCert = (X509Certificate) cf.generateCertificate(is);

		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(null); // You don't need the KeyStore instance to come from
						// a file.
		ks.setCertificateEntry("", caCert);

		tmf.init(ks);

		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(null, tmf.getTrustManagers(), new java.security.SecureRandom());

		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sc, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.getDefaultHostnameVerifier());
		try (CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build()) {

			HttpGet httpget = new HttpGet(url);

			System.out.println("Executing request " + httpget.getRequestLine());

			try (CloseableHttpResponse response = httpclient.execute(httpget)) {
				HttpEntity entity = response.getEntity();

				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				System.out.println(EntityUtils.toString(response.getEntity()));
			}
		}
	}

}