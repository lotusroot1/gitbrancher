package com.alex.gitbrancher.ajax.model;

public class AjaxResponse {
	private AjaxStatus status;
	private Object result;

	public AjaxResponse() {
		this(null);
	}

	public AjaxResponse(Object result) {
		this(result, AjaxStatus.SUCCESS);
	}

	public AjaxResponse(Object result, AjaxStatus status) {
		this.result = result;
		this.status = status;
	}

	public AjaxStatus getStatus() {
		return status;
	}

	public void setStatus(AjaxStatus status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		setStatus(result != null ? AjaxStatus.SUCCESS : AjaxStatus.FAILURE);
		this.result = result;
	}

}
