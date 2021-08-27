$(document).ready(function (){
	
	$("#sendSurvey").on("click",onClickSendSurvey);
	
	
});

function onClickSendSurvey(){
	$("#sendSurvey").attr('disabled',true);
	var projectSfid = $("#projectList").find(':selected').val();
	sendSurvey(projectSfid);
}
function sendSurvey(projectSfid){
	var url = ctx+"/pr/sendSurveyREG?projectSfid="+projectSfid;
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