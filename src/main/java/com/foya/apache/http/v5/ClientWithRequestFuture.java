package com.foya.apache.http.v5;
import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.impl.sync.CloseableHttpClient;
import org.apache.hc.client5.http.impl.sync.FutureRequestExecutionService;
import org.apache.hc.client5.http.impl.sync.HttpClientBuilder;
import org.apache.hc.client5.http.impl.sync.HttpRequestFutureTask;
import org.apache.hc.client5.http.methods.HttpGet;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.client5.http.sync.ResponseHandler;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;

public class ClientWithRequestFuture {

    public static void main(String[] args) throws Exception {
        // the simplest way to create a HttpAsyncClientWithFuture
        CloseableHttpClient httpclient = HttpClientBuilder.create()
                .setMaxConnPerRoute(5)
                .setMaxConnTotal(5).build();
        ExecutorService execService = Executors.newFixedThreadPool(5);
        try (FutureRequestExecutionService requestExecService = new FutureRequestExecutionService(
                httpclient, execService)) {
            // Because things are asynchronous, you must provide a ResponseHandler
            ResponseHandler<Boolean> handler = new ResponseHandler<Boolean>() {
                @Override
                public Boolean handleResponse(HttpResponse response) throws IOException {
                    // simply return true if the status was OK
                    return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
                }
            };

            // Simple request ...
            HttpGet request1 = new HttpGet("http://httpbin.org/get");
            HttpRequestFutureTask<Boolean> futureTask1 = requestExecService.execute(request1,
                    HttpClientContext.create(), handler);
            Boolean wasItOk1 = futureTask1.get();
            System.out.println("It was ok? " + wasItOk1);

            // Cancel a request
            try {
                HttpGet request2 = new HttpGet("http://httpbin.org/get");
                HttpRequestFutureTask<Boolean> futureTask2 = requestExecService.execute(request2,
                        HttpClientContext.create(), handler);
                futureTask2.cancel(true);
                Boolean wasItOk2 = futureTask2.get();
                System.out.println("It was cancelled so it should never print this: " + wasItOk2);
            } catch (CancellationException e) {
                System.out.println("We cancelled it, so this is expected");
            }

            // Request with a timeout
            HttpGet request3 = new HttpGet("http://httpbin.org/get");
            HttpRequestFutureTask<Boolean> futureTask3 = requestExecService.execute(request3,
                    HttpClientContext.create(), handler);
            Boolean wasItOk3 = futureTask3.get(10, TimeUnit.SECONDS);
            System.out.println("It was ok? " + wasItOk3);

            FutureCallback<Boolean> callback = new FutureCallback<Boolean>() {
                @Override
                public void completed(Boolean result) {
                    System.out.println("completed with " + result);
                }

                @Override
                public void failed(Exception ex) {
                    System.out.println("failed with " + ex.getMessage());
                }

                @Override
                public void cancelled() {
                    System.out.println("cancelled");
                }
            };

            // Simple request with a callback
            HttpGet request4 = new HttpGet("http://httpbin.org/get");
            // using a null HttpContext here since it is optional
            // the callback will be called when the task completes, fails, or is cancelled
            HttpRequestFutureTask<Boolean> futureTask4 = requestExecService.execute(request4,
                    HttpClientContext.create(), handler, callback);
            Boolean wasItOk4 = futureTask4.get(10, TimeUnit.SECONDS);
            System.out.println("It was ok? " + wasItOk4);
        }
    }
}