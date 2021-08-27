
function processResponse(response){
	if(response===null){
		return;
	}
	if(response.hasError){
		alert(response.message);
	}
	else
		window.location.href="pr/dashboard";
/*	window.location.href="residentsurveyadmin";*/
	
}