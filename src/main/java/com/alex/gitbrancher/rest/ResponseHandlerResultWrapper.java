package com.alex.gitbrancher.rest;

import org.apache.http.HttpResponse;

public class ResponseHandlerResultWrapper<T> {
	private HttpResponse response;
	private T result;

	public ResponseHandlerResultWrapper(T result, HttpResponse response) {
		this.result = result;
		this.response = response;
	}

	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
