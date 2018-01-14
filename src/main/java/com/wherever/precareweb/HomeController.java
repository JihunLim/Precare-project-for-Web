package com.wherever.precareweb;

import java.text.DateFormat;
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
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
		String user_id= SecurityContextHolder.getContext().getAuthentication().getName().toString(); 
		System.out.println("dd : " + user_id);
		if(user_id != null) 
			model.addAttribute("user_name", dao.selectUserNameWithIdDao(user_id));
		else if("anonymousUser".equals(user_id))
			model.addAttribute("user_name", "Guest");
		
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
		return "manager/test";
		}catch(Exception ex) {
			resultPage = "cmmn/saveDataFailure";
			System.out.println(ex.getStackTrace());
		}
		return resultPage;
	}
	
	
}

