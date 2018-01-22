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
								<h1><span id="logo" color="white" style="font-size:1em;">User ID : <input type="text" style="border:1px solid white; background-color:transparent;font-size:1em; color:white; font-weight:bold;" 
								value='${user_id}' onfocus="this.style.color=white" name="userId"/></span></h1>
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
				<section id="banner">
					<header>
						<h2>Hi!  <strong>${user_name}</strong>.</h2>
						<p>
							<!-- 예측된 결과가 없을 경우 -->
							<c:if test="${prediction_count <= 0}"> 
								<h2>"Oops! You haven't finished the survey yet."</h2>
							</c:if>
							<!-- 예측된 결과가 있을 경우 -->
							<c:if test="${prediction_count > 0}"> 
								<c:if test="${prediction_list[0].pre_result eq 'yes'}"> 
									<h2>Recently, you have been suffering from <span style="color: blue;"><strong>${prediction_list[0].pre_sort}</strong></span>.</h2>
								</c:if>
								<c:if test="${prediction_list[0].pre_result eq 'no'}"> 
									<h2>Congratulations! You are mentally sound great without <strong><span color="blue">${prediction_list[0].pre_sort}</span></strong>.</h2> 
								</c:if>
							</c:if>
						</p>
					</header>
				</section>

			<!-- Main -->
				<div>
					<section id="banner">
						<article id="main" class="container special">
							<header>
								<h2>Past Prediction Results</h2>
							</header>
							<table width="800" cellpadding="0" cellspacing="0" border="1">
								<thead>
									<tr style="background: #eee; border-bottom: 3px inset #ccc;">
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
									<tr style="text-align:center;">
										<td bgcolor="#FFFAF0" style="border-right: 1px solid #ccc;border-left: 0px solid #ccc;">${dto.pre_date}</td>
										<td>${dto.pre_sort}</td>
										<td>${dto.pre_result}</td>
										<!-- result가 no면 없음, result가 yes면 -> 가능성이 30아래면 약, 60아래면 중, 100아래면 상 -->
										<c:if test="${dto.pre_result eq 'no'}">
											<td>-</td>
										</c:if>
										<c:if test="${dto.pre_result eq 'yes'}">
											<c:if test="${dto.pre_probability <= 30}"><td>low</td></c:if>
											<c:if test="${dto.pre_probability > 30 && dto.probability <=60}"><td>middle</td></c:if>
											<c:if test="${dto.pre_probability > 60 && dto.probability <=100}"><td>high</td></c:if>
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
								<h2 style="color: white;">"We can't give you the answer yet!"</h2>
							</c:if>
							<!-- 예측된 결과가 있을 경우 -->
							<c:if test="${prediction_count > 0}"> 
								<c:if test="${prediction_list[0].pre_result eq 'yes'}"> 
									<h2 style="color: white;"><strong style="color: white;">${user_name}!</strong> I will give you some solutions.</h2>
									<p>
										<!-- depression -->
										<c:if test="${prediction_list[0].pre_sort eq 'depression'}"> 
											<p style="color: white;">Do you have any of the following symptoms, such as suicidal ideation, loss of will, helplessness, fatigue, sleep disorders, sexual dysfunction, loss of concentration, and appetite disorders?
											You need to relax yourself by adjusting your stress, relaxing feelings, meeting your friends.
											And foods such as chocolate, burdock, potatoes, cabbage, mackerel, and banana are very helpful in improving depression.
											</p>
										</c:if>
										<!-- Panic disorder -->
										<c:if test="${prediction_list[0].pre_sort eq 'Panic disorder'}">
											<p style="color: white;">Do you have any of the following symptoms, such as Panic attacks, agoraphobia, tachycardia, palpitations, dyspnea, and sweating?
											You need cognitive-behavior therapy along with taking medication. It is important to find out what you are afraid of and correct it.
											You must visit the hospital and get care.
											</p>
										</c:if>
										<!-- Personality disorder -->
										<c:if test="${prediction_list[0].pre_sort eq 'Personality disorder'}"> 
											<p style="color: white;">Do your friends often talk badly about your personality?
											Personality disorders are caused by the environment in which you live.
											It's hard to accept this, but you need ongoing mental consultation and emotional control of special situations.
											</p> 
										</c:if>
										<!-- insomnia -->
										<c:if test="${prediction_list[0].pre_sort eq 'insomnia'}"> 
											<p style="color: white;">Do you have a negative environment in which you can not sleep?
											If you know that, it is the most important to remove that.
											It is also important to keep the sleep patterns the same all the time, and it is also helpful to listen to calm music.
											Cherries, almonds, and warm milk are good foods for insomnia.
											</p> 
										</c:if>
										<!-- Internet addiction -->
										<c:if test="${prediction_list[0].pre_sort eq 'Internet addiction'}"> 
											<p style="color: white;">Do you use too much internet?
											You should abstain from using the internet for a fixed time.
											It is also helpful to find other hobbies such as exercise.
											You should be careful that the use of the internet doesn't affect your daily life.
											</p> 
										</c:if>
									</p>
								</c:if>
								<c:if test="${prediction_list[0].pre_result eq 'no'}"> 
									<h2 style="color: white;">Always keep your mental health as it is now.</h2> 
								</c:if>
							</c:if>
						
						<div class="row">
						
						
							<article class="12u 12u(mobile) special">
								
								
								
								<header>
									
								</header>
								
								
								<p>
									<h3 style="color: white;">Manager's Comment : </h3>
									<div class="row">
										<div class="12u$ 12u$(mobile)">
										<textarea name="comment" placeholder="" style="width:100%;border:1;overflow:visible;text-overflow:ellipsis;"
											 <c:if test="${login_id eq user_id}"> readonly </c:if>>${prediction_list[0].pre_result}</textarea>										
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
										<p>Urna nisl non quis interdum mus ornare ridiculus egestas ridiculus lobortis vivamus tempor aliquet.</p>
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
    		

	</body>

</html>