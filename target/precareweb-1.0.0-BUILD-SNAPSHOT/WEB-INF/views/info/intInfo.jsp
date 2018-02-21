<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String cp= request.getContextPath(); %>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
	<title>http://www.blueb.co.kr</title>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link rel="stylesheet" href="<%=cp%>/resources/assets/css/infostyle.css" />
</head>
<body>



<div class="wrap">
       <h1>Internet addiction</h1>   
        <div class="youtubeWrap">
        <iframe src="https://www.youtube.com/embed/iOUsiXgtHGQ" width="500" height="315" frameborder="0"></iframe>
        </div> 
        <p>
         Do you play video games on the Internet in excess? Are you compulsively shopping online? Can’t physically stop checking Facebook? Is your excessive computer use interfering with your daily life – relationships, work, school? If you answered yes to any of these questions, you may be suffering from Internet Addition Disorder, also commonly referred to as Compulsive Internet Use (CIU), Problematic Internet Use (PIU), or iDisorder.<br /><br />
         <span class="icon fa-paper-plane"></span>Helpful URL to read : <a href="https://www.psycom.net/iadcriteria.html" target="_blank">PSYCOM</a>
         
       </p> 
       <br />
</div>
<script src="<%=cp%>/resources/assets/js/youtubeWrapper.js"></script>
<script>
         document.body.scrollIntoView(true);
         parent.document.all.aaa.height = document.body.scrollHeight; 
</script> 

</body>
</html>