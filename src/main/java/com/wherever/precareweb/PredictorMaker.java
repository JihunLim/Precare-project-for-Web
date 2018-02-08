package com.wherever.precareweb;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


public class PredictorMaker {
	// public static JSONObject inpParms;
    private String apikey;
    private String apiurl;
    private String jsonBody2;
    private String f_result;
    private String str_posibility;
    private int f_posibility;
    private String strKrResult;
    private String strEnResult;
    
    public PredictorMaker() {
    	
    }   
    
    public PredictorMaker(ArrayList selectAnswerDao) {
		// TODO Auto-generated constructor stub
    	
	}
    
    public String getF_result() {
		return f_result;
	}

	public int getF_posibility() {
		return Integer.valueOf(str_posibility.substring(2,4));
	}
	
	public String getStrKrResult() {
		//String tempStr = "";
		if("no".equals(f_result))
			return "�ش� ��ȯ�� �ް� ���� �ʽ��ϴ�.";
		else {
			if(f_posibility<=30)
				return "�ش� ��ȯ�� �ǽ��� �� �� ������ ������ ������ �ƴմϴ�.";
			else if(f_posibility<=70)
				return "�ش� ��ȯ�� �ǽ��� �� �� ������ ġ�ᰡ �ʿ��� �����Դϴ�.";
			else if(f_posibility<=100)
				return "�ش� ��ȯ�� �ް� ������ ����� �������� ���������� ������ ġ�ᰡ �ʿ��մϴ�.";
			else
				return "���� �ý��ۿ� ������ �߻��߽��ϴ�.\n�����ڿ��� ���� �ٶ��ϴ�.";
		}
	}
	
	public String getStrEnResult() {
		//String tempStr = "";
		if("no".equals(f_result))
			return "�ش� ��ȯ�� �ް� ���� �ʽ��ϴ�.";
		else {
			if(f_posibility<=30)
				return "�ش� ��ȯ�� �ǽ��� �� �� ������ ������ ������ �ƴմϴ�.";
			else if(f_posibility<=70)
				return "�ش� ��ȯ�� �ǽ��� �� �� ������ ġ�ᰡ �ʿ��� �����Դϴ�.";
			else if(f_posibility<=100)
				return "�ش� ��ȯ�� �ް� ������ ����� �������� ���������� ������ ġ�ᰡ �ʿ��մϴ�.";
			else
				return "���� �ý��ۿ� ������ �߻��߽��ϴ�.\n�����ڿ��� ���� �ٶ��ϴ�.";
		}
	}

