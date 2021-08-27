<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<title>CC Survey</title>
<body>

<h1>Surveys</h1>

<p><a href="<c:url value='/pr/surveyAdmin' />">RM Survey</a></p>
<p><a href="<c:url value='/pr/surveyAdminBL' />">Baseline Survey</a></p>
<p><a href="<c:url value='/pr/surveyAdminRES' />">Resident Survey</a></p>
<p><a href="<c:url value='/pr/surveyAdminONB' />">Onboarding Survey</a></p>
<p><a href="<c:url value='/pr/surveyAdminREG' />">Registration Survey</a></p>
<p><a href="<c:url value='/pr/surveyAdminCV' />">Customer Visit Survey</a></p>
<p><a href="<c:url value='/pr/surveyAdminHOV' />">Handover Survey</a></p>
<p><a href="<c:url value='/pr/surveyAdminSV' />">Site Visit Survey</a></p>
</body>
</html>
