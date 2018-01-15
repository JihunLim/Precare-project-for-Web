package com.wherever.precareweb;

import java.text.DateFormat;
import java.util.ArrayList;
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
		String nextDyslepsiaId = "";
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
			nextDyslepsiaId = "004001";
			nextInternetaddictionId = "005001";
		}else {
			//로그인이 되어있는 경우
			//설문조사 모드 설정
			queMode = userInfo.getUser_questionMode();
			//우울증
			int temp = ((progressInfo.getPro_depressionState())!=null)?(Integer.parseInt(progressInfo.getPro_depressionState().substring(3, 6)) + 1):1;
			String strTemp = "";
			if(temp % Integer.parseInt(queEndNum.get("qen_depressionENum").toString()) == 0) {
				//문제를 다 푼 경우
				strTemp = "001";
			}else {
				//문제를 다 풀지 못한 경우 -> temp 숫자를 3자리의 문자열로 변경
				strTemp = String.format("%03d", temp);
			}
			nextDepressionId = "001" + strTemp;
			
			//공황장애
			temp = ((progressInfo.getPro_panicdisorderState())!=null)?(Integer.parseInt(progressInfo.getPro_panicdisorderState().substring(3, 6)) + 1):1;
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
			temp = ((progressInfo.getPro_personalitydisorderState())!=null)?(Integer.parseInt(progressInfo.getPro_personalitydisorderState().substring(3, 6)) + 1):1;
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
			temp = ((progressInfo.getPro_dyslepsiaState())!=null)?(Integer.parseInt(progressInfo.getPro_dyslepsiaState().substring(3, 6)) + 1):1;
			strTemp = "";
			if(temp % Integer.parseInt(queEndNum.get("qen_dyslepsiaENum").toString()) == 0) {
				//문제를 다 푼 경우
				strTemp = "001";
			}else {
				//문제를 다 풀지 못한 경우 -> temp 숫자를 3자리의 문자열로 변경
				strTemp = String.format("%03d", temp);
			}
			nextDyslepsiaId = "004" + strTemp;
			
			//인터넷중독
			temp = ((progressInfo.getPro_internetaddictionState())!=null)?(Integer.parseInt(progressInfo.getPro_internetaddictionState().substring(3, 6)) + 1):1;
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
				System.out.println("test : " + que.getQue_id());
				if("depression".equals(que.getQue_sort())) {
					//1. 우울증 문제 추출 및 전달
					if(nextDepressionId.equals(que.getQue_id())) {
						model.addAttribute("question1", que.getQue_text());
						continue;
					}
				}else if("Panic disorder".equals(que.getQue_sort())) {
					//2. 공황장애 문제 추출 및 전달
					if(nextPanicdisorderId.equals(que.getQue_id())) {
						model.addAttribute("question2", que.getQue_text());
						continue;
					}
				}else if("Personality disorder".equals(que.getQue_sort())) {
					//3. 성격장애 문제 추출 및 전달
					if(nextPersonalitydisorderId.equals(que.getQue_id())) {
						model.addAttribute("question3", que.getQue_text());
						continue;
					}
				}else if("Dyslepsia".equals(que.getQue_sort())) {
					//4. 수면장애 문제 추출 및 전달
					if(nextDyslepsiaId.equals(que.getQue_id())) {
						model.addAttribute("question4", que.getQue_text());
						continue;
					}
				}else if("Internet addiction".equals(que.getQue_sort())) {
					//5. 인터넷중독 문제 추출 및 전달
					if(nextInternetaddictionId.equals(que.getQue_id())) {
						model.addAttribute("question5", que.getQue_text());
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
	
	
	@RequestMapping("/showResult") 
	public String showResult(Locale locale, Model model, HttpServletRequest request) throws Exception {
		String page = "list/resultPage";
		Boolean checkResultToCome = false;
		String managers = null;
		String[] argManagers = null;
		String loginId = SecurityContextHolder.getContext().getAuthentication().getName().toString();
		//use this under code when you use db.
		PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
		String user_id= request.getParameter("user_id"); 
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
		
		//System.out.println("num : "+numPrediction);
		//System.out.println("info : "+predictionList.get(0).getPre_sort());
		//System.out.println("info : "+predictionList.get(0).getPre_result());
		
		
		
		
		
		return page;
	}
	
	
	@RequestMapping("/updateComment") 
	public String updateComment(Locale locale, Model model, HttpServletRequest request) throws Exception {
		String resultPage = "cmmn/saveDataSuccess";
		try {
		PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
		String comment = request.getParameter("comment");
		String pre_id = request.getParameter("pre_id");
		Map pre_map = new HashMap();
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
			
			//String comment = request.getParameter("comment");
			//String pre_id = request.getParameter("pre_id");
			//Map pre_map = new HashMap();
			//pre_map.put("pre_id", pre_id);
			//pre_map.put("comment", comment);
			//dao.updateCommentWithIdDao(pre_map);
		}

		}catch(Exception ex) {
			resultPage = "cmmn/saveDataFailure";
			System.out.println(ex.getStackTrace());
		}
		return resultPage;
	}	
	
}

