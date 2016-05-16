package com.foya.apache.http.v5;
import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.sync.CloseableHttpClient;
import org.apache.hc.client5.http.impl.sync.HttpClients;
import org.apache.hc.client5.http.methods.CloseableHttpResponse;
import org.apache.hc.client5.http.methods.HttpGet;
import org.apache.hc.core5.http.entity.EntityUtils;
import org.apache.hc.core5.pool.PoolStats;

/**
 * Example demonstrating how to evict expired and idle connections
 * from the connection pool.
 */
public class ClientEvictExpiredConnections {

    public static void main(String[] args) throws Exception {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        try (CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(cm)
                .evictExpiredConnections()
                .evictIdleConnections(5L, TimeUnit.SECONDS)
                .build()) {
            // create an array of URIs to perform GETs on
            String[] urisToGet = {
                    "http://hc.apache.org/",
                    "http://hc.apache.org/httpcomponents-core-ga/",
                    "http://hc.apache.org/httpcomponents-client-ga/",
            };

            for (int i = 0; i < urisToGet.length; i++) {
                String requestURI = urisToGet[i];
                HttpGet request = new HttpGet(requestURI);

                System.out.println("Executing request " + requestURI);

                try (CloseableHttpResponse response = httpclient.execute(request)) {
                    System.out.println("----------------------------------------");
                    System.out.println(response.getStatusLine());
                    EntityUtils.consume(response.getEntity());
                }
            }

            PoolStats stats1 = cm.getTotalStats();
            System.out.println("Connections kept alive: " + stats1.getAvailable());

            // Sleep 10 sec and let the connection evictor do its job
            Thread.sleep(10000);

            PoolStats stats2 = cm.getTotalStats();
            System.out.println("Connections kept alive: " + stats2.getAvailable());

        }
    }

}