	public String settingPredictMaker() {
    	HttpPost post;
        HttpClient client;
        StringEntity entity;
        String result = "";
    	String age = "50";
    	try {
    	apiurl = "https://japaneast.services.azureml.net/workspaces/f3ffcfcfbc994a5884270451a452d864/services/f8e217a14c6d4fbf8f0a3704469933dd/execute?api-version=2.0&details=true";
        apikey = "xLk1CdwJ7WOVD1MauoCS90DrG/74LcpAiHVadMuiniWEWKdX6Bsvtf6/YG/YDZzA2JLWm7FNTT1ikxZtL8jU9A==";
    	}catch(Exception ex) {
    		System.out.println("There are wrong in apiurl or apikey, maybe both!");
    	}
        
    	try {
    		jsonBody2 = "{\r\n" + 
            		"  \"Inputs\": {\r\n" + 
            		"    \"input1\": {\r\n" + 
            		"      \"ColumnNames\": [\r\n" + 
            		"        \"age\",\r\n" + 
            		"        \"education\",\r\n" + 
            		"        \"marital-status\",\r\n" + 
            		"        \"relationship\",\r\n" + 
            		"        \"race\",\r\n" + 
            		"        \"sex\"\r\n" + 
            		"      ],\r\n" + 
            		"      \"Values\": [\r\n" + 
            		"        [\r\n" + 
            		          age+",\r\n" + 
            		"          \"Bachelors\",\r\n" + 
            		"          \"Never-married\",\r\n" + 
            		"          \"Not-in-family\",\r\n" + 
            		"          \"White\",\r\n" + 
            		"          \"Male\"\r\n" + 
            		"        ]\r\n" + 
            		"      ]\r\n" + 
            		"    }\r\n" + 
            		"  },\r\n" + 
            		"  \"GlobalParameters\": {}\r\n" + 
            		"}";
    	}catch(Exception ex) {
    		System.out.println("There are wrong in making jsonBody!");
    	}
    	
    	try {
    		 // create HttpPost and HttpClient object
            post = new HttpPost(apiurl);
            client = HttpClientBuilder.create().build();
            
            // setup output message by copying JSON body into 
            // apache StringEntity object along with content type
            entity = new StringEntity(jsonBody2, HTTP.UTF_8);
            entity.setContentEncoding(HTTP.UTF_8);
            entity.setContentType("text/json");

            // add HTTP headers
            post.setHeader("Accept", "text/json");
            post.setHeader("Accept-Charset", "UTF-8");
        
            // set Authorization header based on the API key
            post.setHeader("Authorization", ("Bearer "+apikey));
            post.setEntity(entity);

            // Call REST API and retrieve response content
            HttpResponse authResponse = client.execute(post);
            
            result =  EntityUtils.toString(authResponse.getEntity());
    	}catch(Exception ex) {
    		System.out.println("There are wrong in communicating with AzureML in posting!");
    	}
   
    	return result;
    }
	
	//����� �Ǻ��� ����
	public Boolean depressionPredictor(ArrayList questionList, HashMap<String, Object> userData) {
		 String result = "";
		 String dep_data="";
	    
    	try {
    		//������� �´� ������ ����
    		apiurl = "https://japaneast.services.azureml.net/workspaces/f3ffcfcfbc994a5884270451a452d864/services/a487a2cd37ea45b28c8c6eb67b34c4ac/execute?api-version=2.0&details=true";
        	apikey = "mHRk8IYo/oX30AzAxWTsqpOX9vpj9MgHLwiDXkraVMvXLXVK3oepKV2ynQT9z+W43bBfHQ31qig24y2zy+r37g==";
    	}catch(Exception ex) {
    		System.out.println("There are wrong in apiurl or apikey, maybe both!");
    	}
        
    	try {
    		dep_data = "{\r\n" + 
    				"  \"Inputs\": {\r\n" + 
    				"    \"input1\": {\r\n" + 
    				"      \"ColumnNames\": [\r\n" + 
    				"        \"que1\",\r\n" + 
    				"        \"que2\",\r\n" + 
    				"        \"que3\",\r\n" + 
    				"        \"que4\",\r\n" + 
    				"        \"que5\",\r\n" + 
    				"        \"que6\",\r\n" +
    				"        \"que7\",\r\n" +
    				"        \"que8\",\r\n" +
    				"        \"que9\",\r\n" +
    				"        \"que10\",\r\n" +
    				"        \"que11\",\r\n" +
    				"        \"que12\",\r\n" +
    				"        \"que13\",\r\n" +
    				"        \"que14\",\r\n" +
    				"        \"que15\",\r\n" +
    				"        \"age\",\r\n" +
    				"        \"sex\",\r\n" +
    				"        \"occupation\"\r\n" +
    				"      ],\r\n" + 
    				"      \"Values\": [\r\n" + 
    				"        [\r\n" + 
    				"          \""+questionList.get(0)+"\",\r\n" + 
    				"          \""+questionList.get(1)+"\",\r\n" +  
    				"          \""+questionList.get(2)+"\",\r\n" +
    				"          \""+questionList.get(3)+"\",\r\n" +
    				"          \""+questionList.get(4)+"\",\r\n" +
    				"          \""+questionList.get(5)+"\",\r\n" +
    				"          \""+questionList.get(6)+"\",\r\n" +
    				"          \""+questionList.get(7)+"\",\r\n" +
    				"          \""+questionList.get(8)+"\",\r\n" +
    				"          \""+questionList.get(9)+"\",\r\n" +
    				"          \""+questionList.get(10)+"\",\r\n" +
    				"          \""+questionList.get(11)+"\",\r\n" +
    				"          \""+questionList.get(12)+"\",\r\n" +
    				"          \""+questionList.get(13)+"\",\r\n" +
    				"          \""+questionList.get(14)+"\",\r\n" +
    				"          \""+userData.get("age").toString()+"\",\r\n" +
    				"          \""+userData.get("sex").toString()+"\",\r\n" +
    				"          \""+userData.get("occupation").toString()+"\"\r\n" +
    				"        ]\r\n" + 
    				"      ]\r\n" + 
    				"    }\r\n" + 
    				"  },\r\n" + 
    				"  \"GlobalParameters\": {}\r\n" + 
    				"}";
    	}catch(Exception ex) {
    		System.out.println("There are wrong in making jsonBody!");
    		System.out.println(ex.getMessage());
    		return false;
    	}
    
    	checkPredictString(1, connectAZureML(apiurl, apikey, dep_data));
	
		
		return true;
	}
	
