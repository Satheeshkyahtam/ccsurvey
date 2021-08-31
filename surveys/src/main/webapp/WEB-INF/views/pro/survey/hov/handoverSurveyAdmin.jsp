<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Survey</title>
<link rel="stylesheet" href="<c:url value='/css/common/bootstrap.min.css' />">
<link rel="stylesheet" href="<c:url value='/css/common/datatable.css' />">
<link rel="stylesheet" href="<c:url value='/css/common/button.datatable.css' />">

<link rel="stylesheet" href="<c:url value='/vendor/multiselectDD/prettify.min.css'/>">
<link rel="stylesheet" href="<c:url value='/vendor/multiselectDD/bootstrap-multiselect.css'/>">

<link rel="stylesheet" href="<c:url value='/vendor/font-awesome/font-awesome.css'/>">

<script src="<c:url value='/js/common/jquery.min.js' />"></script>
<script src="<c:url value='/js/common/bootstrap.min.js' />"></script>
<script src="<c:url value='/js/masters/ho/project.js' />"></script>
<script src="<c:url value='/js/common/springform.js' />"></script>
<script src="<c:url value='/js/transactions/hov/survey.js' />"></script>
<script src="<c:url value='/js/transactions/hov/handoverContacts.js' />"></script>
<script src="<c:url value='/js/common/jquery-datatable.js' />"></script>
<script src="<c:url value='/js/common/jquery-datatable-button.js' />"></script>
<script src="<c:url value='/js/common/jszip.min.js' />"></script>
<script src="<c:url value='/js/common/button.html5.js' />"></script>
<script src="<c:url value='/js/common/constants.js' />"></script>

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

	<div class="container mrgT20">
	 <!-- Nav tabs -->
	  <ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Contact Info</a></li>
		<li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Table 2</a></li>
	  </ul>

	  <!-- Tab panes -->
	  <div class="tab-content">
		
		
	
		<div role="tabpanel" class="tab-pane active" id="home">
			<div class="filterColBg">
				<div class="filterCol">
				  <%-- <div class="form-group col-md-2">
					<label >Region</label>
					<select class="form-control input-sm" name="region" id="regionList">
					<option selected value="0">Choose Region</option>
					<c:forEach var="region" items="${regions}">
						<option value="${region}">${region}</option>
					</c:forEach>
					</select>
				  </div> --%>
				  
				<%-- <div class="form-group col-md-2">
					<label >Region</label>
					<select class="form-control input-sm multiselectDD" name="region" id="regionListHO" multiple="multiple" style="height:34px;">
						<option selected value="0">Choose Region</option>
						<c:forEach var="region" items="${regions}">
							<option value="${region}">${region}</option>
						</c:forEach>
					</select>
				</div> --%> 
				  
				<!-- <div class="col-md-3 form-group">
					<label class="controlLabel required">Tower <strong style="color: #f00;">*</strong></label>
					<select class="form-control multiselectDD" multiple="multiple" id="towerMst" style="height:34px;">
						<option>Select</option>
					</select>	
				</div> -->
				  
				  <div class="form-group col-md-2">
					<label >Projects</label>
					<select class="form-control input-sm multiselectDD" name="projectSfid" id="projectList" multiple="multiple" style="height:34px;   ">				
					</select>
				  </div>
				  <div class="form-group col-md-2">
					<label style="display: block;"> &nbsp; </label>
					<button class="btn btn-primary btn-sm" id="getContacts">Get Contacts</button>
				  </div> 		  
				  
				   <div class="form-group col-md-2">
					<label style="display: block;"> &nbsp; </label>
					<button class="btn btn-primary btn-sm" id="exportRecords">Count-0</button>
				  </div> 
				  <div class="form-group col-md-2 pull-right">
					<label style="display: block;"> &nbsp; </label>
					<button class="btn btn-primary btn-sm" id="sendSurvey">Send Survey</button>
				  </div> 
				  <div class="clearfix"></div> 
				  <div id="hoNoteMsg" style="display:none; padding: 0 15px 5px 15px; color: #b10101; ">To process the all project records, it might take a few minutes</div>
				  <div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
			</div>
	<div class="container-fluid" style="overflow: auto;">
			<table class="display nowrap table table-bordred" style="width:100%" id="surveyContactTable">
				<thead>
				  <!-- <tr>
					<th>Firstname</th>
					<th>Email</th>
					<th>Mobile</th>
					<th>Booking No</th>
					<th>Booking Date </th>
					<th>Type</th>
					<th>Project</th>
					<th>Unit</th>
				  </tr> -->
				  
				  <!-- Added by A -->
				  <tr>
					<th>Touchpoint</th>
					<th>app_id</th>
					<th>user_phone</th>
					<th>transactionDate</th>
					<th>user_email</th>
					<th>cust_name</th>
					<th>name</th>
					<th>bookingDate</th>
					<th>sentDate</th>
					
					<th>sentStatus</th>
					<th>field1</th>
					<th>field2</th>
					<th>field3</th>
					<th>field4</th>
					<th>field6</th>
					<th>field8</th>
					<th>field9</th>
					<th>field10</th>
					<th>field11</th>
					<th>field13</th>
					<th>field14</th>
					<th>field15</th>
					<th>field16</th>
					<th>field18</th>
					<th>field20</th>
					<th>surveyType</th>
					<th>propertyName</th>
					<th>projectName</th>
				  </tr>
				  <!-- END Added by A -->
				  
				</thead>
				<tbody id="suveyContacts">
				</tbody>
			</table>
		<div class="clearfix"></div>
		</div>
					
		</div>
		<div role="tabpanel" class="tab-pane" id="profile">... 2</div>
	  </div>
		<div class="clearfix"></div>
	</div>
	
	<script>
/* 	$(document).ready(function() {
	    $('#surveyContactTable').DataTable( {
	        dom: 'Bfrtip',
	        "buttons": [
	       { "extend": 'excel', "text":'Export To Excel',"className": 'btn btn-default btn-xs' }
	   ],
	    } );
	} ); */
	</script>
	
<script src="<c:url value='/vendor/multiselectDD/prettify.min.js'/>"></script>
<script src="<c:url value='/vendor/multiselectDD/bootstrap-multiselect.js'/>"></script>	

<script>
/* $(document).ready(function(){
	$('#regionListHO').multiselect({
		maxHeight: '200',
		allSelectedText: 'All',
		enableClickableOptGroups: true,
		includeSelectAllOption: true,
		enableFiltering: true,
		enableCaseInsensitiveFiltering: true,
		buttonWidth: '100%'
	});
}); */

</script>

</body>
</html>                                		                            