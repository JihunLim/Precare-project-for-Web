package com.wherever.precareweb.dao;

import java.util.List;

import com.wherever.precareweb.dto.Prediction;

public interface PrecareDao {
	//�μ� �� �̸��� ����ϱ�
	public String selectUserNameWithIdDao(String data);
	//���� ��� ����ϱ�
	public List<Prediction> selectAllPredictionWithIdDao(String data);
	//���� ��� ���� ����ϱ�
	public int selectCountPredictionWithIdDao(String data);
	
		
}
