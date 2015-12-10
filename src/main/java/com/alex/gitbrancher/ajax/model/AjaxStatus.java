package com.alex.gitbrancher.ajax.model;

public enum AjaxStatus {
	SUCCESS("success"), //
	FAILURE("failure");

	private String message;

	private AjaxStatus(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
