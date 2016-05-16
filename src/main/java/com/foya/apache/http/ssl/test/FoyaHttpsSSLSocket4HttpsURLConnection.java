package com.foya.apache.http.ssl.test;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class FoyaHttpsSSLSocket4HttpsURLConnection {

	public static String IP = "127.0.0.1";
	public static String PORT = "30008";

	public static void main(String[] args) throws Exception {
		URL url01 = new URL("https://" + IP + ":" + PORT + "");
		runUrl(url01);
		URL url02 = new URL("https://" + IP + ":" + PORT + "/");
		runUrl(url02);
		URL url03 = new URL("https://" + IP + ":" + PORT + "/?name=user");
		runUrl(url03);
		URL url04 = new URL("https://" + IP + ":" + PORT + "/tstar");
		runUrl(url04);
		URL url041 = new URL("https://" + IP + ":" + PORT + "/tstar123");
		runUrl(url041);

		URL url05 = new URL("https://" + IP + ":" + PORT + "/tstar?name=user");
		runUrl(url05);
		URL url06 = new URL("https://" + IP + ":" + PORT + "/foya?name=user");
		runUrl(url06);

	}

	public static void runUrl(URL url) throws Exception {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		HttpsURLConnection.setFollowRedirects(true);

		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setUseCaches(false);
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setInstanceFollowRedirects(true);

		String data = "美國海洋大氣總署公布全球均溫已連11個月偏暖，有人憂心今夏會很熱。氣象專家吳德榮";
		String useragent = "Mozilla/5.0 (Windows NT 5.1; rv:2.0b6) Gecko/20100101 Firefox/4.0b6";

		con.setRequestProperty("User-Agent", useragent);
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Content-length", String.valueOf(data.length()));
		con.setRequestProperty("fileName", "client" + new Date().getTime() + ".txt");

		con.setRequestMethod("POST");
		OutputStreamWriter post = new OutputStreamWriter(con.getOutputStream());
		post.write(data);
		post.flush();
		System.out.println("out");
		//---------------------------

		Reader reader = new InputStreamReader(con.getInputStream());
		while (true) {
			int ch = reader.read();
			if (ch == -1) {
				break;
			}
			System.out.print((char) ch);
		}
	}
}
