package com.alex.gitbrancher.model;

import java.sql.Timestamp;

public class Tweets {

	private Integer tweeter;
	private String content;
	private Timestamp createddate;

	public Tweets(Integer userId1, String con, Timestamp date) {
		this.tweeter = userId1;
		this.content = con;
		this.createddate = date;
	}

	public Integer getTweeter() {
		return tweeter;
	}

	public void setTweeter(Integer tweeter) {
		this.tweeter = tweeter;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

}