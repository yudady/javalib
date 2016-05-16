package com.foya.apache.http.v5;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.BasicScheme;
import org.apache.hc.client5.http.impl.sync.CloseableHttpClient;
import org.apache.hc.client5.http.impl.sync.HttpClients;
import org.apache.hc.client5.http.methods.CloseableHttpResponse;
import org.apache.hc.client5.http.methods.HttpGet;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.entity.EntityUtils;

/**
 * An example of HttpClient can be customized to authenticate
 * preemptively using BASIC scheme.
 * <b>
 * Generally, preemptive authentication can be considered less
 * secure than a response to an authentication challenge
 * and therefore discouraged.
 */
public class ClientPreemptiveBasicAuthentication {

    public static void main(String[] args) throws Exception {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            // Generate BASIC scheme object and add it to the local auth cache
            BasicScheme basicAuth = new BasicScheme();
            basicAuth.initPreemptive(new UsernamePasswordCredentials("user", "passwd".toCharArray()));

            HttpHost target = new HttpHost("httpbin.org", 80, "http");

            // Add AuthCache to the execution context
            HttpClientContext localContext = HttpClientContext.create();
            localContext.resetAuthExchange(target, basicAuth);

            HttpGet httpget = new HttpGet("http://httpbin.org/hidden-basic-auth/user/passwd");

            System.out.println("Executing request " + httpget.getRequestLine() + " to target " + target);
            for (int i = 0; i < 3; i++) {
                try (CloseableHttpResponse response = httpclient.execute(httpget, localContext)) {
                    System.out.println("----------------------------------------");
                    System.out.println(response.getStatusLine());
                    System.out.println(EntityUtils.toString(response.getEntity()));
                }
            }
        }
    }

}
