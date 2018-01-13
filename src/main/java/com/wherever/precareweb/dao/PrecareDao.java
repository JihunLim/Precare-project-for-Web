package com.wherever.precareweb.dao;

import java.util.List;

import com.wherever.precareweb.dto.Prediction;

public interface PrecareDao {
	//부서 장 이메일 출력하기
	public String selectUserNameWithIdDao(String data);
	//예측 결과 출력하기
	public List<Prediction> selectAllPredictionWithIdDao(String data);
	//예측 결과 개수 출력하기
	public int selectCountPredictionWithIdDao(String data);
	
		
}
