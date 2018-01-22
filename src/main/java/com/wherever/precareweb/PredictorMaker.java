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
	
	//우울증 판별모델 접속
	public Boolean depressionPredictor(ArrayList questionList, HashMap<String, Object> userData) {
		 String result = "";
		 String dep_data="";
	    
    	try {
    		//우울증에 맞는 것으로 변경
    		apiurl = "https://japaneast.services.azureml.net/workspaces/f3ffcfcfbc994a5884270451a452d864/services/211baad2745f449389f195b6303f0566/execute?api-version=2.0&details=true";
        	apikey = "jpv4F/uYHMuSDbYeGE6iOHnJhDiwncBQ52CQ7gKdX2A95PX0eEuVsy+wzCZg9w9+kFWJB6BLHDJd3/CtHEaEww==";
    	}catch(Exception ex) {
    		System.out.println("There are wrong in apiurl or apikey, maybe both!");
    	}
        
    	try {
    		dep_data = "{\r\n" + 
    				"  \"Inputs\": {\r\n" + 
    				"    \"input1\": {\r\n" + 
    				"      \"ColumnNames\": [\r\n" + 
    				"        \"question1\",\r\n" + 
    				"        \"question2\",\r\n" + 
    				"        \"question3\",\r\n" + 
    				"        \"question4\",\r\n" + 
    				"        \"question5\",\r\n" + 
    				"        \"question6\",\r\n" +
    				"        \"question7\",\r\n" +
    				"        \"question8\",\r\n" +
    				"        \"question9\",\r\n" +
    				"        \"question10\",\r\n" +
    				"        \"question11\",\r\n" +
    				"        \"question12\",\r\n" +
    				"        \"question13\",\r\n" +
    				"        \"question14\",\r\n" +
    				"        \"question15\",\r\n" +
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
				//우울증인 경우
				int i = 1;
				String[] temp = predictStr.split("\"");
				for(String word : temp) {
					System.out.println(i + ">>  " + word);
					i++;
				}
				if("depressive-disorder".equals(temp[133].toString()))
					f_result = "yes";
				else
					f_result = "no";
				
				str_posibility = temp[135].toString();
			}
			
		}catch(Exception ex) {
			System.out.println("There are wrong in getting result!");
		}
		
	}
	
	
	
}





