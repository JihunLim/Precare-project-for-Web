package com.wherever.precareweb;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

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
		//use this under code when you use db.
		PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
		String user_id= request.getParameter("user_id"); 
		System.out.println("userid 1 : " + user_id);
		if(user_id == null || "".equals(user_id) || "anonymousUser".equals(user_id)) {
			//get current login user's infomation
			System.out.println("good ");
			user_id= SecurityContextHolder.getContext().getAuthentication().getName().toString(); 
		}
		System.out.println("userid 2 : " + user_id);
		String userName = dao.selectUserNameWithIdDao(user_id);
		if(userName == null || "".equals(userName)) {
			page = "cmmn/notFoundUser";
		}
		model.addAttribute("user_name", userName);
		System.out.println("username : " + dao.selectUserNameWithIdDao(user_id));
		return page;
	}
	
	
	@RequestMapping("/test") 
	public String test(Locale locale, Model model) throws Exception {
		//use this under code when you use db.
		PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
		//getUserInfo(model);
		model.addAttribute("data", dao.selectUserNameWithIdDao("limjihun204"));
		System.out.print("관리자페이지");
		return "manager/test";
	}
	
	
}