	//��Ȳ��� �Ǻ��� ����
	public Boolean panicDisorderPredictor(ArrayList questionList, HashMap<String, Object> userData) {
		 String result = "";
		 String dep_data="";
	    
    	try {
    		//��Ȳ��ֿ� �´� ������ ����
    		apiurl = "https://japaneast.services.azureml.net/workspaces/f3ffcfcfbc994a5884270451a452d864/services/335ff71ca80c430ab4083a674c766b50/execute?api-version=2.0&details=true";
        	apikey = "KwtcFqOsiWiZOCmJtC7lmORYhvmJvqGrWCjJpHKuvJ6ZNB/z1poq2Mm3myZz1grjLN1gywK35GXldugGRXDRKA==";
    	}catch(Exception ex) {
    		System.out.println("There are wrong in apiurl or apikey, maybe both!");
    	}
        
    	try {
    		dep_data = "{\r\n" + 
    				"  \"Inputs\": {\r\n" + 
    				"    \"input1\": {\r\n" + 
    				"      \"ColumnNames\": [\r\n" + 
    				"        \"que1\",\r\n" + 
    				"        \"que2\",\r\n" + 
    				"        \"que3\",\r\n" + 
    				"        \"que4\",\r\n" + 
    				"        \"que5\",\r\n" + 
    				"        \"que6\",\r\n" +
    				"        \"que7\",\r\n" +
    				"        \"que8\",\r\n" +
    				"        \"que9\",\r\n" +
    				"        \"que10\",\r\n" +
    				"        \"que11\",\r\n" +
    				"        \"que12\",\r\n" +
    				"        \"que13\",\r\n" +
    				"        \"age\",\r\n" +
    				"        \"sex\",\r\n" +
    				"        \"occupation\"\r\n" +
    				"      ],\r\n" + 
    				"      \"Values\": [\r\n" + 
    				"        [\r\n" + 
    				"          \""+questionList.get(0)+"\",\r\n" + 
    				"          \""+questionList.get(1)+"\",\r\n" +  
    				"          \""+questionList.get(2)+"\",\r\n" +
    				"          \""+questionList.get(3)+"\",\r\n" +
    				"          \""+questionList.get(4)+"\",\r\n" +
    				"          \""+questionList.get(5)+"\",\r\n" +
    				"          \""+questionList.get(6)+"\",\r\n" +
    				"          \""+questionList.get(7)+"\",\r\n" +
    				"          \""+questionList.get(8)+"\",\r\n" +
    				"          \""+questionList.get(9)+"\",\r\n" +
    				"          \""+questionList.get(10)+"\",\r\n" +
    				"          \""+questionList.get(11)+"\",\r\n" +
    				"          \""+questionList.get(12)+"\",\r\n" +
    				"          \""+userData.get("age").toString()+"\",\r\n" +
    				"          \""+userData.get("sex").toString()+"\",\r\n" +
    				"          \""+userData.get("occupation").toString()+"\"\r\n" +
    				"        ]\r\n" + 
    				"      ]\r\n" + 
    				"    }\r\n" + 
    				"  },\r\n" + 
    				"  \"GlobalParameters\": {}\r\n" + 
    				"}";
    	}catch(Exception ex) {
    		System.out.println("There are wrong in making jsonBody!");
    		System.out.println(ex.getMessage());
    		return false;
    	}
	    
    	checkPredictString(2, connectAZureML(apiurl, apikey, dep_data));
	
		
		return true;
	}
	
