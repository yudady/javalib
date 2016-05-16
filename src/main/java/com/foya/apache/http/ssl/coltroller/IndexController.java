package com.foya.apache.http.ssl.coltroller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexController implements HttpRequestHandler {

	private static final Logger mLogger = LoggerFactory.getLogger(IndexController.class);


	@Override
	public void handle(final HttpRequest request, final HttpResponse response, final HttpContext context) throws HttpException, IOException {

		String uri = request.getRequestLine().getUri();
		mLogger.debug("[LOG][IndexController][uri]" + uri);
		if (uri.startsWith("/?")) {
			uri = uri.substring(2);
		}
		try {
			HttpEntity entity = null;
			String contentType = "text/html";

			entity = new EntityTemplate(new ContentProducer() {
				public void writeTo(final OutputStream outstream) throws IOException {
					OutputStreamWriter writer = new OutputStreamWriter(outstream, "UTF-8");
					String resp = "Server is up and running IndexController";

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
