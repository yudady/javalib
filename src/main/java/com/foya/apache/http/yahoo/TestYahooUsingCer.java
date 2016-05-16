package com.foya.apache.http.yahoo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.junit.Test;

public class TestYahooUsingCer {

	String url = "https://www.yahoo.com/";

	@SuppressWarnings("rawtypes")
	@Test
	public void multipart() throws CertificateException, UnrecoverableKeyException {
		try {
			File cer = new File(System.getProperty("user.dir") + "/src/main/resources/yahoo.cer");

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

			SSLSocketFactory socketFactory = sc.getSocketFactory();

			HttpsURLConnection.setDefaultSSLSocketFactory(socketFactory);

			// ----------------------------------------------------
			URL urlconn = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) urlconn.openConnection();
			con.setSSLSocketFactory(socketFactory);
			con.setInstanceFollowRedirects(true);

			// dumpl all cert info
			print_https_cert(con);

			// dump all the content
			print_content(con);

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

	private void print_https_cert(HttpsURLConnection con) {

		if (con != null) {

			try {

				System.out.println("Response Code : " + con.getResponseCode());
				System.out.println("Cipher Suite : " + con.getCipherSuite());
				System.out.println("\n");

				Certificate[] certs = con.getServerCertificates();
				for (Certificate cert : certs) {
					System.out.println("Cert Type : " + cert.getType());
					System.out.println("Cert Hash Code : " + cert.hashCode());
					System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
					System.out.println("Cert Public Key Format : " + cert.getPublicKey().getFormat());
					System.out.println("\n");
				}

			} catch (SSLPeerUnverifiedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private void print_content(HttpsURLConnection con) {
		if (con != null) {

			try {

				System.out.println("****** Content of the URL ********");
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

				String input;

				while ((input = br.readLine()) != null) {
					System.out.println(input);
				}
				br.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