	//������� �Ǻ��� ����
		public Boolean personalityDisorderPredictor(ArrayList questionList, HashMap<String, Object> userData) {
			 String result = "";
			 String dep_data="";
		    
	    	try {
	    		//������ֿ� �´� ������ ����
	    		apiurl = "https://japaneast.services.azureml.net/workspaces/f3ffcfcfbc994a5884270451a452d864/services/e6a1c50893814f8a92ed7c2480a426cb/execute?api-version=2.0&details=true";
	        	apikey = "IkMqgN084ONVVZWVpRn85NERj/72OslxYxsTtHo7MkkixFcRHXsdftbf/5MBhHaylCK7lqtqIfcnkjIGFuWfVQ==";
	    	}catch(Exception ex) {
	    		System.out.println("There are wrong in apiurl or apikey, maybe both!");
	    	}
	        
	    	try {
	    		dep_data = "{\r\n" + 
	    				"  \"Inputs\": {\r\n" + 
	    				"    \"input1\": {\r\n" + 
	    				"      \"ColumnNames\": [\r\n" + 
	    				"        \"qua1\",\r\n" + 
	    				"        \"qua2\",\r\n" + 
	    				"        \"qua3\",\r\n" + 
	    				"        \"qua4\",\r\n" + 
	    				"        \"qua5\",\r\n" + 
	    				"        \"qua6\",\r\n" +
	    				"        \"qua7\",\r\n" +
	    				"        \"qua8\",\r\n" +
	    				"        \"qua9\",\r\n" +
	    				"        \"qua10\",\r\n" +
	    				"        \"qua11\",\r\n" +
	    				"        \"qua12\",\r\n" +
	    				"        \"qua13\",\r\n" +
	    				"        \"qua14\",\r\n" +
	    				"        \"qua15\",\r\n" +
	    				"        \"qua16\",\r\n" +
	    				"        \"qua17\",\r\n" +
	    				"        \"qua18\",\r\n" +
	    				"        \"age\",\r\n" +
	    				"        \"sex\",\r\n" +
	    				"        \"occupation\"\r\n" +
	    				"      ],\r\n" + 
	    				"      \"Values\": [\r\n" + 
	    				"        [\r\n" + 
	    				"          \""+questionList.get(0)+"\",\r\n" + 
	    				"          \""+questionList.get(1)+"\",\r\n" +  
	    				"          \""+questionList.get(2)+"\",\r\n" +
	    				"          \""+questionList.get(3)+"\",\r\n" +
	    				"          \""+questionList.get(4)+"\",\r\n" +
	    				"          \""+questionList.get(5)+"\",\r\n" +
	    				"          \""+questionList.get(6)+"\",\r\n" +
	    				"          \""+questionList.get(7)+"\",\r\n" +
	    				"          \""+questionList.get(8)+"\",\r\n" +
	    				"          \""+questionList.get(9)+"\",\r\n" +
	    				"          \""+questionList.get(10)+"\",\r\n" +
	    				"          \""+questionList.get(11)+"\",\r\n" +
	    				"          \""+questionList.get(12)+"\",\r\n" +
	    				"          \""+questionList.get(13)+"\",\r\n" +
	    				"          \""+questionList.get(14)+"\",\r\n" +
	    				"          \""+questionList.get(15)+"\",\r\n" +
	    				"          \""+questionList.get(16)+"\",\r\n" +
	    				"          \""+questionList.get(17)+"\",\r\n" +
	    				"          \""+userData.get("age").toString()+"\",\r\n" +
	    				"          \""+userData.get("sex").toString()+"\",\r\n" +
	    				"          \""+userData.get("occupation").toString()+"\"\r\n" +
	    				"        ]\r\n" + 
	    				"      ]\r\n" + 
	    				"    }\r\n" + 
	    				"  },\r\n" + 
	    				"  \"GlobalParameters\": {}\r\n" + 
	    				"}";
	    	}catch(Exception ex) {
	    		System.out.println("There are wrong in making jsonBody!");
	    		System.out.println(ex.getMessage());
	    		return false;
	    	}
		    
	    	checkPredictString(3, connectAZureML(apiurl, apikey, dep_data));
		
			
			return true;
		}
		
