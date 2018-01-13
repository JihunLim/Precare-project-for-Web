package com.wherever.precareweb.dto;

public class Prediction {
	private int pre_id;
	private String pre_userId;
	private String pre_sort;
	private String pre_result;
	private float pre_probability;
	private String pre_comment;
	private String pre_date;
	
	public Prediction() {}

	public int getPre_id() {
		return pre_id;
	}

	public void setPre_id(int pre_id) {
		this.pre_id = pre_id;
	}

	public String getPre_userId() {
		return pre_userId;
	}

	public void setPre_userId(String pre_userId) {
		this.pre_userId = pre_userId;
	}

	public String getPre_sort() {
		return pre_sort;
	}

	public void setPre_sort(String pre_sort) {
		this.pre_sort = pre_sort;
	}

	public String getPre_date() {
		return pre_date;
	}

	public void setPre_date(String pre_date) {
		this.pre_date = pre_date;
	}

	public String getPre_result() {
		return pre_result;
	}

	public void setPre_result(String pre_result) {
		this.pre_result = pre_result;
	}

	public float getPre_probability() {
		return pre_probability;
	}

	public void setPre_probability(float pre_probability) {
		this.pre_probability = pre_probability;
	}

	public String getPre_comment() {
		return pre_comment;
	}

	public void setPre_comment(String pre_comment) {
		this.pre_comment = pre_comment;
	}
	
	
	
	
	
	
	
}
