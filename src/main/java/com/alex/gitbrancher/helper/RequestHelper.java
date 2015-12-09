package com.alex.gitbrancher.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component("requestHelper")
public class RequestHelper {
	public static final String BASE_URL = "https://api.github.com";

	public List<JSONObject> getArrayRequest(HttpMethod method, String url) throws Exception {
		List<JSONObject> response = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
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
				httpRequest = new HttpPost(url);
				break;
			case PUT:
				httpRequest = new HttpPut(url);
				break;
			case TRACE:
				httpRequest = new HttpTrace(url);
				break;
			}

			httpRequest.addHeader("Authorization", "token " + getToken());
			response = httpclient.execute(httpRequest, new JSONArrayResponseHandler());
		} finally {
			httpclient.close();
		}
		return response;
	}

	public String sendRequest(HttpMethod method, String url) throws Exception {
		return this.sendRequest(method, url, null);
	}

	public String sendRequest(HttpMethod method, String url, Map<String, Object> paramMap) throws Exception {

		String response = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
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

			httpRequest.addHeader("Authorization", "token " + getToken());
			System.out.println("Executing request " + httpRequest.getRequestLine());
			response = httpclient.execute(httpRequest, new StringOutputResponseHandler());
			System.out.println(response);
		} finally {
			httpclient.close();
		}
		return response;
	}

	public String getToken() {
		return "someTokenHere";
	}
}
