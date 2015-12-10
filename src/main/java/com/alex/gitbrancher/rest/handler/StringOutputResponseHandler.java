package com.alex.gitbrancher.rest.handler;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

public class StringOutputResponseHandler implements ResponseHandler<String> {

	@Override
	public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		String output = null;
		int status = response.getStatusLine().getStatusCode();

		Header[] headers = response.getAllHeaders();
		StringBuilder sb = new StringBuilder("\nHeaders:\n");
		for (Header header : headers) {
			sb.append("\t" + header.getName() + ": " + header.getValue() + "\n");
		}
		System.out.println(sb.toString());
		if (status >= 200 && status < 300) {
			HttpEntity entity = response.getEntity();
			output = entity != null ? EntityUtils.toString(entity) : null;
		} else {
			throw new ClientProtocolException("Unexpected response status: " + status);
		}
		return output;
	}

}