		//������� �Ǻ��� ����
		public Boolean imsomniaDisorderPredictor(ArrayList questionList, HashMap<String, Object> userData) {
			 String result = "";
			 String dep_data="";
		    
	    	try {
	    		//������ֿ� �´� ������ ����
	    		apiurl = "https://japaneast.services.azureml.net/workspaces/f3ffcfcfbc994a5884270451a452d864/services/0706c7a864d9417c843196d28308615a/execute?api-version=2.0&details=true";
	        	apikey = "Pe9pCTytShvlFP/bJpE/1bK+FF3V6WWJTr4RzOXeFe+nNEUuNyjdzkmeGwH7f5C20PXyNlk+9jhRhgRRIPew+A==";
	    	}catch(Exception ex) {
	    		System.out.println("There are wrong in apiurl or apikey, maybe both!");
	    	}
	        
	    	try {
	    		dep_data = "{\r\n" + 
	    				"  \"Inputs\": {\r\n" + 
	    				"    \"input1\": {\r\n" + 
	    				"      \"ColumnNames\": [\r\n" + 
	    				"        \"qua1\",\r\n" + 
	    				"        \"qua2\",\r\n" + 
	    				"        \"qua3\",\r\n" + 
	    				"        \"qua4\",\r\n" + 
	    				"        \"qua5\",\r\n" + 
	    				"        \"qua6\",\r\n" +
	    				"        \"qua7\",\r\n" +
	    				"        \"qua8\",\r\n" +
	    				"        \"qua9\",\r\n" +
	    				"        \"qua10\",\r\n" +
	    				"        \"qua11\",\r\n" +
	    				"        \"qua12\",\r\n" +
	    				"        \"qua13\",\r\n" +
	    				"        \"age\",\r\n" +
	    				"        \"sex\",\r\n" +
	    				"        \"occupation\"\r\n" +
	    				"      ],\r\n" + 
	    				"      \"Values\": [\r\n" + 
	    				"        [\r\n" + 
	    				"          \""+questionList.get(0)+"\",\r\n" + 
	    				"          \""+questionList.get(1)+"\",\r\n" +  
	    				"          \""+questionList.get(2)+"\",\r\n" +
	    				"          \""+questionList.get(3)+"\",\r\n" +
	    				"          \""+questionList.get(4)+"\",\r\n" +
	    				"          \""+questionList.get(5)+"\",\r\n" +
	    				"          \""+questionList.get(6)+"\",\r\n" +
	    				"          \""+questionList.get(7)+"\",\r\n" +
	    				"          \""+questionList.get(8)+"\",\r\n" +
	    				"          \""+questionList.get(9)+"\",\r\n" +
	    				"          \""+questionList.get(10)+"\",\r\n" +
	    				"          \""+questionList.get(11)+"\",\r\n" +
	    				"          \""+questionList.get(12)+"\",\r\n" +
	    				"          \""+userData.get("age").toString()+"\",\r\n" +
	    				"          \""+userData.get("sex").toString()+"\",\r\n" +
	    				"          \""+userData.get("occupation").toString()+"\"\r\n" +
	    				"        ]\r\n" + 
	    				"      ]\r\n" + 
	    				"    }\r\n" + 
	    				"  },\r\n" + 
	    				"  \"GlobalParameters\": {}\r\n" + 
	    				"}";
	    	}catch(Exception ex) {
	    		System.out.println("There are wrong in making jsonBody!");
	    		System.out.println(ex.getMessage());
	    		return false;
	    	}
		    
	    	checkPredictString(4, connectAZureML(apiurl, apikey, dep_data));
		
			
			return true;
		}
		
