$(document).ready(function (){
	$("#preference").on("click",onClickPreference);
	/*$(".formSubmit").on("click",function(event){
		alert("Hi");
	});*/
	
});

function onClickPreference(){
	return false;
}

function processResponse(res){
	console.log(res);
	alert(res);
}

function onSubmitPreference(event){
	   alert('Hi');
	   event.stopImmediatePropagation();
	   event.preventDefault();
	   var formObj = $("#rmPreference");
	   $(this).prop("disabled", true);
	   submitFormJson(formObj);
	   $(this).prop("disabled", false);

}

function processResponse(res){
	if (res == null){
		return;
	}
	console.log(res);
	var data  = res.data;
	var message = "";
	if(data == null){
		alert("No Response from server");
		return;
	}
	if(data.preferenceId !=null){
		$("#surveyPreferenceId").val(data.preferenceId);
	}
	alert(res.message);
}