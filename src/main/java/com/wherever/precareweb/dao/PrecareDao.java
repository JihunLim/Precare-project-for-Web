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
	//����� �̸� ����ϱ�
	public String selectUserNameWithIdDao(String data);
	//���� ��� ����ϱ�
	public List<Prediction> selectAllPredictionWithIdDao(String data);
	//���� ��� ���� ����ϱ�
	public int selectCountPredictionWithIdDao(String data);
	//Ŀ��Ʈ �����ϱ�(����id, Ŀ��Ʈ
	public void updateCommentWithIdDao(Map data);
	//������ ��������
	public String selectAllManagersWithIdDao(String data);
	//����� ���� �������� 
	public UserDto selectAllUserWithIdDao(String data);
	//����� �������� ��������
	public ProgressViewDto selectAllProgressWithIdDao(String data);
	//��ü ���� �������� 
	public ArrayList<QuestionDto> selectAllQuestionDao();
	//���� ���̵�� ���� �������� 
	public ArrayList<QuestionDto> selectAllQuestionWithIdDao(String data);
	//���� ���� �� ���� ��������
	public HashMap selectQuestionEndNumDao();
	//����ڰ� Ǭ �������� �����ϱ�
	public void insertSurveyAnswerDao(ResultDto data);
	//state ��������
	public int countStateWithIdAndSortDao(Map data);
	//��� ��������
	public ArrayList<Integer> selectAnswerDao(Map data);
	//������� �����ϱ�
	public void insertPredictDao(Map data);
	//ȸ������ �����ϱ�
	public void insertUserDao(UserDto data);
	//id �ߺ�üũ
	public int checkDuplicateIdDao(String data);
	//manager �����ϱ�
	public void updateManagerWithIdDao(Map data);
	//��� �����ϱ�
	public void updatePwdWithIdDao(Map data);
	//predict id�� ��� ��������
	public Prediction selectPredictionWithIdDao(String data); 
	//�ش� ������ ���� ���� �̾ƿ���
	public ArrayList<String> selectQuestinoWithSortDao(String data);
	
	
	
	
	
	//�׽�Ʈ
	public void insertTestDao(String tempStr);
	
}