		//���ͳ��ߵ�(num5) �Ǻ��� ����
		public Boolean internetAddictionPredictor(ArrayList questionList, HashMap<String, Object> userData) {
			 String result = "";
			 String dep_data="";
		    
	    	try {
	    		//���ͳ��ߵ��� �´� ������ ����
	    		apiurl = "https://japaneast.services.azureml.net/workspaces/f3ffcfcfbc994a5884270451a452d864/services/d6381f61c43b46d1b0d99cb8343f5438/execute?api-version=2.0&details=true";
	        	apikey = "AzmOeRMfqPHGpMWgVyNvKLH9t6M+JMYVa4eOD2+rK6C0zpUckBFRX3wvkONjsFU01ByASSMmwKlaA4H60b00Yw==";
	    	}catch(Exception ex) {
	    		System.out.println("There are wrong in apiurl or apikey, maybe both!");
	    	}
	        
	    	try {
	    		dep_data = "{\r\n" + 
	    				"  \"Inputs\": {\r\n" + 
	    				"    \"input1\": {\r\n" + 
	    				"      \"ColumnNames\": [\r\n" + 
	    				"        \"qua1\",\r\n" + 
	    				"        \"qua2\",\r\n" + 
	    				"        \"qua3\",\r\n" + 
	    				"        \"qua4\",\r\n" + 
	    				"        \"qua5\",\r\n" + 
	    				"        \"qua6\",\r\n" +
	    				"        \"qua7\",\r\n" +
	    				"        \"qua8\",\r\n" +
	    				"        \"qua9\",\r\n" +
	    				"        \"qua10\",\r\n" +
	    				"        \"qua11\",\r\n" +
	    				"        \"qua12\",\r\n" +
	    				"        \"qua13\",\r\n" +
	    				"        \"qua14\",\r\n" +
	    				"        \"qua15\",\r\n" +
	    				"        \"qua16\",\r\n" +
	    				"        \"qua17\",\r\n" +
	    				"        \"qua18\",\r\n" +
	    				"        \"qua19\",\r\n" +
	    				"        \"qua20\",\r\n" +
	    				"        \"age\",\r\n" +
	    				"        \"sex\",\r\n" +
	    				"        \"occupation\"\r\n" +
	    				"      ],\r\n" + 
	    				"      \"Values\": [\r\n" + 
	    				"        [\r\n" + 
	    				"          \""+questionList.get(0)+"\",\r\n" + 
	    				"          \""+questionList.get(1)+"\",\r\n" +  
	    				"          \""+questionList.get(2)+"\",\r\n" +
	    				"          \""+questionList.get(3)+"\",\r\n" +
	    				"          \""+questionList.get(4)+"\",\r\n" +
	    				"          \""+questionList.get(5)+"\",\r\n" +
	    				"          \""+questionList.get(6)+"\",\r\n" +
	    				"          \""+questionList.get(7)+"\",\r\n" +
	    				"          \""+questionList.get(8)+"\",\r\n" +
	    				"          \""+questionList.get(9)+"\",\r\n" +
	    				"          \""+questionList.get(10)+"\",\r\n" +
	    				"          \""+questionList.get(11)+"\",\r\n" +
	    				"          \""+questionList.get(12)+"\",\r\n" +
	    				"          \""+questionList.get(13)+"\",\r\n" +
	    				"          \""+questionList.get(14)+"\",\r\n" +
	    				"          \""+questionList.get(15)+"\",\r\n" +
	    				"          \""+questionList.get(16)+"\",\r\n" +
	    				"          \""+questionList.get(17)+"\",\r\n" +
	    				"          \""+questionList.get(18)+"\",\r\n" +
	    				"          \""+questionList.get(19)+"\",\r\n" +
	    				"          \""+userData.get("age").toString()+"\",\r\n" +
	    				"          \""+userData.get("sex").toString()+"\",\r\n" +
	    				"          \""+userData.get("occupation").toString()+"\"\r\n" +
	    				"        ]\r\n" + 
	    				"      ]\r\n" + 
	    				"    }\r\n" + 
	    				"  },\r\n" + 
	    				"  \"GlobalParameters\": {}\r\n" + 
	    				"}";
	    	}catch(Exception ex) {
	    		System.out.println("There are wrong in making jsonBody!");
	    		System.out.println(ex.getMessage());
	    		return false;
	    	}
		    
	    	checkPredictString(5, connectAZureML(apiurl, apikey, dep_data));
		
			
			return true;
		}
	
