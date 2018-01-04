package com.wherever.precareweb.dto;

public class ResultDto {
	private int result_id;
	private String user_id;
	private String question_id;
	private int question_result;
	private String last_status;
	
	public ResultDto() {}

	public ResultDto(int result_id, String user_id, String question_id, int question_result, String last_status) {
		super();
		this.result_id = result_id;
		this.user_id = user_id;
		this.question_id = question_id;
		this.question_result = question_result;
		this.last_status = last_status;
	}

	public int getResult_id() {
		return result_id;
	}

	public void setResult_id(int result_id) {
		this.result_id = result_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}

	public int getQuestion_result() {
		return question_result;
	}

	public void setQuestion_result(int question_result) {
		this.question_result = question_result;
	}

	public String getLast_status() {
		return last_status;
	}

	public void setLast_status(String last_status) {
		this.last_status = last_status;
	}
	
	
}
