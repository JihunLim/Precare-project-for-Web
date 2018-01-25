<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<%String cp= request.getContextPath(); %>
<%@ page session="false" %>

<!--
	Wherever Team Web Project for MS Imagine cup 2018
-->
<!DOCTYPE HTML>
<html>
	<head>
		<title>Precare</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<!--[if lte IE 8]><script src="<%=cp%>/resources/assets/js/ie/html5shiv.js"></script><![endif]-->
		<link rel="stylesheet" href="<%=cp%>/resources/assets/css/main.css" />
		<!--[if lte IE 8]><link rel="stylesheet" href="<%=cp%>/resources/assets/css/ie8.css" /><![endif]-->
	</head>
	<body class="homepage">
		<div id="page-wrapper">
			
			<!-- Header -->
				<div id="header">
				
					<!-- Inner -->
						<div class="inner">
							<header>
								<h1><span id="logo" color="white" style="font-size:1em;">User ID :
								<input type="text" style="border:1px solid white; background-color:transparent;font-size:1em; color:white; font-weight:bold;" 
									value='${user_id}' onfocus="this.style.color=white" name="userId" size=15/></span></h1>
								<hr />
								<p>You can check it out to other result!</p>
							</header>
							<footer>
								<a href="#" class="button circled scrolly" onclick="javascript:go2Post(document.getElementsByName('userId')[0].value);">Search!</a>
							</footer>
						</div>

					<!-- Nav -->
						<nav id="nav">
							<ul>
								<li><a href="home"><span class="icon fa-home"></span>Home</a></li>
								<li><a href="showResult"><span class="icon fa-universal-access"></span>Management</a></li>
								
								<s:authorize access="isAuthenticated()">
                           			<li><a href="${pageContext.request.contextPath}/j_spring_security_logout"> <span class="icon fa-sign-out"></span>[Log Out]</a></li>
                       			</s:authorize>
                       			
                       			<s:authorize access="isAnonymous()">
                           			<li><a href="loginForm"><span class="icon fa-sign-in"></span>Login</a></li>
                       			</s:authorize>
					
							</ul>
						</nav>
				</div>
				
			<!-- Banner -->
				<div class="wrapper style1" style="background-color: #D8D8D8; background-repeat:no-repeat;">
					<section id="features" class="container special" >
						<header>
							<h2>Hi!  <strong>${user_name}</strong>.</h2>
							<p>
								<!-- 예측된 결과가 없을 경우 -->
								<c:if test="${prediction_count <= 0}"> 
									<h2>"Oops! You haven't finished the survey yet."</h2>
								</c:if>
								<!-- 예측된 결과가 있을 경우 -->
								<c:if test="${prediction_count > 0}"> 
									<c:if test="${target_prediction.pre_result eq 'yes'}"> 
										<h2>Recently, you have been suffering from <span style="color: blue;"><strong>${target_prediction.pre_sort}</strong></span>.</h2>
									</c:if>
									<c:if test="${target_prediction.pre_result eq 'no'}"> 
										<h2>Congratulations! You are mentally sound great without <strong><span color="blue">${target_prediction.pre_sort}</span></strong>.</h2> 
									</c:if>
								</c:if>
							</p>
						</header>
					</section>
				</div>
		
			<!-- Main -->
				<div>
					<section id="banner">
						<article id="main" class="container special">
							<header>
								<h2>Past Prediction Results</h2>
							</header>
							<table width="800" cellpadding="0" cellspacing="0" border="1">
								<thead>
									<tr style="background: #eee; border-bottom: 3px inset #ccc;" >
										<th>date</th>
										<th>survey title</th>
										<th>result</th>
										<th>Severity</th>
										<th>survey content</th>
									<th></th>
									</tr>
								</thead>
								<c:forEach items="${prediction_list}" var="dto">
								<tbody>
									<tr style="text-align:center;cursor:pointer;" onclick="javascript:go2PostWithResult('${dto.pre_id}');" class="highlight">
										<td style="border-right: 1px solid #ccc;border-left: 0px solid #ccc;">${dto.pre_date}</td>
										<td>${dto.pre_sort}</td>
										<td>${dto.pre_result}</td>
										<!-- result가 no면 없음, result가 yes면 -> 가능성이 30아래면 약, 60아래면 중, 100아래면 상 -->
										<c:if test="${dto.pre_result eq 'no'}">
											<td>-</td>
										</c:if>
										<c:if test="${dto.pre_result eq 'yes'}">
											<c:if test="${dto.pre_probability <= 30}"><td>low</td></c:if>
											<c:if test="${dto.pre_probability > 30 && dto.pre_probability <=60}"><td>middle</td></c:if>
											<c:if test="${dto.pre_probability > 60 && dto.pre_probability <=100}"><td>high</td></c:if>
										</c:if>
										<td><a href="#" class="skel-layers-ignoreHref"><span class="icon fa-file-text"></span></a></td>
									</tr>
								</tbody>
								</c:forEach>
							</table>
						</article>
					</section>

				</div>

			<!-- Features -->
				<div id= "cure" class="wrapper style1">

					<section id="features" class="container special">
					<!-- None -->
						<!-- 예측된 결과가 없을 경우 -->
							<c:if test="${prediction_count <= 0}"> 
								<header>
									<h2 style="color: black;"> Helpful Solutions for <strong style="color: black;">${user_name}.</strong></h2>
								</header>
								<p align="center" style="text-align:center;">
										        <iframe src="https://www.youtube.com/embed/sKTzZe52nB0" width="640" height="320" frameborder="0" allowfullscreen=""></iframe>
										     </p>
										     <p>You haven't completed the survey yet but, it’s important to take care of yourself and get the most from life. Below are 7 practical ways to look after your mental health. Making simple changes to how you live doesn’t need to cost a fortune or take up loads of time. Anyone can follow this advice. Why not start today?<p />
											 		<ul><strong>Talking about your feelings can help you stay in good mental health and deal with times when you feel troubled.</strong></ul>
										     		<li><strong>Regular exercise can boost your self-esteem and can help you concentrate,</strong></li>
										     		<li><strong>Your brain needs a mix of nutrients in order to stay healthy and function well,</strong></li>
										     		<li><strong>Drink sensibly. Drinking is not a good way to manage difficult feelings.</strong></li>
										     		<li><strong>None of us are superhuman. We all sometimes get tired or overwhelmed by how we feel or when things don’t go to plan.</strong></li>
										     		<li><strong>A change of scene or a change of pace is good for your mental health</strong></li>
										     		<li><strong>Enjoying yourself can help beat stress. Doing an activity you enjoy probably means you’re good at it,</strong></li><br />
										     <span class="icon fa-paper-plane"></span>Referenced Web-page : <a href="https://www.mentalhealth.org.uk/publications/how-to-mental-health" target="_blank">Mental Health Foundation</a>
								
							</c:if>
							<!-- 예측된 결과가 있을 경우 -->
							<c:if test="${prediction_count > 0}"> 
								<c:if test="${target_prediction.pre_result eq 'yes'}"> 
									<header>
										<h2 style="color: black;"> Helpful Solutions for <strong style="color: black;">${user_name}.</strong></h2>
									</header>
									<p>
										<!-- depression -->
										<c:if test="${target_prediction.pre_sort eq 'depression'}"> 
											 <p align="center" style="text-align:center;">
										        <iframe src="https://www.youtube.com/embed/xKJxxq74c-8" width="640" height="320" frameborder="0" allowfullscreen=""></iframe>
										     </p> 
										     <p>Depression drains your energy, hope, and drive, making it difficult to take the steps that will help you to feel better. But while overcoming depression isn’t quick or easy, it’s far from impossible. You can’t just will yourself to “snap out of it,” but you do have more control than you realize—even if your depression is severe and stubbornly persistent. The key is to start small and build from there. Feeling better takes time, but you can get there by making positive choices for yourself each day.<p />
											 
											 		<ul><strong>There are some tips to overcome depression.</strong></ul>
										     		<li><strong>Look for support from people who make you feel safe and cared for.</strong></li>
										     		<li><strong>Make face-time a priority.</strong></li>
										     		<li><strong>Try to keep up with social activities even if you don’t feel like it. </strong></li>
										     		<li><strong>Find ways to support others.</strong></li>
										     		<li><strong>Care for a pet.</strong></li>
										     		<li><strong>Join a support group for depression.</strong></li><br />
										     <span class="icon fa-paper-plane"></span>Referenced Web-page : <a href="https://www.helpguide.org/articles/depression/coping-with-depression.htm" target="_blank">HELPGUIDE.ORG</a>	
										</c:if>
										<!-- Panic disorder -->
										<c:if test="${target_prediction.pre_sort eq 'Panic disorder'}">
											 <p align="center" style="text-align:center;">
										        <iframe src="https://www.youtube.com/embed/PCjsCeszgSg" width="640" height="320" frameborder="0" allowfullscreen=""></iframe>
										     </p> 
										     <p>Panic attacks are terrifying and debilitating, and they can happen to anyone. There's usually not much you can do other than wait it out, but these are the tricks some people have found helpful — physically, emotionally, or otherwise — for dealing with panic attacks.<p />
											 
											 		<ul><strong>Listen to songs that will help you control your breathing rhythm.</strong></ul>
										     		<li><strong>Play with a toy that engages your senses.</strong></li>
										     		<li><strong>Count five things you can see, four you can touch, three you can hear, two you can smell, and one you can taste.</strong></li>
										     		<li><strong>Just visualize doing yoga if you're not physically able to.</strong></li>
										     		<li><strong>Play with silly putty.</strong></li>
										     		<li><strong>Have a mantra that you can repeat as you ride out an attack, like "This is not going to kill me."</strong></li>
										     		<li><strong>Concentrate on a funny cartoon when you feel an attack coming on.</strong></li><br />
										     <span class="icon fa-paper-plane"></span>Referenced Web-page : <a href="https://www.buzzfeed.com/annaborges/panic-attacks-at-the-disco?utm_term=.dsbBmzBy7#.jbNNDMNbB" target="_blank">BuzzFeeD</a>	
										</c:if>
										<!-- Personality disorder -->
										<c:if test="${target_prediction.pre_sort eq 'Personality disorder'}"> 
											 <p align="center" style="text-align:center;">
										        <iframe src="https://www.youtube.com/embed/4E1JiDFxFGk" width="640" height="320" frameborder="0" allowfullscreen=""></iframe>
										     </p> 
										     <p>You might be diagnosed with a personality disorder if you have difficulties with how you think and feel about yourself and other people, and are having problems in your life as a result.<p />
											 <p>Having personality disorder is like the emotional version of being a burn victim. Everything in the world hurts more than it seems to for everyone else and any 'thick skin' you are supposed to have just isn't there.</p>
											 
											 		<ul><strong>Talk to someone. It can be hard to reach out when you’re not feeling well, but it might help to share difficult thoughts.</strong></ul>
										     		<li><strong>Keep a mood diary. You could also make a note of what's going well.</strong></li>
										     		<li><strong>Plan for difficult times. If you're feeling less well you might not be able to tell people what help you want, so it could be helpful to plan ahead.</strong></li>
										     		<li><strong>Make a self-care box. You could put together some things that might help you when you’re struggling – a bit like making a first-aid kit for your mental health.</strong></li><br />
										     <span class="icon fa-paper-plane"></span>Referenced Web-page : <a href="https://www.mind.org.uk/information-support/types-of-mental-health-problems/borderline-personality-disorder-bpd/self-care-for-bpd/#.WmdEbahl-Uk" target="_blank">MIND</a>	 
										</c:if>
										<!-- insomnia -->
										<c:if test="${target_prediction.pre_sort eq 'insomnia'}"> 
											 <p align="center" style="text-align:center;">
										        <iframe src="https://www.youtube.com/embed/s3vaI15ICQg" width="640" height="320" frameborder="0" allowfullscreen=""></iframe>
										     </p> 
										     <p>Getting a good night’s sleep may seem like an impossible goal when you’re wide awake at 3 a.m., but you have much more control over the quality of your sleep than you probably realize. Just as how you feel during your waking hours often hinges on how well you sleep at night, so the cure for sleep difficulties can often be found in your daily routine.<p />
										     <p>Unhealthy daytime habits and lifestyle choices can leave you tossing and turning at night and adversely affect your mood, brain and heart health, immune system, creativity, vitality, and weight. But by experimenting with the following tips to find the ones that work best for you, you can enjoy better sleep at night, improve your mental and physical health, and improve how you think and feel during the day.</p>
											 
											 		<ul><strong>Keep in sync with your body's natural sleep-wake cycle.</strong></ul>
										     		<li><strong>Expose yourself to bright sunlight in the morning.</strong></li>
										     		<li><strong>Exercise during the day.</strong></li>
										     		<li><strong>Be smart about what you eat and drink such as limitting caffeine and nicotine.</strong></li>
										     		<li><strong>Wind down and clear your head.</strong></li>
										     		<li><strong>Keep your room dark, cool, and quiet.</strong></li>
										     		<li><strong>Learn ways to get back to sleep.</strong></li><br />
										     <span class="icon fa-paper-plane"></span>Referenced Web-page : <a href="https://www.helpguide.org/articles/sleep/getting-better-sleep.htm" target="_blank">HELPGUIDE.ORG</a>	
										</c:if>
										<!-- Internet addiction -->
										<c:if test="${target_prediction.pre_sort eq 'Internet addiction'}"> 
											 <p align="center" style="text-align:center;">
										        <iframe src="https://www.youtube.com/embed/vOSYmLER664" width="640" height="320" frameborder="0" allowfullscreen=""></iframe>
										     </p> 
										     <p>Too much time spent online can cause a host of emotional and physical problems, damage personal relationships, and decrease performance at work or school. Nevertheless, Internet addiction is a growing issue. However, if you are struggling with the problem, you can overcome it by taking steps to limit your Internet usage, filling your time with alternative activities, and seeking support.<p />
											 
											 		<ul><strong>Develop a personal inventory of things Internet addiction is keeping you from.</strong></ul>
										     		<li><strong>You can and should decide on an appropriate amount of time to set aside for personal Internet usage.</strong></li>
										     		<li><strong>If Internet usage is taking up too much of your time, you can block the problem by filling your schedule with alternative activities.</strong></li>
										     		<li><strong>Having someone or something interrupt your Internet use can be very effective.</strong></li>
										     		<li><strong>Internet addiction can be curtailed if online activities are put in perspective in comparison with the rest of your life.</strong></li>
										     		<li><strong>Abstain from any particular problem apps, sites, and habits.</strong></li>
										     		<li><strong>Creating visual reminders your Internet addiction and your determination to stop it can be a powerful way of cutting back time spent online.</strong></li><br />
										     <span class="icon fa-paper-plane"></span>Referenced Web-page : <a href="https://www.wikihow.com/Overcome-Internet-Addiction" target="_blank">WikiHow</a>	
										</c:if>
									</p>
								</c:if>
								<c:if test="${target_prediction.pre_result eq 'no'}"> 
									<p align="center" style="text-align:center;">
										        <iframe src="https://www.youtube.com/embed/sKTzZe52nB0" width="640" height="320" frameborder="0" allowfullscreen=""></iframe>
										     </p>
										     <p>Congratulations! It is amazing to keep a good mental health in modern society.<p> 
										     <p>It’s important to take care of yourself and get the most from life. Below are 7 practical ways to look after your mental health. Making simple changes to how you live doesn’t need to cost a fortune or take up loads of time. Anyone can follow this advice. Why not start today?<p />
											 
											 		<ul><strong>Talking about your feelings can help you stay in good mental health and deal with times when you feel troubled.</strong></ul>
										     		<li><strong>Regular exercise can boost your self-esteem and can help you concentrate,</strong></li>
										     		<li><strong>Your brain needs a mix of nutrients in order to stay healthy and function well,</strong></li>
										     		<li><strong>Drink sensibly. Drinking is not a good way to manage difficult feelings.</strong></li>
										     		<li><strong>None of us are superhuman. We all sometimes get tired or overwhelmed by how we feel or when things don’t go to plan.</strong></li>
										     		<li><strong>A change of scene or a change of pace is good for your mental health</strong></li>
										     		<li><strong>Enjoying yourself can help beat stress. Doing an activity you enjoy probably means you’re good at it,</strong></li><br />
										     <span class="icon fa-paper-plane"></span>Referenced Web-page : <a href="https://www.mentalhealth.org.uk/publications/how-to-mental-health" target="_blank">Mental Health Foundation</a>
									
								</c:if>
							</c:if>
						
						<div class="row">
						
						
							<article class="12u 12u(mobile) special">
								
								
								
								<header>
									
								</header>
								
								
								<p>
									<h3 style="color: black;">Manager's Comment : </h3>
									<div class="row">
										<div class="12u$ 12u$(mobile)">
										<textarea name="comment" placeholder="" style="width:100%;height:200px;border:1;overflow:visible;text-overflow:ellipsis;"
											 <c:if test="${login_id eq user_id}"> readonly </c:if>>${target_prediction.pre_comment}</textarea>										
										</div>
									</div>
									
									<c:if test="${login_id ne user_id}">  
										<footer>
											<a href="#" class="button circled scrolly" onclick="javascript:go2PostWithComment();">Submit</a>
										</footer>
									</c:if>
							</article>
							
						</div>
					</section>

				</div>

			<!-- Footer -->
				<div id="footer">
					<div class="container">

						<div class="row">
							<div class="12u">

								<!-- Contact -->
									<section class="contact">
										<header>
											<h3>Wherever Team in Korea!</h3>
										</header>
										<p>Precare project for Microsoft Imagine cup 2018.</p>
									</section>

								<!-- Copyright -->
									<div class="copyright">
										<ul class="menu">
											<li>&copy; Wherever Team. All rights reserved.</li><li>Developed by: Jihun Lim & Junghun Kim & Jungsoo Lee </a></li>
										</ul>
									</div>
							</div>
						</div>
					</div>
				</div>

		</div>

		<!-- Scripts -->
			<script src="<%=cp%>/resources/assets/js/jquery.min.js"></script>
			<script src="<%=cp%>/resources/assets/js/jquery.dropotron.min.js"></script>
			<script src="<%=cp%>/resources/assets/js/jquery.scrolly.min.js"></script>
			<script src="<%=cp%>/resources/assets/js/jquery.onvisible.min.js"></script>
			<script src="<%=cp%>/resources/assets/js/skel.min.js"></script>
			<script src="<%=cp%>/resources/assets/js/util.js"></script>
			<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
			<script src="<%=cp%>/resources/assets/js/main.js"></script>
			<script>
		    	 function go2Post(value){
		    	 var form = document.createElement("form");
		
		         form.action = "showResult";
		         form.method = "post";
		
		         var hiddenField = document.createElement("input");
		         hiddenField.setAttribute("type", "hidden");
		         hiddenField.setAttribute("name", "user_id");
		         hiddenField.setAttribute("value", document.getElementsByName('userId')[0].value);
		         //hiddenField.setAttribute("value", value);
		         form.appendChild(hiddenField);
		         document.body.appendChild(form);
		         form.submit();
    			 }
    		</script>
    		<script>
		    	 function go2PostWithComment(){
		    	 var form = document.createElement("form");
		
		         form.action = "updateComment";
		         form.method = "post";
		
		         var hiddenField = document.createElement("input");
		         hiddenField.setAttribute("type", "hidden");
		         hiddenField.setAttribute("name", "comment");
		         hiddenField.setAttribute("value", document.getElementsByName('comment')[0].value);
		         
		         var hiddenField2 = document.createElement("input");
		         hiddenField2.setAttribute("type", "hidden");
		         hiddenField2.setAttribute("name", "pre_id");
		         hiddenField2.setAttribute("value", "${prediction_list[0].pre_comment}");
		         
		         form.appendChild(hiddenField);
		         document.body.appendChild(form);
		         form.submit();
    			 }
    		</script>
    		<script>
		    	 function go2PostWithResult(value){
		    	 var form = document.createElement("form");
		
		         form.action = "showResult";
		         form.method = "post";
		
		         var hiddenField = document.createElement("input");
		         hiddenField.setAttribute("type", "hidden");
		         hiddenField.setAttribute("name", "target_preId");
		         hiddenField.setAttribute("value", value);
		         form.appendChild(hiddenField);
		         document.body.appendChild(form);
		         form.submit();
    			 }
    		</script>
    		<script type="text/javascript">
  $(window).resize(function(){resizeYoutube();});
  $(function(){resizeYoutube();});
  function resizeYoutube(){ $("iframe").each(function(){ if( /^https?:\/\/www.youtube.com\/embed\//g.test($(this).attr("src")) ){ $(this).css("width","100%"); $(this).css("height",Math.ceil( parseInt($(this).css("width")) * 480 / 854 ) + "px");} }); }
</script>



    		

	</body>

</html>