$(document).ready(function (){
	
	$("#sendSurvey").on("click",onClickSendSurvey);
	
	
});

function onClickSendSurvey(){
	$("#sendSurvey").attr('disabled',true);
	var projectSfid = $("#projectList").find(':selected').val();
	//sendSurvey(projectSfid);
	
	sendMultiProjectSurveyBL ();
}

function sendMultiProjectSurveyBL () {
	var project = $('#projectList').find('option:selected');
	var selectedProject = [];
	$(project).each(function(index, brand){
		selectedProject.push($(this).val());
	});
	
	if (selectedProject != "") {
		selectedProject = selectedProject.join(",");
	} else {
		alert("Please select the filter parameters!")
		selectedProject = "";
		return;
	}
	
	var fromDate = $('#fromDate').val();
	var toDate =  $('#toDate').val();
	if(fromDate ==null || toDate == null){
		alert("Please select the filter parameters!")
		return "";
	}
	
	var url = ctx+"/pr/sendMultiProjectSurveyBL?projectSfid="+selectedProject+"&fromDate="+fromDate+"&toDate="+toDate;
	submitToURL(url,"processSurveyResponse");
}

function sendSurvey(projectSfid){
	var fromDate = $('#fromDate').val();
	var toDate =  $('#toDate').val();
	if(projectSfid == null || fromDate ==null || toDate == null){
		alert("Please select the filter parameters!")
		$("#sendSurvey").attr('disabled',false);
		return "";
	}

	var url = ctx+"/pr/sendSurveyBL?projectSfid="+projectSfid+"&fromDate="+fromDate+"&toDate="+toDate;
	submitToURL(url,"processSurveyResponse");
}

function processSurveyResponse(response){
	if(response ==null){
		return;
	}
	if(response.hasError){
		alert(response.message);
	}else if(response.data==null){
		alert("No Import information");
	}
	else{
		alert("Import Count = " +response.data.importCount);
	}
	$("#sendSurvey").attr('disabled',false);
}