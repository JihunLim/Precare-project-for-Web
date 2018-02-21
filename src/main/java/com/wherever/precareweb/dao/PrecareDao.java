package com.wherever.precareweb.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wherever.precareweb.dto.Prediction;
import com.wherever.precareweb.dto.ProgressViewDto;
import com.wherever.precareweb.dto.QuestionDto;
import com.wherever.precareweb.dto.ResultDto;
import com.wherever.precareweb.dto.UserDto;

public interface PrecareDao {
	//사용자 이름 출력하기
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
	//사용자가 푼 설문조사 저장하기
	public void insertSurveyAnswerDao(ResultDto data);
	//state 가져오기
	public int countStateWithIdAndSortDao(Map data);
	//답안 가져오기
	public ArrayList<Integer> selectAnswerDao(Map data);
	//예측결과 저장하기
	public void insertPredictDao(Map data);
	//회원가입 저장하기
	public void insertUserDao(UserDto data);
	//id 중복체크
	public int checkDuplicateIdDao(String data);
	//manager 수정하기
	public void updateManagerWithIdDao(Map data);
	//비번 수정하기
	public void updatePwdWithIdDao(Map data);
	//predict id로 결과 가져오기
	public Prediction selectPredictionWithIdDao(String data); 
	//해당 종류의 문제 질문 뽑아오기
	public ArrayList<String> selectQuestinoWithSortDao(String data);
	
	
	
	
	
	//테스트
	public void insertTestDao(String tempStr);
	
}
