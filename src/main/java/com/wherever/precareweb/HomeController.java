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
			//�α����� �ȵ��ִ� ���
			model.addAttribute("user_name", "Guest");
		}else {
			//�α����� �Ǿ��ִ� ���
			model.addAttribute("user_name", dao.selectUserNameWithIdDao(user_id));
			userInfo = dao.selectAllUserWithIdDao(user_id);
			progressInfo = dao.selectAllProgressWithIdDao(user_id);
			queEndNum = dao.selectQuestionEndNumDao();
		}
		
		// ���� -> �α����� �ȵǾ� ���� ��쿡�� ��� �� ���ΰ�?
		
		/*
		 * �������� ���� �˰���
		 * 1. �������� ������ �����´�. (1:auto/2:�����/3:��Ȳ���/4:�������/5:�������/6:���ͳ��ߵ�) -> �⺻ ������ auto
		      - auto�� �� �׸� �� ���� 1���� ������
		 * 2. progress ���̺��� �����Ͽ� ���� ����� �������� ��ȣ�� �� �׸񺰷� �����´�.
		 *    - ��, �ش� ������ ��ȣ�� ������ ��ȣ�� ���(- �� Ǭ �����, % �̿�) 
		        1. ù���� ������ ����
		 * 3. 1������ ���õ� �׸����� 5���� ������ �����´�. 
		 * 4. ������ veiw ������ �ѷ��ش�.  
		 */
		String queMode = "";
		String nextDepressionId = "";
		String nextPanicdisorderId = "";
		String nextPersonalitydisorderId = "";
		String nextDyslepsiaId = "";
		String nextInternetaddictionId = "";
		
		//1. �ֱٿ� ����� ������ �����´�. -> progressInfo
		//2. �� �׸� �� ���� ���� ��ȣ�� ���Ѵ�.
	
		if("anonymousUser".equals(user_id) || "".equals(user_id)|| user_id == null) {
			//�α����� �ȵ��ִ� ���
			//�������� ��� ����
			queMode = "auto";
			nextDepressionId = "001001";
			nextPanicdisorderId = "002001";
			nextPersonalitydisorderId = "003001";
			nextDyslepsiaId = "004001";
			nextInternetaddictionId = "005001";
		}else {
			//�α����� �Ǿ��ִ� ���
			//�������� ��� ����
			queMode = userInfo.getUser_questionMode();
			//�����
			int temp = ((progressInfo.getPro_depressionState())!=null)?(Integer.parseInt(progressInfo.getPro_depressionState().substring(3, 6)) + 1):1;
			String strTemp = "";
			if(temp % Integer.parseInt(queEndNum.get("qen_depressionENum").toString()) == 0) {
				//������ �� Ǭ ���
				strTemp = "001";
			}else {
				//������ �� Ǯ�� ���� ��� -> temp ���ڸ� 3�ڸ��� ���ڿ��� ����
				strTemp = String.format("%03d", temp);
			}
			nextDepressionId = "001" + strTemp;
			
			//��Ȳ���
			temp = ((progressInfo.getPro_panicdisorderState())!=null)?(Integer.parseInt(progressInfo.getPro_panicdisorderState().substring(3, 6)) + 1):1;
			strTemp = "";
			if(temp % Integer.parseInt(queEndNum.get("qen_panicENum").toString()) == 0) {
				//������ �� Ǭ ���
				strTemp = "001";
			}else {
				//������ �� Ǯ�� ���� ��� -> temp ���ڸ� 3�ڸ��� ���ڿ��� ����
				strTemp = String.format("%03d", temp);
			}
			nextPanicdisorderId = "002" + strTemp;
			
			//�������
			temp = ((progressInfo.getPro_personalitydisorderState())!=null)?(Integer.parseInt(progressInfo.getPro_personalitydisorderState().substring(3, 6)) + 1):1;
			strTemp = "";
			if(temp % Integer.parseInt(queEndNum.get("qen_personalityENum").toString()) == 0) {
				//������ �� Ǭ ���
				strTemp = "001";
			}else {
				//������ �� Ǯ�� ���� ��� -> temp ���ڸ� 3�ڸ��� ���ڿ��� ����
				strTemp = String.format("%03d", temp);
			}
			nextPersonalitydisorderId = "003" + strTemp;
			
			//�������
			temp = ((progressInfo.getPro_dyslepsiaState())!=null)?(Integer.parseInt(progressInfo.getPro_dyslepsiaState().substring(3, 6)) + 1):1;
			strTemp = "";
			if(temp % Integer.parseInt(queEndNum.get("qen_dyslepsiaENum").toString()) == 0) {
				//������ �� Ǭ ���
				strTemp = "001";
			}else {
				//������ �� Ǯ�� ���� ��� -> temp ���ڸ� 3�ڸ��� ���ڿ��� ����
				strTemp = String.format("%03d", temp);
			}
			nextDyslepsiaId = "004" + strTemp;
			
			//���ͳ��ߵ�
			temp = ((progressInfo.getPro_internetaddictionState())!=null)?(Integer.parseInt(progressInfo.getPro_internetaddictionState().substring(3, 6)) + 1):1;
			strTemp = "";
			if(temp % Integer.parseInt(queEndNum.get("qen_internetENum").toString()) == 0) {
				//������ �� Ǭ ���
				strTemp = "001";
			}else {
				//������ �� Ǯ�� ���� ��� -> temp ���ڸ� 3�ڸ��� ���ڿ��� ����
				strTemp = String.format("%03d", temp);
			}
			nextInternetaddictionId = "005" + strTemp;		
		}
		
		
		if("auto".equals(queMode)) {
			//��ü ������ �����´�. 
			questionList = dao.selectAllQuestionDao(); 
			//�� ���� �ϳ����� �����´�. 
			for(QuestionDto que : questionList) {
				System.out.println("test : " + que.getQue_id());
				if("depression".equals(que.getQue_sort())) {
					//1. ����� ���� ���� �� ����
					if(nextDepressionId.equals(que.getQue_id())) {
						model.addAttribute("question1", que.getQue_text());
						continue;
					}
				}else if("Panic disorder".equals(que.getQue_sort())) {
					//2. ��Ȳ��� ���� ���� �� ����
					if(nextPanicdisorderId.equals(que.getQue_id())) {
						model.addAttribute("question2", que.getQue_text());
						continue;
					}
				}else if("Personality disorder".equals(que.getQue_sort())) {
					//3. ������� ���� ���� �� ����
					if(nextPersonalitydisorderId.equals(que.getQue_id())) {
						model.addAttribute("question3", que.getQue_text());
						continue;
					}
				}else if("Dyslepsia".equals(que.getQue_sort())) {
					//4. ������� ���� ���� �� ����
					if(nextDyslepsiaId.equals(que.getQue_id())) {
						model.addAttribute("question4", que.getQue_text());
						continue;
					}
				}else if("Internet addiction".equals(que.getQue_sort())) {
					//5. ���ͳ��ߵ� ���� ���� �� ����
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
		//�����ϰ��� �ϴ� ����ڰ� �����ϴ��� Ȯ��
		String userName = dao.selectUserNameWithIdDao(user_id);
		if(userName == null || "".equals(userName)) {
			page = "cmmn/notFoundUser";
			return page;
		}
		
		//�����ϰ��� �ϴ� ����ڰ� �ڽ��̶�� ��ȸ ����
		if(loginId.equals(user_id))
			checkResultToCome = true;
		
		// �����ϰ��� �ϴ� ����ڰ� ��ȸ�Ϸ��� ������� �����ڰ� �´��� Ȯ��
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
		
		//prediction ���� ��������
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
		
		//�α��� ���� Ȯ��
		if("anonymousUser".equals(user_id) || "".equals(user_id)|| user_id == null) {
			//�α����� �ȵ��ִ� ���
			resultPage = "login/loginForm";
			return resultPage;
		}else {
			//�α����� �Ǿ��ִ� ���
			//db�� ������ �����ϱ�
			
			/*
			 * �������� ���� �˰���
			 * 1. �������� ����� ������ �޾ƿ´�. 
			 * 2. ����� ������ db�� �ϳ��� �����Ѵ�.
			      - res_id : �ڵ�����
			        res_userId : �α��ε� ����� id ����
			        res_questionId : ���� id�� �޾Ƽ� ����
			        res_questionAnswer : ������ �´� ��� ����
			        res_status : ���� ����(ex 001)�� �̿��ؼ� 'progress' db�� ���� count+1 �� ����
			        res_date : �ڵ�����  
			 * 3. ����, �ش��׸��� �������縦 ��ģ ���(������ȣ�� �ش��׸��� �������� ���, %�̿�)
			        1. user_id�� ���� ����� last_status�� �̿��ؼ� ������ 'predictor' Ŭ������ ������.
			        2. ���� ����� Ȯ���� �޾ƿͼ� 'prediction' db�� ������ �����Ѵ�. 
			        
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

