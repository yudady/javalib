package com.foya.apache.http.yahoo;

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

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.junit.Test;

public class TestYahoo {

	String url = "https://www.yahoo.com/";

	@Test
	public void multipart() throws CertificateException, UnrecoverableKeyException {
		final char[] JKS_PASSWORD = "tomcat".toCharArray();
		final char[] KEY_PASSWORD = "tomcat".toCharArray();
		try {
			/* Get the JKS contents */
			final KeyStore keyStore = KeyStore.getInstance("JKS");
			try (final InputStream is = new FileInputStream(new File(System.getProperty("user.dir") + "/src/main/resources/tomcat.keystore"))) {
				keyStore.load(is, JKS_PASSWORD);
			}
			final KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(keyStore, KEY_PASSWORD);
			final TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(keyStore);

			/* Creates a socket factory for HttpsURLConnection using JKS contents */
			final SSLContext sc = SSLContext.getInstance("TLS");
			//sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new java.security.SecureRandom());

			sc.init(null, null, new java.security.SecureRandom());
			SSLSocketFactory socketFactory = sc.getSocketFactory();

			HttpsURLConnection.setDefaultSSLSocketFactory(socketFactory);

			//----------------------------------------------------
			URL urlconn = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) urlconn.openConnection();
			con.setSSLSocketFactory(socketFactory);
			con.setInstanceFollowRedirects(true);

			//dumpl all cert info
			print_https_cert(con);

			//dump all the content
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
