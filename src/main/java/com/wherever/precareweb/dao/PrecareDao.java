package com.wherever.precareweb.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wherever.precareweb.dto.Prediction;
import com.wherever.precareweb.dto.ProgressViewDto;
import com.wherever.precareweb.dto.QuestionDto;
import com.wherever.precareweb.dto.UserDto;

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
	//사용자 정보 가져오기 
	public UserDto selectAllUserWithIdDao(String data);
	//사용자 진행정보 가져오기
	public ProgressViewDto selectAllProgressWithIdDao(String data);
	//전체 문제 가져오기 
	public ArrayList<QuestionDto> selectAllQuestionDao();
	//문제 아이디로 문제 가져오기 
	public ArrayList<QuestionDto> selectAllQuestionWithIdDao(String data);
	//문제 유형 별 갯수 가져오기
	public HashMap selectQuestionEndNumDao();
	
}
