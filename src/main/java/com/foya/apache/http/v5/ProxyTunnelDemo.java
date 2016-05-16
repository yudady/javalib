package com.foya.apache.http.v5;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.sync.ProxyClient;
import org.apache.hc.core5.http.HttpHost;

/**
 * Example code for using {@link ProxyClient} in order to establish a tunnel through an HTTP proxy.
 */
public class ProxyTunnelDemo {

    public final static void main(String[] args) throws Exception {

        ProxyClient proxyClient = new ProxyClient();
        HttpHost target = new HttpHost("www.yahoo.com", 80);
        HttpHost proxy = new HttpHost("localhost", 8888);
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("user", "pwd".toCharArray());
        try (Socket socket = proxyClient.tunnel(proxy, target, credentials)) {
            Writer out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.ISO_8859_1);
            out.write("GET / HTTP/1.1\r\n");
            out.write("Host: " + target.toHostString() + "\r\n");
            out.write("Agent: whatever\r\n");
            out.write("Connection: close\r\n");
            out.write("\r\n");
            out.flush();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.ISO_8859_1));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

}

