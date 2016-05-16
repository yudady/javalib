package com.foya.apache.http.ssl.coltroller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstarController implements HttpRequestHandler {

	private static final Logger mLogger = LoggerFactory.getLogger(TstarController.class);

	@Override
	public void handle(final HttpRequest request, final HttpResponse response, final HttpContext context)
			throws HttpException, IOException {
		HttpEntityEnclosingRequest httpEntityEnclosingRequest = (HttpEntityEnclosingRequest) request;
		String fileName = "server" + new Date().getTime() + ".txt";

		Header header = request.getFirstHeader("fileName");
		if(header != null){
			fileName = header.getValue();
		}

		HttpEntity reqEntity = httpEntityEnclosingRequest.getEntity();

		InputStream input = reqEntity.getContent();

		mLogger.debug("[LOG][-----------------------------------]");
		IOUtils.copy(input, new FileOutputStream(new File("C:/Users/tommy/Desktop/" + fileName)));
		mLogger.debug("");
		mLogger.debug("[LOG][-----------------------------------]");

		try {
			HttpEntity entity = null;
			String contentType = "text/html";

			entity = new EntityTemplate(new ContentProducer() {
				public void writeTo(final OutputStream outstream) throws IOException {
					OutputStreamWriter writer = new OutputStreamWriter(outstream, "UTF-8");
					String resp = "Server is up and running TstarController ";

					writer.write(resp);
					writer.flush();
				}
			});

			((EntityTemplate) entity).setContentType(contentType);

			response.setEntity(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
