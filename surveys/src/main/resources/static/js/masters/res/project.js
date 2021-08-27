$(document).ready(function(){
	
	$("#regionList").on("change", onChangeRegion);
/*	$("#getContacts").on("click", getContacts);*/
});

function onChangeRegion(){
	var region = $(this).find(':selected').val();
    getProjectList(region);
}

function getProjectList(region){
	var url = ctx+"/getProjectList?region="+region;
	submitToURL(url,"populateProjects");
}

function populateProjects(response){
	$('#projectList').empty();
	if(response===null){
		return;
	}
	
	$('#projectList').append('<option selected value="0">Choose Project</option>');

	var data =  response.data;
	if(data===null){
		return;
	}
	
	var projects= data.projects;
	if(projects === null){
		return;
	}
	
	$.each(projects, function(key, entry){
		$('#projectList').append($('<option></option>').attr('value', entry.sfid).text(entry.name));
	});
	return;
}	
	