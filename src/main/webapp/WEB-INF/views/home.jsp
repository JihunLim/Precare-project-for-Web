
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
		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.1.4/Chart.min.js"></script>
		<script type="text/javascript">
				var box1 = "<b><font color='red'>box1 -> 여기에 관련된 내용 쓰기</font></b>";
				var box2 = "<b><font color='green'>box2 -> 여기에 관련된 내용 쓰기</font></b>";
				
				function boxContextChange(selectedBox){
					var contentView = document.getElementById("contentView");
					contentView.innerHTML = selectedBox;
				}
				
		
			</script>
	</head>
	<body class="homepage">
		<div id="page-wrapper">

			<!-- Header -->
				<div id="header">

					<!-- Inner -->
						<div class="inner">
							<header>
								<h1><a href="index.html" id="logo">Precare</a></h1>
								<hr />
								<p>Hello, ${user_name}!<br /> 
								We can prevent your depression and smartphone addiction before you are sick.</p>
							</header>
							<footer>
								<a href="#banner" class="button circled scrolly">Start</a>
							</footer>
						</div>

					<!-- Nav -->
						<nav id="nav">
							<ul>
								<li><a href="home">Home</a></li>
								<li><a href="showResult">Management</a></li>
								
								<s:authorize access="isAuthenticated()">
                           			<li><a href="${pageContext.request.contextPath}/j_spring_security_logout"> <span class="icon fa-sign-out"></span>[Log Out]</a></li>
                       			</s:authorize>
                       			
                       			<s:authorize access="isAnonymous()">
                           			<li><a href="loginForm">Login</a></li>
                       			</s:authorize>
					
							</ul>
						</nav>
				</div>


			<!-- Banner -->
				<section id="banner" style="background-image: url('../../../resources/images/img_header.jpg');">
					<header>
						<h2>Welcome to the <strong>Precare</strong>.</h2>
						<p>
							We introduce noticeable sites that provide information about </br><strong>depression</strong> and <strong>smartphone addiction</strong> and help to prevent them.
						</p>
					</header>
				</section>

			<!-- Carousel -->
			
	
				<section class="carousel">
					<div class="reel">
						<article>
							<a href="https://www.nimh.nih.gov/health/topics/depression/index.shtml" class="image featured scrolly" onclick="boxContextChange(box1)"><img src="resources/images/NIMH.jpg" alt="" /></a>
							<header>
								<h3><a href="https://www.nimh.nih.gov/health/topics/depression/index.shtml">National Institute of Mental Health</a></h3>
							</header>
							<p>NIMH offers information about mental health and provides opportunities to help people heal.</p>
						</article>

						<article>
							<a href="https://adaa.org/tips#" class="image featured scrolly" onclick="boxContextChange(box2)"><img src="resources/images/WEBMD.jpg" alt="" /></a>
							<header>
								<h3><a href="https://adaa.org/tips#">WebMD</a></h3>
							</header>
							<p>WebMD is an American corporation known as an online publisher of news and information pertaining to human health including many mental health.</p>
						</article>

						<article>
							<a href="https://www.helpguide.org/" class="image featured scrolly" onclick="boxContextChange(box3)"><img src="resources/images/HELPGUIDE.jpg" alt="" /></a>
							<header>
								<h3><a href="https://www.helpguide.org/">HelpGuide</a></h3>
							</header>
							<p>HelpGuide provides mental health information and helps diagnose areas such as smartphone addiction and internet addiction.</p>
						</article>

						<article>
							<a href="https://www.psychologytoday.com/" class="image featured scrolly" onclick="boxContextChange(box4)"><img src="resources/images/PT.jpg" alt="" /></a>
							<header>
								<h3><a href="https://www.psychologytoday.com/">Psychologytoday</a></h3>
							</header>
							<p>Psychologytoday provides articles about health, disease. You can also search for nearby therapists and psychologists.</p>
						</article>

					</div>
				</section>

			<!-- Main (설문조사) -->
			<div class="wrapper style2">
				<article id="main" class="container special">
					<header>
						<h2>Do <strong>survey</strong>.</h2>
					</header>
					
					<p id="contentView">
					
					<!-- 로그인 안되어 있는 경우 -->
					
					<!-- 로그인 되어 있는 경우 -->
					<c:if test="${user_name eq 'Guest'}"> 
					</c:if>
						
							<form name="mainForm" method="post" action="<%=cp%>/checkForm" onsubmit="return checkFormRule()">
							<div class="row">
	
								<div class="4u$ 12u$(mobile)">
									<input type="hidden" name="user_id" value=<s:authentication property="name"/>/>
									<div class="controls">
										<label class="control-label">Survey Topic </label>
										<select name="sort" class="dropdown" onchange="changeValue(this.value)" style="float:left;">
											<option value="1">Auto</option>
											<option value="2">Depression</option>
											<option value="3">Panic disorder</option>
											<option value="4">Personality disorder</option>
											<option value="5">Dyslepsia</option>
											<option value="6">Internet addiction</option>
										</select>
									</div>
								</div>
							</div>
							
							<!-- first question -->
							<br /><br />
							<label class="control-label">Q1. ${question1}</label>
							<div class="btn-group" data-toggle="buttons">
								<label class="btn btn-primary"> <input type="radio" name="os_document" id="option1" value="1" required>Very dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="os_document" id="option2" value="2" required>Somewhat dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="os_document" id="option3" value="3" required>Neutral</label>
								<label class="btn btn-primary"> <input type="radio" name="os_document" id="option3" value="4" required>Somewhat satisfied</label>
								<label class="btn btn-primary"> <input type="radio" name="os_document" id="option3" value="5" required>Very satisfied</label>
							</div>
								
							<!-- Second question -->
							<br /><br />
							<label class="control-label">Q2. ${question2}</label>
							<div class="btn-group" data-toggle="buttons">
								<label class="btn btn-primary"> <input type="radio" name="os_document1" id="option1" value="1" required>Very dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="os_document1" id="option2" value="2" required>Somewhat dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="os_document1" id="option3" value="3" required>Neutral</label>
								<label class="btn btn-primary"> <input type="radio" name="os_document1" id="option3" value="4" required>Somewhat satisfied</label>
								<label class="btn btn-primary"> <input type="radio" name="os_document1" id="option3" value="5" required>Very satisfied</label>
							</div>
							
							<!-- Third question -->
							<br /><br />
							<label class="control-label">Q3. ${question3}</label>
							<div class="btn-group" data-toggle="buttons">
								<label class="btn btn-primary"> <input type="radio" name="os_document2" id="option1" value="1" required>Very dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="os_document2" id="option2" value="2" required>Somewhat dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="os_document2" id="option3" value="3" required>Neutral</label>
								<label class="btn btn-primary"> <input type="radio" name="os_document2" id="option3" value="4" required>Somewhat satisfied</label>
								<label class="btn btn-primary"> <input type="radio" name="os_document2" id="option3" value="5" required>Very satisfied</label>
							</div>
							
							<!-- Fourth question -->
							<br /><br />
							<label class="control-label">Q4. ${question4}</label>
							<div class="btn-group" data-toggle="buttons">
								<label class="btn btn-primary"> <input type="radio" name="os_document3" id="option1" value="1" required>Very dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="os_document3" id="option2" value="2" required>Somewhat dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="os_document3" id="option3" value="3" required>Neutral</label>
								<label class="btn btn-primary"> <input type="radio" name="os_document3" id="option3" value="4" required>Somewhat satisfied</label>
								<label class="btn btn-primary"> <input type="radio" name="os_document3" id="option3" value="5" required>Very satisfied</label>
							</div>
							
							<!-- Fifth question -->
							<br /><br />
							<label class="control-label">Q5. ${question5}</label>
							<div class="btn-group" data-toggle="buttons">
								<label class="btn btn-primary"> <input type="radio" name="os_document4" id="option1" value="1" required>Very dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="os_document4" id="option2" value="2" required>Somewhat dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="os_document4" id="option3" value="3" required>Neutral</label>
								<label class="btn btn-primary"> <input type="radio" name="os_document4" id="option3" value="4" required>Somewhat satisfied</label>
								<label class="btn btn-primary"> <input type="radio" name="os_document4" id="option3" value="5" required>Very satisfied</label>
							</div>
							
							
						
						</p>
						<!-- Submit button -->
							<footer>
								<div class="form-actions">
								<br />
									<input type="submit" value="Submit" style="background: #0FAAB2; float:middle;"/>
								</div>
							</footer>				
						</form>
				</article>
			</div>
			
			
			<!-- Write -->
				<div class="wrapper style2">
				<article id="write" class="container special">
					<header>
						<h2><strong>Progress</strong>.</h2>
					</header>
					<p id="contentView">
						<!-- 진행상황 그래프로 표시하기 -->
						<label for="myChart2"><h3>□ 부서 별 미실시 수</h3><br /></label>
			
						<div class="row">
							<!-- Bar Chart -->
							<canvas id="myChart2"></canvas>
							<script>
								var maxNum = "<c:out value="${maxNumBar}"/>";
								maxNum *= 1;
								var h_barOptions = {
									legend : {
										display : true,
										position : 'bottom'
									},
									scales : {
										xAxes : [ {
											ticks : {
												min : 0,
												max : maxNum,
												stepSize : 1
											}
										} ]
									}
								};
								var data_list = new Array();
								var name_list = new Array();
								<c:forEach items="${nameDept}" var="item">
								name_list.push("${item}");
								</c:forEach>
								<c:forEach items="${numNonImplement}" var="item">
								data_list.push("${item}");
								</c:forEach>
								var ctx = document.getElementById('myChart2').getContext(
										'2d');
								var myChart = new Chart(ctx, {
									type : 'horizontalBar',
									data : {
										labels : name_list,
										datasets : [ {
											label : '사무실 보안점검 미실시 수',
											data : data_list,
											backgroundColor : [ 'rgba(0, 99, 132, 0.6)',
													'rgba(30, 99, 132, 0.6)',
													'rgba(60, 99, 132, 0.6)',
													'rgba(90, 99, 132, 0.6)',
													'rgba(120, 99, 132, 0.6)',
													'rgba(150, 99, 132, 0.6)',
													'rgba(180, 99, 132, 0.6)',
													'rgba(210, 99, 132, 0.6)',
													'rgba(240, 99, 132, 0.6)',
													'rgba(270, 99, 132, 0.6)' ],
											borderColor : [ 'rgba(0, 99, 132, 1)',
													'rgba(30, 99, 132, 1)',
													'rgba(60, 99, 132, 1)',
													'rgba(90, 99, 132, 1)',
													'rgba(120, 99, 132, 1)',
													'rgba(150, 99, 132, 1)',
													'rgba(180, 99, 132, 1)',
													'rgba(210, 99, 132, 1)',
													'rgba(240, 99, 132, 1)',
													'rgba(270, 99, 132, 1)' ],
											borderWidth : 2,
											hoverBorderWidth : 0
										} ]
									},
									options : h_barOptions
								});
							</script>
						</div>
					</p>
				</article>
			</div>

			<!-- Features -->
				<div class="wrapper style1">

					<section id="features" class="container special">
						<header>
							<h2><a href="#" class="button" style="background: #0FAAB2" onclick="javascript:go2Post('<s:authentication property="name"/>');">See result </a></h2>
							<p>You can check your result.</p>
						</header>
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
		         hiddenField.setAttribute("value", value);
		         form.appendChild(hiddenField);
		         document.body.appendChild(form);
		         form.submit();
    			 }
    		</script>

	</body>

</html>