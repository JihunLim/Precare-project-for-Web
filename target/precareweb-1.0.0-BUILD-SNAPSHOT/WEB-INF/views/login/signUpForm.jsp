<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%String cp= request.getContextPath(); %>
<!doctype html>
<html lang="kr">
	<head>
	<script src="<%=cp%>/resources/assets/js/jquery.min.js"></script>
	<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>

	
	
    
	<meta charset="UTF-8">
	<title>http://www.blueb.co.kr</title>

<style rel="stylesheet">
body {
   font: 13px/20px 'Helvetica Neue', Helvetica, Arial, sans-serif;
   color: #333333;
   background: #d0efd9;
}

.signUp {
   position: relative;
   margin: 50px auto;
   width: 450px;
   padding: 33px 25px 29px;
   background: #FFFFFF;
   border-bottom: 1px solid #C4C4C4;
   border-radius: 5px;
   -webkit-box-shadow: 0 1px 5px rgba(0, 0, 0, 0.25);
   box-shadow: 0 1px 5px rgba(0, 0, 0, 0.25);
}

.signUp:before,
.signUp:after {
   content: '';
   position: absolute;
   bottom: 1px;
   left: 0;
   right: 0;
   height: 10px;
   background: inherit;
   border-bottom: 1px solid #D2D2D2;
   border-radius: 4px;
}

.signUp:after {
   bottom: 3px;
   border-color: #DCDCDC;
}

.signUpTitle {
   margin: -25px -25px 25px;
   padding: 15px 25px;
   line-height: 35px;
   font-size: 26px;
   font-weight: 300;
   color: #777;
   text-align: center;
   text-shadow: 0 1px rgba(255, 255, 255, 0.75);
   background: #F7F7F7;
}

