package com.wherever.precareweb;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wherever.precareweb.dao.PrecareDao;
import com.wherever.precareweb.dto.Prediction;
import com.wherever.precareweb.dto.ProgressViewDto;
import com.wherever.precareweb.dto.QuestionDto;
import com.wherever.precareweb.dto.ResultDto;
import com.wherever.precareweb.dto.UserDto;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	public SqlSession sqlSession;

	@Autowired
	private JavaMailSender mailSender;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		UserDto userInfo = null;
		ProgressViewDto progressInfo = null;
		ArrayList<QuestionDto> questionList = null;
		HashMap queEndNum = null;
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
		
		String user_id= SecurityContextHolder.getContext().getAuthentication().getName().toString(); 
		System.out.println("dd : " + user_id);
		if("anonymousUser".equals(user_id) || "".equals(user_id)|| user_id == null) {
			//로그인이 안되있는 경우
			model.addAttribute("user_name", "Guest");
		}else {
			//로그인이 되어있는 경우
			model.addAttribute("user_name", dao.selectUserNameWithIdDao(user_id));
			userInfo = dao.selectAllUserWithIdDao(user_id);
			progressInfo = dao.selectAllProgressWithIdDao(user_id);
			queEndNum = dao.selectQuestionEndNumDao();
		}
		
		// 문제 -> 로그인이 안되어 있을 경우에는 어떻게 할 것인가?
		
		/*
		 * 설문조사 선택 알고리즘
		 * 1. 설문조사 종목을 가져온다. (1:auto/2:우울증/3:공황장애/4:성격장애/5:수면장애/6:인터넷중독) -> 기본 설정은 auto
		      - auto는 각 항목 당 문제 1개씩 가져옴
		 * 2. progress 테이블을 참조하여 현재 진행된 설문조사 번호를 각 항목별로 가져온다.
		 *    - 단, 해당 유형의 번호가 마지막 번호일 경우(- 다 푼 경우임, % 이용) 
		        1. 첫번재 문제로 선택
		 * 3. 1번에서 선택된 항목으로 5개의 질문을 가져온다. 
		 * 4. 질문을 veiw 쪽으로 뿌려준다.  
		 */
		String queMode = "";
		String nextDepressionId = "";
		String nextPanicdisorderId = "";
		String nextPersonalitydisorderId = "";
		String nextInsomniaId = "";
		String nextInternetaddictionId = "";
		
		//1. 최근에 진행된 정보를 가져온다. -> progressInfo
		//2. 각 항목 별 다음 문제 번호를 구한다.
	
		if("anonymousUser".equals(user_id) || "".equals(user_id)|| user_id == null) {
			//로그인이 안되있는 경우
			//설문조사 모드 설정
			queMode = "auto";
			nextDepressionId = "001001";
			nextPanicdisorderId = "002001";
			nextPersonalitydisorderId = "003001";
			nextInsomniaId = "004001";
			nextInternetaddictionId = "005001";
		}else {
			//로그인이 되어있는 경우
			//그래프를 위해서 각 종목 별 진행 상황 전달
			
			model.addAttribute("status_dep", ((progressInfo.getPro_depressionState())!=null)?(Integer.parseInt(progressInfo.getPro_depressionState().substring(3, 6)) ):0);
			model.addAttribute("status_pan", ((progressInfo.getPro_panicdisorderState())!=null)?(Integer.parseInt(progressInfo.getPro_panicdisorderState().substring(3, 6)) ):0);
			model.addAttribute("status_per", ((progressInfo.getPro_personalitydisorderState())!=null)?(Integer.parseInt(progressInfo.getPro_personalitydisorderState().substring(3, 6)) ):0);
			model.addAttribute("status_ins", ((progressInfo.getPro_dyslepsiaState())!=null)?(Integer.parseInt(progressInfo.getPro_dyslepsiaState().substring(3, 6)) ):0);
			model.addAttribute("status_int", ((progressInfo.getPro_internetaddictionState())!=null)?(Integer.parseInt(progressInfo.getPro_internetaddictionState().substring(3, 6)) ):0);
			
			
			//설문조사 모드 설정
			queMode = userInfo.getUser_questionMode();
			//우울증
			int temp = ((progressInfo.getPro_depressionState())!=null)?(Integer.parseInt(progressInfo.getPro_depressionState().substring(3, 6)) ):0;
			model.addAttribute("status_dep", String.valueOf(temp));
			String strTemp = "";
			if(temp % Integer.parseInt(queEndNum.get("qen_depressionENum").toString()) == 0) {
				//문제를 다 푼 경우
				strTemp = "001";
			}else {
				//문제를 다 풀지 못한 경우 -> temp 숫자를 3자리의 문자열로 변경
				strTemp = String.format("%03d", temp+1);
			}
			nextDepressionId = "001" + strTemp;
			
			//공황장애
			temp = ((progressInfo.getPro_panicdisorderState())!=null)?(Integer.parseInt(progressInfo.getPro_panicdisorderState().substring(3, 6)) ):0;
			strTemp = "";
			if(temp % Integer.parseInt(queEndNum.get("qen_panicENum").toString()) == 0) {
				//문제를 다 푼 경우
				strTemp = "001";
			}else {
				//문제를 다 풀지 못한 경우 -> temp 숫자를 3자리의 문자열로 변경
				strTemp = String.format("%03d", temp);
			}
			nextPanicdisorderId = "002" + strTemp;
			
			//성격장애
			temp = ((progressInfo.getPro_personalitydisorderState())!=null)?(Integer.parseInt(progressInfo.getPro_personalitydisorderState().substring(3, 6)) ):0;
			strTemp = "";
			if(temp % Integer.parseInt(queEndNum.get("qen_personalityENum").toString()) == 0) {
				//문제를 다 푼 경우
				strTemp = "001";
			}else {
				//문제를 다 풀지 못한 경우 -> temp 숫자를 3자리의 문자열로 변경
				strTemp = String.format("%03d", temp);
			}
			nextPersonalitydisorderId = "003" + strTemp;
			
			//수면장애
			temp = ((progressInfo.getPro_dyslepsiaState())!=null)?(Integer.parseInt(progressInfo.getPro_dyslepsiaState().substring(3, 6)) ):0;
			strTemp = "";
			if(temp % Integer.parseInt(queEndNum.get("qen_dyslepsiaENum").toString()) == 0) {
				//문제를 다 푼 경우
				strTemp = "001";
			}else {
				//문제를 다 풀지 못한 경우 -> temp 숫자를 3자리의 문자열로 변경
				strTemp = String.format("%03d", temp);
			}
			nextInsomniaId = "004" + strTemp;
			
			//인터넷중독
			temp = ((progressInfo.getPro_internetaddictionState())!=null)?(Integer.parseInt(progressInfo.getPro_internetaddictionState().substring(3, 6)) ):0;
			strTemp = "";
			if(temp % Integer.parseInt(queEndNum.get("qen_internetENum").toString()) == 0) {
				//문제를 다 푼 경우
				strTemp = "001";
			}else {
				//문제를 다 풀지 못한 경우 -> temp 숫자를 3자리의 문자열로 변경
				strTemp = String.format("%03d", temp);
			}
			nextInternetaddictionId = "005" + strTemp;		
		}
		
		
		if("auto".equals(queMode)) {
			//전체 문제를 가져온다. 
			questionList = dao.selectAllQuestionDao(); 
			//각 문제 하나씩을 가져온다. 
			for(QuestionDto que : questionList) {
				if("depression".equals(que.getQue_sort())) {
					//1. 우울증 문제 추출 및 전달
					if(nextDepressionId.equals(que.getQue_id())) {
						model.addAttribute("question1", que.getQue_text());
						model.addAttribute("id_question1", que.getQue_id());
						continue;
					}
				}else if("Panic disorder".equals(que.getQue_sort())) {
					//2. 공황장애 문제 추출 및 전달
					if(nextPanicdisorderId.equals(que.getQue_id())) {
						model.addAttribute("question2", que.getQue_text());
						model.addAttribute("id_question2", que.getQue_id());
						continue;
					}
				}else if("Personality disorder".equals(que.getQue_sort())) {
					//3. 성격장애 문제 추출 및 전달
					if(nextPersonalitydisorderId.equals(que.getQue_id())) {
						model.addAttribute("question3", que.getQue_text());
						model.addAttribute("id_question3", que.getQue_id());
						continue;
					}
				}else if("Dyslepsia".equals(que.getQue_sort())) {
					//4. 수면장애 문제 추출 및 전달
					if(nextInsomniaId.equals(que.getQue_id())) {
						model.addAttribute("question4", que.getQue_text());
						model.addAttribute("id_question4", que.getQue_id());
						continue;
					}
				}else if("Internet addiction".equals(que.getQue_sort())) {
					//5. 인터넷중독 문제 추출 및 전달
					if(nextInternetaddictionId.equals(que.getQue_id())) {
						model.addAttribute("question5", que.getQue_text());
						model.addAttribute("id_question5", que.getQue_id());
						continue;
					}
				}
			}
		}
		
		return "home";
	}
	
	@RequestMapping("/loginForm") 
	public String loginForm(Locale locale, Model model) throws Exception {
		//use this under code when you use db.
		PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
	
		return "login/loginForm";
	}
	
	@RequestMapping("/loginSuccess") 
	public String loginSuccess(Locale locale, Model model) throws Exception {
		//use this under code when you use db.
		PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
	
		return "login/loginSuccess";
	}
	
	@RequestMapping("/showResult") 
	public String showResult(Locale locale, Model model, HttpServletRequest request) throws Exception {
		String page = "list/resultPage";
		Boolean checkResultToCome = false;
		Prediction preWithIdContent = null;
		String managers = null;
		String[] argManagers = null;
		String loginId = SecurityContextHolder.getContext().getAuthentication().getName().toString();
		//use this under code when you use db.
		PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
		String user_id= request.getParameter("user_id"); 
		String target_preId = request.getParameter("target_preId");
		
		if(user_id == null || "".equals(user_id) || "anonymousUser".equals(user_id)) {
			//get current login user's infomation
			user_id= loginId; 
			checkResultToCome = true;
		}
		//접근하고자 하는 사용자가 존재하는지 확인
		String userName = dao.selectUserNameWithIdDao(user_id);
		if(userName == null || "".equals(userName)) {
			page = "cmmn/notFoundUser";
			return page;
		}
		
		//접근하고자 하는 사용자가 자신이라면 조회 가능
		if(loginId.equals(user_id))
			checkResultToCome = true;
		
		// 접근하고자 하는 사용자가 조회하려는 사용자의 관리자가 맞는지 확인
		if(!checkResultToCome){
			managers = dao.selectAllManagersWithIdDao(user_id);
			argManagers = managers.split(",");
			for(String word : argManagers) {
				if(loginId.equals(word))
					checkResultToCome = true;
			}
			if(!checkResultToCome) {
				page = "cmmn/notAccessUser";
				return page;
			}
				
		}
		
		
		model.addAttribute("user_id", user_id);
		model.addAttribute("user_name", userName);
		model.addAttribute("login_id", loginId);
		
		//prediction 정보 가져오기
		int numPrediction = dao.selectCountPredictionWithIdDao(user_id);
		List<Prediction> predictionList = null;
		if(numPrediction > 0) {
			predictionList = dao.selectAllPredictionWithIdDao(user_id);
		} 
		model.addAttribute("prediction_count", numPrediction);
		model.addAttribute("prediction_list", predictionList);
		System.out.println("target_preid : " + target_preId);
		if(target_preId != null) { 
			preWithIdContent = dao.selectPredictionWithIdDao(target_preId);
		}
		else {
			if(numPrediction > 0)
				preWithIdContent = (Prediction)predictionList.get(0);
		}
		
		model.addAttribute("target_prediction", preWithIdContent);
	
		
		return page;
	}
	
	
	@RequestMapping("/updateComment") 
	public String updateComment(Locale locale, Model model, HttpServletRequest request) throws Exception {
		String resultPage = "cmmn/saveDataSuccess";
		try {
		PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
		String comment = request.getParameter("comment");
		String pre_id = request.getParameter("pre_id");
		Map<String, String> pre_map = new HashMap<String, String>();
		pre_map.put("pre_id", pre_id);
		pre_map.put("comment", comment);
		dao.updateCommentWithIdDao(pre_map);
		}catch(Exception ex) {
			resultPage = "cmmn/saveDataFailure";
			System.out.println(ex.getStackTrace());
		}
		return resultPage;
	}
	
	
	@RequestMapping("/checkForm") 
	public String checkForm(Locale locale, Model model, HttpServletRequest request) throws Exception {
		String resultPage = "cmmn/saveDataSuccess";
		String user_id= SecurityContextHolder.getContext().getAuthentication().getName().toString();
		ResultDto resDto = null;
		HashMap<String, Integer> endNumQuestion = null; 
		ArrayList<String> ansList = null;
		PredictorMaker pm = null;
		HashMap<String, Object> userData = new HashMap<String, Object>();
		try {
		PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
		
		//로그인 상태 확인
		if("anonymousUser".equals(user_id) || "".equals(user_id)|| user_id == null) {
			//로그인이 안되있는 경우
			resultPage = "login/loginForm";
			return resultPage;
		}else {
			//로그인이 되어있는 경우
			//db에 데이터 저장하기
			pm = new PredictorMaker();
			//각 문제의 마지막 번호 
			endNumQuestion = dao.selectQuestionEndNumDao();
			//사용자 정보 저장
			userData.put("age", 24);
			userData.put("sex", "male");
			userData.put("occupation", "yes");
			/*
			 * 설문조사 제출 알고리즘
			 * 1. 설문조사 제출된 정보를 받아온다. 
			 * 2. 제출된 정보를 db에 하나씩 저장한다.
			      - res_id : 자동생성
			        res_userId : 로그인된 사용자 id 저장
			        res_questionId : 질문 id를 받아서 저장
			        res_questionAnswer : 질문에 맞는 답안 저장
			        res_status : 질문 유형(ex 001)을 이용해서 'progress' db를 통해 count+1 을 저장
			        res_date : 자동생성  
			 * 3. 만약, 해당항목의 설문조사를 마친 경우(문제번호가 해당항목의 마지막일 경우, %이용)
			        1. user_id와 문제 종목과 last_status를 이용해서 정보를 'predictor' 클래스로 보낸다.
			        2. 예측 결과와 확률을 받아와서 'prediction' db에 정보를 저장한다. 
			        
			 */
			
			//1
			int question1 = Integer.parseInt(request.getParameter("question1"));
			String id_question1 = request.getParameter("id_question1");
			int question2 = Integer.parseInt(request.getParameter("question2"));
			String id_question2 = request.getParameter("id_question2");
			int question3 = Integer.parseInt(request.getParameter("question3"));
			String id_question3 = request.getParameter("id_question3");
			int question4 = Integer.parseInt(request.getParameter("question4"));
			String id_question4 = request.getParameter("id_question4");
			int question5 = Integer.parseInt(request.getParameter("question5"));
			String id_question5 = request.getParameter("id_question5");
			
			//2
			Map idSortMap = new HashMap();
			idSortMap.put("pre_userId", user_id);
			Map tempMap = new HashMap();
			tempMap.put("res_userId", user_id);
			
			int depStatus = 0;
			int panStatus = 0;
			int perStatus = 0;
			int dysStatus = 0;
			int intStatus = 0;
			//문제 출제 방식
			int sortNum = Integer.parseInt(request.getParameter("sort"));
			if(sortNum == 0) {
				//auto인 경우
				//첫 번째 문제
				idSortMap.put("pre_sort", "depression");
				depStatus = dao.countStateWithIdAndSortDao(idSortMap)+1;
				resDto = new ResultDto(user_id, id_question1, question1, depStatus);
				dao.insertSurveyAnswerDao(resDto);
				//두 번째 문제
				idSortMap.replace("pre_sort", "Panic disorder");
				panStatus = dao.countStateWithIdAndSortDao(idSortMap)+1;
				resDto = new ResultDto(user_id, id_question2, question2, panStatus);
				dao.insertSurveyAnswerDao(resDto);
				//세 번째 문제
				idSortMap.replace("pre_sort", "Personality disorder");
				perStatus = dao.countStateWithIdAndSortDao(idSortMap)+1;
				resDto = new ResultDto(user_id, id_question3, question3, perStatus);
				dao.insertSurveyAnswerDao(resDto);
				//네 번째 문제
				idSortMap.replace("pre_sort", "Dyslepsia");
				dysStatus = dao.countStateWithIdAndSortDao(idSortMap)+1;
				resDto = new ResultDto(user_id, id_question4, question4, dysStatus);
				dao.insertSurveyAnswerDao(resDto);
				//다섯 번째 문제
				idSortMap.replace("pre_sort", "Internet addiction");
				intStatus = dao.countStateWithIdAndSortDao(idSortMap)+1;
				resDto = new ResultDto(user_id, id_question5, question5, intStatus);
				dao.insertSurveyAnswerDao(resDto);
				//3. 검사하기 (각 유형의 문제를 끝마친 경우)
					
				if(String.valueOf(endNumQuestion.get("qen_depressionENum")).equals(String.valueOf(Integer.parseInt(id_question1.substring(3, 6))))) {
					System.out.println("캬하나나하낳나하나5");
					//우울증 검사를 마친 경우 -> 예측결과를 받아서 db에 저장함
					tempMap.put("res_questionId", "001%");
					tempMap.put("res_status", depStatus);
					pm.depressionPredictor(dao.selectAnswerDao(tempMap), userData);
					//우울증 결과 db에 저장시키기
					Map preMap2 = new HashMap();
					preMap2.put("pre_userId", user_id);
					preMap2.put("pre_sort", "depression");
					preMap2.put("pre_result", pm.getF_result());
					preMap2.put("pre_probability", pm.getF_posibility());
					dao.insertPredictDao(preMap2);
				}	
//				}else if(String.valueOf(endNumQuestion.get("qen_panicENum")).equals(String.valueOf(Integer.parseInt(id_question2.substring(3, 6))))) { 
//					//공황장애 끝낸 경우
//					tempMap.put("res_questionId", id_question2.substring(0,3)+"%");
//					tempMap.put("res_status", panStatus);
//					pm.depressionPredictor(dao.selectAnswerDao(tempMap), userData); //수정해야 함
//					//우울증 결과 db에 저장시키기
//					Map preMap2 = new HashMap();
//					preMap2.put("pre_userId", user_id);
//					preMap2.put("pre_sort", "depression");
//					preMap2.put("pre_result", pm.getResult());
//					preMap2.put("pre_probability", pm.getPosibility());
//					dao.insertPredictDao(preMap2); 
//				}else if(String.valueOf(endNumQuestion.get("qen_personalityENum")).equals(String.valueOf(Integer.parseInt(id_question3.substring(3, 6))))) {
//					//성격장애 끝낸 경우
//					tempMap.put("res_questionId", id_question3.substring(0,3)+"%");
//					tempMap.put("res_status", perStatus);
//					pm.depressionPredictor(dao.selectAnswerDao(tempMap), userData); //수정해야 함
//					//성격장애 결과 db에 저장시키기
//					Map preMap2 = new HashMap();
//					preMap2.put("pre_userId", user_id);
//					preMap2.put("pre_sort", "Personality disorder");
//					preMap2.put("pre_result", pm.getResult());
//					preMap2.put("pre_probability", pm.getPosibility());
//					dao.insertPredictDao(preMap2);
//				}else if(String.valueOf(endNumQuestion.get("qen_dyslepsiaENum")).equals(String.valueOf(Integer.parseInt(id_question4.substring(3, 6))))) {
//					//수면장애 끝낸 경우
//					tempMap.put("res_questionId", id_question4.substring(0,3)+"%");
//					tempMap.put("res_status", dysStatus);
//					pm.depressionPredictor(dao.selectAnswerDao(tempMap), userData); //수정해야 함
//					//수면장애 결과 db에 저장시키기
//					Map preMap2 = new HashMap();
//					preMap2.put("pre_userId", user_id);
//					preMap2.put("pre_sort", "Dyslepsia");
//					preMap2.put("pre_result", pm.getResult());
//					preMap2.put("pre_probability", pm.getPosibility());
//					dao.insertPredictDao(preMap2);
//				}else if(String.valueOf(endNumQuestion.get("qen_internetENum")).equals(String.valueOf(Integer.parseInt(id_question5.substring(3, 6))))) {
//					//인터넷중독 끝낸 경우
//					tempMap.put("res_questionId", id_question5.substring(0,3)+"%");
//					tempMap.put("res_status", intStatus);
//					pm.depressionPredictor(dao.selectAnswerDao(tempMap), userData); //수정해야 함
//					//인터넷중독 결과 db에 저장시키기
//					Map preMap2 = new HashMap();
//					preMap2.put("pre_userId", user_id);
//					preMap2.put("pre_sort", "Internet addiction");
//					preMap2.put("pre_result", pm.getResult());
//					preMap2.put("pre_probability", pm.getPosibility());
//					dao.insertPredictDao(preMap2); 
//				}
				
			}

		}

		}catch(Exception ex) {
			resultPage = "cmmn/saveDataFailure";
			System.out.println(ex.getMessage());
			System.out.println(ex.getCause());
		}
		return resultPage;
	}	
	
	

	@RequestMapping("/PredictorMaker") 
	public String PredictorMaker(Locale locale, Model model, HttpServletRequest request) throws Exception {
		String resultPage = "content1";
		try {
			PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
			int[] temp = {3,4,3,3,4,2,5,5,3,4,4,3,5,1,5};
			ArrayList<Integer> temp2 = new ArrayList<Integer>(
					Arrays.asList(3,4,3,3,4,2,5,5,3,4,4,3,5,1,5));
			
			HashMap<String, Object> userData = new HashMap<String, Object>();
			userData.put("age", 24);
			userData.put("sex", "male");
			userData.put("occupation", "yes");
			
			PredictorMaker pm = new PredictorMaker();
			pm.depressionPredictor(temp2, userData);
			//System.out.println("결과 : " + pm.getF_result());
			//System.out.println("확률 : " + pm.getF_posibility());
			
			Map dd = new HashMap();
			dd.put("res_userId", "limjihun204");
			dd.put("res_questionId", "001%");
			dd.put("res_status", "1");
			//System.out.println("test : \n" + dao.selectAnswerDao(dd));
			
		}catch(Exception ex) {
			resultPage = "cmmn/saveDataFailure";
			System.out.println(ex.getStackTrace());
		}
		return resultPage;
	}
	
	
	@RequestMapping("/signUpForm") 
	public String signUpForm(Locale locale, Model model, HttpServletRequest request) throws Exception {
		String resultPage = "login/signUpForm";
		return resultPage;
	}
	
	@RequestMapping("/settingForm") 
	public String settingForm(Locale locale, Model model, HttpServletRequest request) throws Exception {
		String resultPage = "setting/settingForm";
		String user_id = "";
		List<UserDto> manager_list = new ArrayList<UserDto>();
		try {
			user_id = SecurityContextHolder.getContext().getAuthentication().getName().toString();
			PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
			if("anonymousUser".equals(user_id) || "".equals(user_id)|| user_id == null) {
				//로그인이 안되있는 경우
				manager_list = null;
				model.addAttribute("manager_list", manager_list);
			}else {
				//로그인이 되어있는 경우
				UserDto userInfo = dao.selectAllUserWithIdDao(SecurityContextHolder.getContext().getAuthentication().getName().toString());
				String[] arrManager = userInfo.getUser_manager().split(",");
				for(String data : arrManager) {
					if(!"".equals(data)) 
						manager_list.add(dao.selectAllUserWithIdDao(data));
				}
				model.addAttribute("manager_list", manager_list);
			}
				
				
		}catch(Exception ex) {
			resultPage = "cmmn/saveDataFailure";
			System.out.println(ex.getMessage());
		}
		return resultPage;
	}
	
	@RequestMapping("/checkSignUp") 
	public String checkSignUp(Locale locale, Model model, HttpServletRequest request) throws Exception {
		String resultPage = "cmmn/saveSignUpSuccess";
		
		try {
			PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
			String user_id = request.getParameter("user_id");
			String user_pwd = request.getParameter("user_pwd");
			String user_name = request.getParameter("user_name");
			String user_sex = request.getParameter("user_sex");
			String user_age = request.getParameter("user_age");
			String user_birthday_year = request.getParameter("birthday_year");
			String user_birthday_month = request.getParameter("birthday_month");
			String user_birthday_day = request.getParameter("birthday_day");
			String user_birthday = user_birthday_year+user_birthday_month+user_birthday_day;
			
			String user_occupation = request.getParameter("user_occupation");
			String user_manager = request.getParameter("user_manager");
			
			UserDto user = new UserDto(user_id, user_pwd, user_name, user_sex, user_age, user_birthday, user_occupation, user_manager);
			dao.insertUserDao(user);
			
			
		}catch(Exception ex) {
			resultPage = "cmmn/saveSignUpFailure";
			System.out.println(ex.getMessage());
		}
		return resultPage;
	}
	
	 @RequestMapping(value = "/checkDuplicateId", method = RequestMethod.POST)
	    public String checkDuplicateId(HttpServletRequest request, Model model) {
		 	PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
	        String id = request.getParameter("id");
	        int rowcount = dao.checkDuplicateIdDao(id);
	        return String.valueOf(rowcount);
	    }
	 
	 
	 @RequestMapping("/deleteManager") 
	 public String deleteManager(HttpServletRequest request, Model model) {
		 String resultPage = "cmmn/successTaskWithSettingForm";
		 String newManager = "";
		 Map<String, String> tempMap = new HashMap<String, String>();
		 try {
			 PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
			 String manager_id = request.getParameter("manager_id");
			 manager_id = manager_id.trim();
			 //아래에 manager_id 겁색해서 없을 시도 오류 발생
			 if("".equals(manager_id) || dao.checkDuplicateIdDao(manager_id) <= 0) {
				 return "cmmn/saveDataFailure";
			 }
			 UserDto userInfo = dao.selectAllUserWithIdDao(SecurityContextHolder.getContext().getAuthentication().getName().toString());
				List<UserDto> manager_list = new ArrayList<UserDto>();
				String[] arrManager = userInfo.getUser_manager().split(",");
				for(String data : arrManager) {
					data = data.trim();
					if(!manager_id.equals(data)) {
						newManager += data;
						newManager += ",";
					}
				}
			//마지막 문자의 콤마 제거
			if(newManager.length() > 0)
				newManager = newManager.substring(0, newManager.length()-1);
			tempMap.put("user_manager", newManager);
			tempMap.put("user_id", SecurityContextHolder.getContext().getAuthentication().getName().toString());
			dao.updateManagerWithIdDao(tempMap);
		
		 }catch(Exception ex) {
			 resultPage = "cmmn/saveDataFailure";
			 System.out.println(ex.getCause());
		 }
		 
         return resultPage;
     }

	 
	 @RequestMapping("/checkNewManager") 
	 public String checkNewManager(HttpServletRequest request, Model model) {
		 String resultPage = "cmmn/successTaskWithSettingForm";
		 Map<String, String> tempMap = new HashMap<String,String>();
		 String user_id = "";
		 String managers = "";
		 try {
	 	 PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
	 	 user_id = SecurityContextHolder.getContext().getAuthentication().getName().toString();
         String manager_id = request.getParameter("manager_id");
         manager_id = manager_id.trim();
         //아래에 manager_id 겁색해서 없을 시도 오류 발생
		 if("".equals(manager_id) || dao.checkDuplicateIdDao(manager_id) <= 0 || user_id.equals(manager_id)) {
			 return "cmmn/saveManagerFailure";
		 }
		 UserDto userInfo = dao.selectAllUserWithIdDao(user_id);
		 if("".equals(userInfo.getUser_manager()))
			 managers = (manager_id).trim();
		 else 
			 managers = (userInfo.getUser_manager() + "," + manager_id).trim();
		 
         tempMap.put("user_manager", managers);
         tempMap.put("user_id", user_id);
         dao.updateManagerWithIdDao(tempMap);
		 }catch(Exception ex) {
			 resultPage = "cmmn/saveDataFailure";
		 }
         return resultPage;
     }
	
	 
	 @RequestMapping("/checkNewPassword") 
	 public String checkNewPassword(HttpServletRequest request, Model model) {
		 String resultPage = "cmmn/successTaskWithSettingForm";
		 Map<String, String> tempMap = new HashMap<String,String>();
		 String user_id = "";
		 try {
	 	 PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
	 	 user_id = SecurityContextHolder.getContext().getAuthentication().getName().toString();
         String user_pwd = request.getParameter("user_pwd");
         tempMap.put("user_pwd", user_pwd);
         tempMap.put("user_id", user_id);
         dao.updatePwdWithIdDao(tempMap);
         
		 }catch(Exception ex) {
			 resultPage = "cmmn/saveDataFailure";
		 }
         return resultPage;
     }
	 
	@RequestMapping("/depInfo") 
	public String depInfo(Locale locale, Model model, HttpServletRequest request) throws Exception {
		String resultPage = "info/depInfo";
		return resultPage;
	}
	
	@RequestMapping("/panInfo") 
	public String panInfo(Locale locale, Model model, HttpServletRequest request) throws Exception {
		String resultPage = "info/panInfo";
		return resultPage;
	}
	
	@RequestMapping("/perInfo") 
	public String perLayer(Locale locale, Model model, HttpServletRequest request) throws Exception {
		String resultPage = "info/perInfo";
		return resultPage;
	}
	
	@RequestMapping("/insInfo") 
	public String insLayer(Locale locale, Model model, HttpServletRequest request) throws Exception {
		String resultPage = "info/insInfo";
		return resultPage;
	}
	
	@RequestMapping("/intInfo") 
	public String intLayer(Locale locale, Model model, HttpServletRequest request) throws Exception {
		String resultPage = "info/intInfo";
		return resultPage;
	}
	
	@RequestMapping("/android") 
	public void androidTest(Locale locale, Model model, HttpServletRequest request) throws Exception {
		System.out.println("Android로 접급했습니다.");
		 PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
		 request.setCharacterEncoding("UTF-8");
		String tempStr = request.getParameter("test");
		dao.insertTestDao(tempStr);
		
	}
	
}

