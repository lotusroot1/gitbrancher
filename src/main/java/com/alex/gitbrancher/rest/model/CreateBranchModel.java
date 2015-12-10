package com.alex.gitbrancher.rest.model;

import java.io.Serializable;

public class CreateBranchModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sha;
	private String name;

	public CreateBranchModel() {

	}

	public CreateBranchModel(String sha, String branchName) {
		this.sha = sha;
		this.name = branchName;
	}

	public String getSha() {
		return sha;
	}

	public String getName() {
		return name;
	}

	public void setSha(String sha) {
		this.sha = sha;
	}

	public void setName(String name) {
		this.name = name;
	}

}
