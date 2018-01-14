package com.wherever.precareweb.dao;

import java.util.List;
import java.util.Map;

import com.wherever.precareweb.dto.Prediction;

public interface PrecareDao {
	//부서 장 이메일 출력하기
	public String selectUserNameWithIdDao(String data);
	//예측 결과 출력하기
	public List<Prediction> selectAllPredictionWithIdDao(String data);
	//예측 결과 개수 출력하기
	public int selectCountPredictionWithIdDao(String data);
	//커멘트 수정하기(유저id, 커멘트
	public void updateCommentWithIdDao(Map data);
	//관리자 가져오기
	public String selectAllManagersWithIdDao(String data);
}
