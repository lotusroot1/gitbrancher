package com.alex.gitbrancher.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.alex.gitbrancher.rest.handler.JSONArrayResponseHandler;
import com.alex.gitbrancher.rest.handler.JSONObjectResponseHandler;
import com.alex.gitbrancher.rest.handler.StringOutputResponseHandler;

@Component("requestHelper")
public class RestHelper {

	@Value("${brancher.github.token}")
	private String githubToken;

	public List<Object> getArrayRequest(HttpMethod method, String url, Map<String, Object> paramMap) throws Exception {
		List<Object> result = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpRequestBase httpRequest = getHttpRequest(method, url, paramMap);
			ResponseHandlerResultWrapper<List<Object>> wrapperResponse = httpclient.execute(httpRequest,
					new JSONArrayResponseHandler());
			// if response has link header, follow those until we reach the last
			// page
			HttpResponse response = wrapperResponse.getResponse();
			result = wrapperResponse.getResult();
			/*
			 * Link:
			 * <https://api.github.com/repositories/47607232/branches?page=2>;
			 * rel="next",
			 * <https://api.github.com/repositories/47607232/branches?page=2>;
			 * rel="last"
			 */
			Header[] linkheaders = response.getHeaders("Link");
			for (Header header : linkheaders) {
				String headerLinkValue = header.getValue();
				String[] headerSubValues = headerLinkValue.split(",");
				if (ArrayUtils.isNotEmpty(headerSubValues)) {
					for (String headerSubValue : headerSubValues) {
						if (headerSubValue.indexOf(";") != -1 && headerSubValue.indexOf("next") != -1) {
							String[] subValueArray = headerSubValue.split(";");
							String nextLink = subValueArray[0].trim();
							nextLink = nextLink.substring(1, nextLink.length() - 1);
							// System.out.println("Grabbing next page: " +
							// nextLink);
							result.addAll(getArrayRequest(method, nextLink, paramMap));
						}
					}
				}
			}
		} finally {
			httpclient.close();
		}
		return result;
	}

	public Map<String, Object> getObjectRequest(HttpMethod method, String url, Map<String, Object> paramMap)
			throws Exception {
		Map<String, Object> response = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpRequestBase httpRequest = getHttpRequest(method, url, paramMap);

			ResponseHandlerResultWrapper<Map<String, Object>> responseWrapper = httpclient.execute(httpRequest,
					new JSONObjectResponseHandler());
			response = responseWrapper.getResult();
		} finally {
			httpclient.close();
		}
		return response;
	}

	public RestResponse sendRequest(HttpMethod method, String url) throws Exception {
		return this.sendRequest(method, url, null);
	}

	public RestResponse sendRequest(HttpMethod method, String url, Map<String, Object> paramMap) throws Exception {
		RestResponse response = new RestResponse();
		String responseData = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpRequestBase httpRequest = getHttpRequest(method, url, paramMap);
			// System.out.println("Executing request " +
			// httpRequest.getRequestLine());
			responseData = httpclient.execute(httpRequest, new StringOutputResponseHandler());
			// System.out.println(response);
		} catch (ClientProtocolException cpe) {
			response.setMessage(cpe.getMessage());
		} catch (IOException ioe) {
			response.setMessage(ioe.getMessage());
		} finally {
			httpclient.close();
		}
		response.setResult(responseData);
		return response;
	}

	HttpRequestBase getHttpRequest(HttpMethod method, String url, Map<String, Object> paramMap) {
		if (paramMap == null) {
			paramMap = new HashMap<String, Object>();
		}

		JSONObject jsonObj = new JSONObject(paramMap);
		StringEntity entity = new StringEntity(jsonObj.toString(), ContentType.APPLICATION_JSON);
		HttpRequestBase httpRequest = null;
		switch (method) {
		default:
		case GET:
			httpRequest = new HttpGet(url);
			break;
		case DELETE:
			httpRequest = new HttpDelete(url);
			break;
		case HEAD:
			httpRequest = new HttpHead(url);
			break;
		case OPTIONS:
			httpRequest = new HttpOptions(url);
			break;
		case PATCH:
			httpRequest = new HttpPatch(url);
			break;
		case POST:
			HttpPost request = new HttpPost(url);
			request.setEntity(entity);
			httpRequest = request;
			break;
		case PUT:
			httpRequest = new HttpPut(url);
			break;
		case TRACE:
			httpRequest = new HttpTrace(url);
			break;
		}

		addRequestHeaders(httpRequest);
		return httpRequest;
	}

	private void addRequestHeaders(HttpRequestBase httpRequest) {
		httpRequest.addHeader(HttpHeaders.AUTHORIZATION, "token " + this.githubToken);
		httpRequest.addHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3+json");
	}
}