	public String connectAZureML(String apiurl, String apikey, String inputdata) {
		HttpPost post;
        HttpClient client;
        StringEntity entity;
        String result = "";
        
		try {
   		 // create HttpPost and HttpClient object
           post = new HttpPost(apiurl);
           client = HttpClientBuilder.create().build();
           
           // setup output message by copying JSON body into 
           // apache StringEntity object along with content type
           entity = new StringEntity(inputdata, HTTP.UTF_8);
           entity.setContentEncoding(HTTP.UTF_8);
           entity.setContentType("text/json");

           // add HTTP headers
           post.setHeader("Accept", "text/json");
           post.setHeader("Accept-Charset", "UTF-8");
       
           // set Authorization header based on the API key
           post.setHeader("Authorization", ("Bearer "+apikey));
           post.setEntity(entity);

           // Call REST API and retrieve response content
           HttpResponse authResponse = client.execute(post);
           
           result =  EntityUtils.toString(authResponse.getEntity());
   	}catch(Exception ex) {
   		System.out.println("There are wrong in communicating with AzureML in posting!");
   	}
  
   	return result;
	}
	
	public void checkPredictString(int mode, String predictStr) {
		try {
			if(mode == 1) {
				//������� ���
				int i = 1;
				String[] temp = predictStr.split("\"");
				System.out.println("�����");
				for(String word : temp) {
					System.out.println(i + ">>  " + word);
					i++;
				}
				f_result = temp[133].toString();
				str_posibility = temp[135].toString();
			}else if(mode == 2) {
				//��Ȳ���
				int i = 1;
				String[] temp = predictStr.split("\"");
				System.out.println("��Ȳ���");
				for(String word : temp) {
					System.out.println(i + ">>  " + word);
					i++;
				}
				f_result = temp[121].toString();
				str_posibility = temp[123].toString();
			}else if(mode == 3) {
				//�������
				int i = 1;
				String[] temp = predictStr.split("\"");
				System.out.println("�������");
				for(String word : temp) {
					System.out.println(i + ">>  " + word);
					i++;
				}
				f_result = temp[151].toString();
				str_posibility = temp[153].toString();
			}else if(mode == 4) {
				//�������
				int i = 1;
				String[] temp = predictStr.split("\"");
				System.out.println("�������");
				for(String word : temp) {
					System.out.println(i + ">>  " + word);
					i++;
				}
				f_result = temp[121].toString();
				str_posibility = temp[123].toString();
				
			}else if(mode == 5) {
				//���ͳ��ߵ�
				int i = 1;
				String[] temp = predictStr.split("\"");
				System.out.println("���ͳ��ߵ�");
				for(String word : temp) {
					System.out.println(i + ">>  " + word);
					i++;
				}
				f_result = temp[163].toString();
				str_posibility = temp[165].toString();
			}
			
		}catch(Exception ex) {
			System.out.println("There are wrong in getting result!");
		}
		
	}
	
	
	
}





