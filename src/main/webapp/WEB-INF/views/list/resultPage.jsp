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
								<a href="#banner" class="button circled scrolly" onclick="javascript:go2Post(document.getElementsByName('userId')[0].value);">Search!</a>
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
				<div class="wrapper style2">

					<article id="main" class="container special">
						<header>
							<h2>Past Prediction Results</h2>
						</header>
						<table width="800" cellpadding="0" cellspacing="0" border="1">
							<thead>
								<tr style="background: #eee; border-bottom: 3px inset #ccc;">
									<th width="110">date</th>
									<th>survey title</th>
									<th>result</th>
									<th>probability</th>
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
									<td>${dto.pre_probability}</td>
									<td><a href="#" class="skel-layers-ignoreHref"><span class="icon fa-file-text"></span></a></td>
								</tr>
							</tbody>
							</c:forEach>
						</table>
					</article>

				</div>

			<!-- Features -->
				<div class="wrapper style1">

					<section id="features" class="container special">
						<header>
							<h2>Here is a helpful remedy for you.</h2>
						</header>
						<div class="row">
						
						
							<article class="12u 12u(mobile) special">
								<p class="image featured"><img src="resources/images/pic07.jpg" alt="" /></p>
								<header>
									<h3><a href="#">Gravida aliquam penatibus</a></h3>
								</header>
								<p>
									<!-- 예측된 결과가 없을 경우 -->
									<c:if test="${prediction_count <= 0}"> 
										<h2>"Oops! You haven't finished the survey yet."</h2>
									</c:if>
									<!-- 예측된 결과가 있을 경우 -->
									<c:if test="${prediction_count > 0}"> 
										<c:if test="${prediction_list[0].prediction_sort eq 'depression'}">
											<c:if test="${prediction_list[0].pre_result eq 'yes'}"> 
												<h2>Recently, you have been suffering from <span style="color: blue;"><strong>${prediction_list[0].pre_sort}</strong></span>.</h2>
											</c:if>
										</c:if>
										
										
									</c:if>
								</p>
								
								<p>
									<h3>Manager's Comment : </h3>
									<span>'${prediction_list[0].pre_result}'</span>
							
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

	</body>

</html>