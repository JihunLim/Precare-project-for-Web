
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
		
	</head>
	<body class="homepage">
		<div id="page-wrapper">

			<!-- Header -->
				<div id="header">

					<!-- Inner -->
						<div class="inner">
							<header>
								<h1><span id="logo">Precare</span></h1>
								<hr />
								<p style="font-weight: 800;">Hello, ${user_name}.<br /></p> 
								<p style="font-weight: 700;">We will prevent you from getting mental sick before you get hurt.</p>
							</header>
							<footer>
								<a href="#banner" class="button circled scrolly">Start</a>
							</footer>
						</div>

					<!-- Nav -->
						<nav id="nav">
							<ul>
								<li><a href="home"><span class="icon fa-home"></span>Home</a></li>
								<li><a href="showResult"><span class="icon fa-universal-access"></span>Management</a></li>
								
								<s:authorize access="isAuthenticated()">
                           			<li><a href="${pageContext.request.contextPath}/j_spring_security_logout"> <span class="icon fa-sign-out"></span>[Log Out]</a></li>
                           			<li><a href="#settingLayer" class="btn-example"><span class="icon fa-cog"></span>Setting</a>
                       			</s:authorize>
                       			
                       			<s:authorize access="isAnonymous()">
                           			<li><a href="loginForm"><span class="icon fa-sign-in"></span>Login</a></li>
                           			<li><a href="#layer2" class="btn-example"><span class="icon fa-user-plus"></span>Sign Up</a>
                       			</s:authorize>
							</ul>
						</nav>
				</div>


			<!-- Banner -->
				<section id="banner">
					<header>
						<h2>MS Imagine Cup 2018 : Wherever Team <br /><strong>“Precare”</strong></h2>
						<p>
							Let's treat <strong>mental illness</strong> through daily surveys using <strong>Big data & Machine learning</strong> Technology before you get sick.
						</p>
					</header>
				</section>

			<!-- Carousel -->
	
				<section class="carousel">
					<div class="reel">
						<article>
							<a href="#depLayer" class="image featured scrolly btn-example"><img src="resources/images/img_dep.jpg" alt="Depression" width="120px" height="200"/></a>
							<header>
								<h3><a href="#depLayer" class="btn-example">Depression</a></h3>
							</header>
							<p>What is depression and what can I do about it?</p>
						</article>

						<article>
							<a href="#panLayer" class="image featured scrolly btn-example"><img src="resources/images/img_pan.jpg" alt="Panic disorder"  width="120px" height="200"/></a>
							<header>
								<h3><a href="#panLayer" class="btn-example">Panic disorder</a></h3>
							</header>
							<p>What is panic disorder and what can I do about it?</p>
						</article>

						<article>
							<a href="#perLayer" class="image featured scrolly btn-example"><img src="resources/images/img_per.jpg" alt="Personality disorder" width="120px" height="200"/></a>
							<header>
								<h3><a href="#perLayer" class="btn-example">Personality disorder</a></h3>
							</header>
							<p>What is personality disorder and what can I do about it?</p>
						</article>

						<article>
							<a href="#insLayer" class="image featured scrolly btn-example"><img src="resources/images/img_ins.jpg" alt="Insomnia" width="120px" height="200"/></a>
							<header>
								<h3><a href="#insLayer" class="btn-example">Insomnia</a></h3>
							</header>
							<p>What is insomnia and what can I do about it?</p>
						</article>
						
						<article>
							<a href="#intLayer" class="image featured scrolly btn-example"><img src="resources/images/img_int.jpg" alt="Internet addiction" width="120px" height="200"/></a>
							<header>
								<h3><a href="#intLayer" class="btn-example">Internet addiction</a></h3>
							</header>
							<p>What is internet addiction and what can I do about it?</p>
						</article>

					</div>
				</section>
			
			<!-- Main (설문조사) -->
			<div class="wrapper style2" style="background-image: url('resources/images/img_paper.jpg'); background-repeat:no-repeat; background-size: cover; background-attachment: fixed;">
				<article id="main" class="container special" style="background-color:white; padding:10px 0px 10px 20px;">
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
										<select name="sort" class="dropdown" onchange="changeValue(this.value)" style="float:left;" >
											<option value="0">Auto</option>
											<option value="1">Depression</option>
											<option value="2">Panic disorder</option>
											<option value="3">Personality disorder</option>
											<option value="4">Insomnia</option>
											<option value="5">Internet addiction</option>
										</select>
									</div>
								</div>
							</div>
							
							<!-- first question -->
							<br /><br />
							<input type="hidden" name="id_question1" value="${id_question1}"/>
							<label class="control-label">Q1. ${question1}</label>
							<div class="btn-group" data-toggle="buttons">
								<label class="btn btn-primary"> <input type="radio" name="question1" id="option1" value="1" required>Very dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="question1" id="option2" value="2" required>Somewhat dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="question1" id="option3" value="3" required>Neutral</label>
								<label class="btn btn-primary"> <input type="radio" name="question1" id="option3" value="4" required>Somewhat satisfied</label>
								<label class="btn btn-primary"> <input type="radio" name="question1" id="option3" value="5" required>Very satisfied</label>
							</div>
								
							<!-- Second question -->
							<br /><br />
							<input type="hidden" name="id_question2" value="${id_question2}"/>
							<label class="control-label">Q2. ${question2}</label>
							<div class="btn-group" data-toggle="buttons">
								<label class="btn btn-primary"> <input type="radio" name="question2" id="option1" value="1" required>Very dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="question2" id="option2" value="2" required>Somewhat dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="question2" id="option3" value="3" required>Neutral</label>
								<label class="btn btn-primary"> <input type="radio" name="question2" id="option3" value="4" required>Somewhat satisfied</label>
								<label class="btn btn-primary"> <input type="radio" name="question2" id="option3" value="5" required>Very satisfied</label>
							</div>
							
							<!-- Third question -->
							<br /><br />
							<input type="hidden" name="id_question3" value="${id_question3}"/>
							<label class="control-label">Q3. ${question3}</label>
							<div class="btn-group" data-toggle="buttons">
								<label class="btn btn-primary"> <input type="radio" name="question3" id="option1" value="1" required>Very dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="question3" id="option2" value="2" required>Somewhat dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="question3" id="option3" value="3" required>Neutral</label>
								<label class="btn btn-primary"> <input type="radio" name="question3" id="option3" value="4" required>Somewhat satisfied</label>
								<label class="btn btn-primary"> <input type="radio" name="question3" id="option3" value="5" required>Very satisfied</label>
							</div>
							
							<!-- Fourth question -->
							<br /><br />
							<input type="hidden" name="id_question4" value="${id_question4}"/>
							<label class="control-label">Q4. ${question4}</label>
							<div class="btn-group" data-toggle="buttons">
								<label class="btn btn-primary"> <input type="radio" name="question4" id="option1" value="1" required>Very dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="question4" id="option2" value="2" required>Somewhat dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="question4" id="option3" value="3" required>Neutral</label>
								<label class="btn btn-primary"> <input type="radio" name="question4" id="option3" value="4" required>Somewhat satisfied</label>
								<label class="btn btn-primary"> <input type="radio" name="question4" id="option3" value="5" required>Very satisfied</label>
							</div>
							
							<!-- Fifth question -->
							<br /><br />
							<input type="hidden" name="id_question5" value="${id_question5}"/>
							<label class="control-label">Q5. ${question5}</label>
							<div class="btn-group" data-toggle="buttons">
								<label class="btn btn-primary"> <input type="radio" name="question5" id="option1" value="1" required>Very dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="question5" id="option2" value="2" required>Somewhat dissatisfied</label> 
								<label class="btn btn-primary"> <input type="radio" name="question5" id="option3" value="3" required>Neutral</label>
								<label class="btn btn-primary"> <input type="radio" name="question5" id="option3" value="4" required>Somewhat satisfied</label>
								<label class="btn btn-primary"> <input type="radio" name="question5" id="option3" value="5" required>Very satisfied</label>
							</div>
							
							
						</p>
						<!-- Submit button -->
							<footer>
								<div class="form-actions">
								<br />
									<label style="text-align:center;"><input type="submit" value="Submit" style="background: #0FAAB2; float:middle;"/></label>
									
								</div>
							</footer>				
						</form>
				</article>
			</div>
			
			
			<!-- Write -->
				<div class="wrapper style2">
				<article id="write" class="container special">
					<header>
						<h2><strong>Progress</strong></h2>
					</header>
					<p id="contentView">
						<!-- 진행상황 그래프로 표시하기 -->
						<label for="myChart2"><h3>□ Progress status</h3><br /></label>
			
						<div class="row">
							<!-- Bar Chart -->
							<canvas id="myChart2"></canvas>
							<script>
								var maxNum = 15;
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
								//input label
								name_list.push("Depression\n(15)");
								name_list.push("Panic disorder");
								name_list.push("Personality disorder");
								name_list.push("Insomnia");
								name_list.push("Internet addiction");
								//input data
								data_list.push("${status_dep}");
								data_list.push("${status_pan}");
								data_list.push("${status_per}");
								data_list.push("${status_ins}");
								data_list.push("${status_int}");
								var ctx = document.getElementById('myChart2').getContext('2d');
								var myChart = new Chart(ctx, {
									type : 'horizontalBar',
									data : {
										labels : name_list,
										datasets : [ {
											label : 'status',
											data : data_list,
											backgroundColor : [ 
												    'rgba(0, 99, 132, 0.6)',
													'rgba(30, 99, 132, 0.6)',
													'rgba(60, 99, 132, 0.6)',
													'rgba(90, 99, 132, 0.6)',
													'rgba(120, 99, 132, 0.6)'
													],
											borderColor : [
												    'rgba(0, 99, 132, 1)',
													'rgba(30, 99, 132, 1)',
													'rgba(60, 99, 132, 1)',
													'rgba(90, 99, 132, 1)',
													'rgba(120, 99, 132, 1)'
													],
											borderWidth : 2,
											hoverBorderWidth : 0
										} ]
									},
									options : {
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
									}
								});
								
								</script>
						</div>
					</p>
				</article>
			</div>

			<!-- Features -->
				<div class="wrapper style1" style="background-image: url('resources/images/background1.jpg'); background-repeat:no-repeat;">

					<section id="features" class="container special" >
						<header>
							<h2><a href="#" class="button" style="background: #0FAAB2" onclick="javascript:go2Post('<s:authentication property="name"/>');">See result </a></h2>
							<p><strong>Check your result!</strong></p>
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
        
        <!-- 레이어 팝업 (설정 창) -->
		    <div id="settingLayer" class="pop-layer">
		    <a href="#" class="btn-layerClose" style="float:right;"><span class="icon fa-times"></span></a>
		        <div class="pop-container">
		            <div class="pop-conts">
		                <!--content //-->
		                <iframe src="settingForm" style="width:100%; height:700px;" scrolling="auto"></iframe> 
		                <!--// content-->
		            </div>
		        </div>
		    </div>
		
		<!-- 레이어 팝업 (회원가입 창) -->
		    <div id="layer2" class="pop-layer">
		    <a href="#" class="btn-layerClose" style="float:right;"><span class="icon fa-times"></span></a>
		        <div class="pop-container">
		            <div class="pop-conts">
		                <!--content //-->
		                <iframe src="signUpForm" style="width:100%; height:700px;" scrolling="auto"></iframe> 
		                <!--// content-->
		            </div>
		        </div>
		    </div>
		    
	    <!-- 레이어 팝업 (로그인 창) -->
		    <div id="loginLayer" class="pop-layer">
		    <a href="#" class="btn-layerClose" style="float:right;"><span class="icon fa-times"></span></a>
		        <div class="pop-container">
		            <div class="pop-conts">
		                <!--content //-->
		                <iframe src="loginForm" style="width:100%; height:400px;" scrolling="auto"></iframe> 
		                <!--// content-->
		            </div>
		        </div>
		    </div>
		    
		<!-- 레이어 팝업 (우울증 창) -->
	    <div id="depLayer" class="pop-layer">
	    <a href="#" class="btn-layerClose" style="float:right;"><span class="icon fa-times"></span></a>
	        <div class="pop-container">
	            <div class="pop-conts">
	                <!--content //-->
	                <iframe src="depInfo" style="width:100%; height:590px;" scrolling="auto"></iframe> 
	                <!--// content-->
	            </div>
	        </div>
	    </div>
	    
	    <!-- 레이어 팝업 (공황장애 창) -->
	    <div id="panLayer" class="pop-layer">
	    <a href="#" class="btn-layerClose" style="float:right;"><span class="icon fa-times"></span></a>
	        <div class="pop-container">
	            <div class="pop-conts">
	                <!--content //-->
	                <iframe src="panInfo" style="width:100%; height:590px;" scrolling="auto"></iframe> 
	                <!--// content-->
	            </div>
	        </div>
	    </div>    
	    
	    <!-- 레이어 팝업 (성격장애 창) -->
	    <div id="perLayer" class="pop-layer">
	    <a href="#" class="btn-layerClose" style="float:right;"><span class="icon fa-times"></span></a>
	        <div class="pop-container">
	            <div class="pop-conts">
	                <!--content //-->
	                <iframe src="perInfo" style="width:100%; height:590px;" scrolling="auto"></iframe> 
	                <!--// content-->
	            </div>
	        </div>
	    </div>    
	    
	    <!-- 레이어 팝업 (수면장애 창) -->
	    <div id="insLayer" class="pop-layer">
	    <a href="#" class="btn-layerClose" style="float:right;"><span class="icon fa-times"></span></a>
	        <div class="pop-container">
	            <div class="pop-conts">
	                <!--content //-->
	                <iframe src="insInfo" style="width:100%; height:590px;" scrolling="auto"></iframe> 
	                <!--// content-->
	            </div>
	        </div>
	    </div>    
	    
	    <!-- 레이어 팝업 (인터넷중독 창) -->
	    <div id="intLayer" class="pop-layer">
	    <a href="#" class="btn-layerClose" style="float:right;"><span class="icon fa-times"></span></a>
	        <div class="pop-container">
	            <div class="pop-conts">
	                <!--content //-->
	                <iframe src="intInfo" style="width:100%; height:590px;" scrolling="auto"></iframe> 
	                <!--// content-->
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
			<script src="<%=cp%>/resources/assets/js/layerPopUp.js"></script>
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