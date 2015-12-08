package com.alex.gitbrancher.model.ajax;

public class AjaxResponse {
	private String status;
	private Object result;

	public AjaxResponse() {
		this(null, null);
	}

	public AjaxResponse(Object result) {
		this(result, "");
	}

	public AjaxResponse(Object result, String status) {
		this.result = result;
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
