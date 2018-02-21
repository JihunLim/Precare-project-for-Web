
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet"
	href="<%=cp%>/resources/assets/css/loginstyle.css" />
</head>
<body>


	<div class="login">
		<a href="<%=cp%>/home"><h1>Precare</h1></a>
		<c:if test="${param.fail == null}">
			<h5 style="text-align: center; color: #1E90FF;">Sign in, please</h5>
		</c:if>
		<c:if test="${param.fail != null}">
			<h5 style="text-align: center; color: red;">We cannot find an account with that email address.</h5>
		</c:if>
		<c:url value="j_spring_security_check" var="loginUrl"/>
		<form action="${loginUrl}" method="post">
			<input type="text" name="j_username" id="j_username" placeholder="User email" required="required" /> 
			<input type="password" name="j_password" id="j_password" placeholder="Password" required="required" />
			<button type="submit" class="btn btn-primary btn-block btn-large">Login</button>
		</form>
	</div>


	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="<%=cp%>/bootstrap/js/bootstrap.js"></script>
</body>

</html>