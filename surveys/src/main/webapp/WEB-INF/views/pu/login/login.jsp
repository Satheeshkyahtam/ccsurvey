<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login Form</title>
<link rel="stylesheet" href="<c:url value='css/common/bootstrap.min.css' />">
<script src="<c:url value='js/common/jquery.min.js' />"></script>
<script src="<c:url value='js/common/bootstrap.min.js' />"></script>
<script src="<c:url value='js/common/springform.js' />"></script>
<script src="<c:url value='js/login/login.js' />"></script>
<script src="<c:url value='/js/common/jquery-serializeJson.js' />"></script>
<style>
.mrgT20 {margin-top:20px !important;}
.mrgB20 {margin-bottom:20px !important;}

.login-form {
	width: 340px;
	margin: 50px auto;
}
.login-form form {
	margin-bottom: 15px;
	background: #f7f7f7;
	box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
	padding: 30px;
}
.login-form h2 {
	margin: 0 0 15px;
}
.form-control, .btn {
	min-height: 38px;
	border-radius: 2px;
}
.btn {        
	font-size: 15px;
	font-weight: bold;
}

.filterCol {background-color: #f0f0f0;  padding-top: 10px;}
.filterColBg {padding: 15px;}
.tab-content {border: 1px solid #ccc; border-top: 0;}
</style>
</head>
<body>
<div class="login-form">
    <form action="authenticate" method="post">
        <h2 class="text-center">Log in</h2>       
        <div class="form-group">
            <input type="text" class="form-control" placeholder="User name" required="required" name="email">
        </div>
        <div class="form-group">
            <input type="password" class="form-control" placeholder="Password" required="required" name="password">
        </div>
        <div class="form-group">
            <button class="formSubmit" type="submit" class="btn btn-primary btn-block">Log in</button>
        </div>
        <div class="clearfix">
<!--             <label class="pull-left checkbox-inline"><input type="checkbox"> Remember me</label> -->
<!--             <a href="#" class="pull-right">Forgot Password?</a> -->
        </div>        
    </form>
   
</div>
</body>
</html>                                		                            