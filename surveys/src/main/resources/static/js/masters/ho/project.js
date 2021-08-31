$(document).ready(function(){
	
	$("#regionList").on("change", onChangeRegion);
	$("#getContacts").on("click", getContacts);
	
	/*Added by A*/
	//$("#regionListHO").on("change", HOonChangeRegion);
	/* END Added by A*/
	
	HOonChangeRegion ();
});

/*Added by A*/
function HOonChangeRegion(){
	
	/*var region = $(this).find('option:selected');
	var selectedRegion = [];
	$(region).each(function(index, brand){
		selectedRegion.push($(this).val());
	});
	
	
	if (selectedRegion != "") {
		selectedRegion = selectedRegion.join(",");
	} else {
		selectedRegion = "";
		return;
	}*/
	
	//var region = $(this).find(':selected').val();
	HOgetProjectList("");
}
/* END Added by A*/

function onChangeRegion(){
	var region = $(this).find(':selected').val();
    getProjectList(region);
}

/*Added by A*/
function HOgetProjectList(region){
	var url = ctx+"/getHOProjectList?region="+region;
	submitToURL(url,"HOpopulateProjects");
}
/* END Added by A*/

function getProjectList(region){
	var url = ctx+"/getProjectList?region="+region;
	submitToURL(url,"populateProjects");
}


/*Added by A*/
function HOpopulateProjects(response){
	
	var optionProject = ''; 
	
	$('#projectList').empty();
	if(response===null){
		return;
	}
	
	
	//optionProject = optionProject+"<option selected value='0'>Choose Project</option>"
	//$('#projectList').append('<option selected value="0">Choose Project</option>');

	var data =  response.data;
	if(data===null){
		return;
	}
	
	var projects= data.projects;
	if(projects === null){
		return;
	}
	
	$.each(projects, function(key, entry){
		//$('#projectList').append($('<option></option>').attr('value', entry.sfid).text(entry.name));
		
		 optionProject = optionProject+"<option value="+entry.sfid+"  >"+entry.name+"</option>";
	});
	
	optionProject = optionProject+"</optgroup>"
	
	$("#projectList").html("");
	$('#projectList').multiselect('destroy');
	$("#projectList").append(optionProject);
	$('#projectList').multiselect({
		maxHeight: '200',
		allSelectedText: 'All',
		enableClickableOptGroups: true,
		includeSelectAllOption: true,
		enableFiltering: true,
		enableCaseInsensitiveFiltering: true,
		buttonWidth: '100%'
	});
	
	$('#projectList').multiselect('refresh');
	
	
	return;
}
/* END Added by A*/


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
	function onChangeProject(){
		
		var project = $(this).find(':selected').val();
	    getContactList(project);
		
	}	
	
	function getContactList(project){
		var url = ctx+"getContactsBL?projectSfid="+project;
		submitToURL(url,"populateContacts");
	}
	
	function getContacts(){
		var project = $('#projectList').find(':selected').val();
		var fromDate = $('#fromDate').val();
		var toDate =  $('#toDate').val();
		if(project == null || fromDate ==null || toDate == null){
			alert("Please select the filter parameters!")
			return "";
		}
		var url = ctx+"getContactsBL?projectSfid="+project+"&fromDate="+fromDate+"&toDate="+toDate;
		submitToURL(url,"populateContacts");
	}
	
	function populateContacts(response){
		$("#surveyContactTable").DataTable().destroy();
		$('#surveyContactTable tbody').empty();
		if(response===null){
			return ;
		}
		
		var data =  response.data;
		if(data===null){
			return;
		}
		
		var contacts= data.contacts;
		if(contacts === null){
			return;
		}
		var records =  data.count;
		$("#exportRecords").html("Count-"+records);


		$.each(contacts, function(key, entry){
			$('#surveyContactTable tbody').append('<tr><td>'+entry.firstName+'</td><td>'
					+entry.email+'</td><td>'+entry.mobile
					+'</td><td>'+entry.name+'</td><td>'+entry.bookingDate+'</td>'
					+'</td><td>'+entry.surveyType+'</td>'
					+'<td>'+entry.field15+'</td>'
					+'<td>'+entry.propertyName+'</td>'
					+'</tr>');
		});		
			$('#surveyContactTable').DataTable( {
		        dom: 'Bfrtip',
		        "buttons": [
		       { "extend": 'excel', "text":'Export To Excel',"className": 'btn btn-default btn-xs' }
		   ],
		    } );
/*		$('#surveyContactTable').DataTable( ).fnClearTable();
		$('#surveyContactTable').DataTable( ).fnDestroy();
*/		return;
	}