package com.foya.apache.http.v5;
import java.net.URL;

import org.apache.hc.client5.http.config.CookieSpecs;
import org.apache.hc.client5.http.cookie.CookieSpecProvider;
import org.apache.hc.client5.http.impl.cookie.RFC6265CookieSpecProvider;
import org.apache.hc.client5.http.impl.sync.CloseableHttpClient;
import org.apache.hc.client5.http.impl.sync.HttpClients;
import org.apache.hc.client5.http.methods.CloseableHttpResponse;
import org.apache.hc.client5.http.methods.HttpGet;
import org.apache.hc.client5.http.psl.PublicSuffixMatcher;
import org.apache.hc.client5.http.psl.PublicSuffixMatcherLoader;
import org.apache.hc.client5.http.ssl.DefaultHostnameVerifier;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.config.Lookup;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.entity.EntityUtils;

/**
 * This example demonstrates how to use a custom public suffix list.
 */
public class ClientCustomPublicSuffixList {

    public final static void main(String[] args) throws Exception {

        // Use PublicSuffixMatcherLoader to load public suffix list from a file,
        // resource or from an arbitrary URL
        PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(
                new URL("https://publicsuffix.org/list/effective_tld_names.dat"));

        // Please use the publicsuffix.org URL to download the list no more than once per day !!!
        // Please consider making a local copy !!!

        DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);

        RFC6265CookieSpecProvider cookieSpecProvider = new RFC6265CookieSpecProvider(publicSuffixMatcher);
        Lookup<CookieSpecProvider> cookieSpecRegistry = RegistryBuilder.<CookieSpecProvider>create()
                .register(CookieSpecs.DEFAULT, cookieSpecProvider)
                .register(CookieSpecs.STANDARD, cookieSpecProvider)
                .register(CookieSpecs.STANDARD_STRICT, cookieSpecProvider)
                .build();

        try (CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLHostnameVerifier(hostnameVerifier)
                .setDefaultCookieSpecRegistry(cookieSpecRegistry)
                .build()) {

            HttpGet httpget = new HttpGet("https://httpbin.org/get");

            System.out.println("executing request " + httpget.getRequestLine());

            try (CloseableHttpResponse response = httpclient.execute(httpget)) {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
        }
    }

}