.signUpTitle:before {
   content: '';
   position: absolute;
   top: 0;
   left: 0;
   right: 0;
   height: 8px;
   background: #C4E17F;
   border-radius: 5px 5px 0 0;
   background-image: -webkit-linear-gradient(left, #C4E17F, #C4E17F 12.5%, #F7FDCA 12.5%, #F7FDCA 25%, #FECF71 25%, #FECF71 37.5%, #F0776C 37.5%, #F0776C 50%, #DB9DBE 50%, #db9CBE 62.5%, #C49CDE 62.5%, #C49CDE 75%, #669AE1 75%, #669AE1 87.5%, #62C2E4 87.5%, #62C2E4);
   background-image: -moz-linear-gradient(left, #c4e17f, #C4E17F 12.5%, #F7FDCA 12.5%, #F7FDCA 25%, #FECF71 25%, #FECF71 37.5%, #F0776C 37.5%, #F0776C 50%, #DB9DBE 50%, #DB9CBE 62.5%, #C49CDE 62.5%, #C49CDE 75%, #669AE1 75%, #669AE1 87.5%, #62C2E4 87.5%, #62C2E4);
   background-image: -o-linear-gradient(left, #C4E17F, #C4E17F 12.5%, #F7FDCC 12.5%, #F7FDCA 25%, #FECF71 25%, #FECF71 37.5%, #F0776C 37.5%, #F0776C 50%, #DB9DBE 50%, #DB9DBE 62.5%, #C49CDE 62.5%, #C49CDE 75%, #669AE1 75%, #669AE1 87.5%, #62C2E4 87.5%, #62C2E4);
   background-image: linear-gradient(to right, #C4E17F, #C4E17F 12.5%, #F7FDCA 12.5%, #F7FDCA 25%, #FECF71 25%, #FECF71 37.5%, #F0776C 37.5%, #F0776C 50%, #DB9DBE 50%, #DB9CBE 62.5%, #c49cde 62.5%, #C49CDE 75%, #669AE1 75%, #669AE1 87.5%, #62c2e4 87.5%, #62C2E4);
}

input {
   font-family: inherit;
   color: inherit;
   -webkit-box-sizing: border-box;
   -moz-box-sizing: border-box;
   box-sizing: border-box;
}

.signUpInput {
   width: 100%;
   height: 50px;
   margin-bottom: 25px;
   padding: 0 15px 2px;
   font-size: 17px;
   background: white;
   border: 2px solid #EBEBEB;
   border-radius: 4px;
   -webkit-box-shadow: inset 0 -2px #EBEBEB;
   box-shadow: inset 0 -2px #EBEBEB;
}

.signUpInput:focus {
   border-color: #62C2E4;
   outline: none;
   -webkit-box-shadow: inset 0 -2px #62C2E4;
   box-shadow: inset 0 -2px #62C2E4;
}

.lt-ie9 .signUpInput {
   line-height: 48px;
}

.signUpButton {
   position: relative;
   vertical-align: top;
   width: 100%;
   height: 54px;
   padding: 0;
   font-size: 22px;
   color: white;
   text-align: center;
   text-shadow: 0 1px 2px rgba(0, 0, 0, 0.25);
   background: #F0776C;
   border: 0;
   border-bottom: 2px solid #D76B60;
   border-radius: 5px;
   cursor: pointer;
   -webkit-box-shadow: inset 0 -2px #D76B60;
   box-shadow: inset 0 -2px #D76B60;
}

.signUpButton:active {
   top: 1px;
   outline: none;
   -webkit-box-shadow: none;
   box-shadow: none;
}

:-moz-placeholder {
   color: #AAAAAA;
   font-weight: 300;
}

::-moz-placeholder {
   color: #AAAAAA;
   opacity: 1;
   font-weight: 300;
}

::-webkit-input-placeholder {
   color: #AAAAAA;
   font-weight: 300;
}

:-ms-input-placeholder {
   color: #AAAAAA;
   font-weight: 300;
}

::-moz-focus-inner {
   border: 0;
   padding: 0;
}
</style>
</head>
<body>
<form class="signUp" id="signupForm" method="post" action="<%=cp%>/checkSignUp" onsubmit="return checkSignUpFormRule()">
   <h1 class="signUpTitle">Sign up for precare</h1>
   <input type="text" class="signUpInput" id="user_id" name="user_id" placeholder="Type your user Email" autofocus required>
   
<!-- 
   	<a href="#" class="button btn-default" onclick="javascript:checkId(document.getElementsByName('user_id')[0].value);">Check id duplication</a>
    <div id="checkMsg"></div>
-->
   
   <input type="password" class="signUpInput" name="user_pwd" placeholder="Choose a password" required>
   <input type="text" class="signUpInput" name="user_name" placeholder="Type your user name" required>
   <input type="number" class="signUpInput" name="user_age" placeholder="Type your user age" required>
   
   <label class="control-label" style="font-size:1.5em;">Birthday </label><br />
   <select name="birthday_year" required>
   		<option value="">year</option>
   		<%for(int i=1900; i<=2018; i++){ %>
   		<option value="<%=i%>"><%=i%></option>
   		<%}%>
   </select>
   
   <select name="birthday_month" required>
   		<option value="">month</option>
   		<%for(int i=1; i<=12; i++){ %>
   		<option value="<%=i%>"><%=i%></option>
   		<%}%>
   </select>
   
   <select name="birthday_day" required>
   		<option value="">day</option>
   		<%for(int i=1; i<=31; i++){ %>
   		<option value="<%=i%>"><%=i%></option>
   		<%}%>
   </select>
   <br /><br />
   
   <label class="control-label" style="font-size:1.5em;">Occupation </label><br />
		<select name="user_occupation" class="dropdown" required>
			<option value="no">Select job</option>
			<option value="no">No job</option>
			<option value="student">Student</option>
			<option value="teacher">Teacher</option>
			<option value="accountant">Accountant</option>
			<option value="office worker">Office worker</option>
			<option value="artist">Artist</option>
			<option value="developer">Developer</option>
			<option value="soldier">Soldier</option>
			<option value="scientist">Scientist</option>
			<option value="vicar">Vicar</option>
			<option value="manager">Manager</option>
			<option value="driver">Driver</option>
			<option value="etc">Etc.</option>
		</select>
	<br />
	<br />
   
    <div class="btn-group" data-toggle="buttons">
		<label class="btn btn-primary"> <input type="radio" name="user_sex" id="option1" value="male" required>Male</label> 
		<label class="btn btn-primary"> <input type="radio" name="user_sex" id="option2" value="female" required>Female</label> 
	</div>
	<br />
	<input type="text" class="signUpInput" name="user_manager" placeholder="Type your manager id (option)" >
    <span>＃You can select several managers: manager1, manager2, manager3</span><br /><br />
   
   
   <input type="submit" value="Sign me up!" class="signUpButton">
</form>

<script type="text/javascript">
	
      function checkId(userId){
            $.ajax({
                type: 'POST',
                url: '/precareweb/checkDuplicateId',
                data: {
                    "id" : userId
                },
                error : function(){
                	alert("통신실패");
                },
                success: function(data){
                	alert("성공");
                    if($.trim(data) == 0){
                    	alert("dd");
                        $('#checkMsg').html('<p style="color:blue">You can use this id.</p>');
                    }
                    else{
                    	alert("dd2")
                        $('#checkMsg').html('<p style="color:red">This id is already use. Please try another id again.</p>');
                    }
                }
            });    //end ajax    
       };
    </script>


</body>
</html>
