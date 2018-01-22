package com.wherever.precareweb.dto;

public class ResultDto {
	private int res_id;
	private String res_userId;
	private String res_questionId;
	private int res_questionAnswer;
	private int res_status;
	private String res_date;
	
	public ResultDto() {}

	public ResultDto(int res_id, String res_userId, String res_questionId, int res_questionAnswer, int res_status,
			String res_date) {
		super();
		this.res_id = res_id;
		this.res_userId = res_userId;
		this.res_questionId = res_questionId;
		this.res_questionAnswer = res_questionAnswer;
		this.res_status = res_status;
		this.res_date = res_date;
	}

	public ResultDto(String res_userId, String res_questionId, int res_questionAnswer, int res_status) {
		super();
		this.res_userId = res_userId;
		this.res_questionId = res_questionId;
		this.res_questionAnswer = res_questionAnswer;
		this.res_status = res_status;
	}

	public int getRes_id() {
		return res_id;
	}

	public void setRes_id(int res_id) {
		this.res_id = res_id;
	}

	public String getRes_userId() {
		return res_userId;
	}

	public void setRes_userId(String res_userId) {
		this.res_userId = res_userId;
	}

	public String getRes_questionId() {
		return res_questionId;
	}

	public void setRes_questionId(String res_questionId) {
		this.res_questionId = res_questionId;
	}

	public int getRes_questionAnswer() {
		return res_questionAnswer;
	}

	public void setRes_questionAnswer(int res_questionAnswer) {
		this.res_questionAnswer = res_questionAnswer;
	}

	public int getRes_status() {
		return res_status;
	}

	public void setRes_status(int res_status) {
		this.res_status = res_status;
	}

	public String getRes_date() {
		return res_date;
	}

	public void setRes_date(String res_date) {
		this.res_date = res_date;
	}

	
}
