$(document).ready(function(){
	
	$("#regionList").on("change", onChangeRegion);
	$("#getContacts").on("click", getContacts);
	
	BLonChangeRegion ();
});


/*Added by A*/
function BLonChangeRegion(){
	
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
	BLgetProjectList("");
}
/* END Added by A*/

function onChangeRegion(){
	var region = $(this).find(':selected').val();
    getProjectList(region);
}

/*Added by A*/
function BLgetProjectList(region){
	var url = ctx+"/getBLProjectList?region="+region;
	submitToURL(url,"BLpopulateProjects");
}
/* END Added by A*/

function getProjectList(region){
	var url = ctx+"/getProjectList?region="+region;
	submitToURL(url,"populateProjects");
}

/*Added by A*/
function BLpopulateProjects(response){
	
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
		var url = ctx+"/pr/getContactsBL?projectSfid="+project;
		submitToURL(url,"populateContacts");
	}
	
	function getContacts(){
		
		/*Added by A*/
		
		$('#getContacts').html("Loading...");
		$('#blNoteMsg').show();
		
		var project = $('#projectList').find('option:selected');
		var selectedProject = [];
		$(project).each(function(index, brand){
			selectedProject.push($(this).val());
		});
		
		if (selectedProject != "") {
			selectedProject = selectedProject.join(",");
		} else {
			alert("Please select the filter parameters!");
			selectedProject = "";
			
			$('#getContacts').html("Get Contacts");
			$('#blNoteMsg').hide();
			
			return;
		}
		
		var fromDate = $('#fromDate').val();
		var toDate =  $('#toDate').val();
		if(fromDate ==null || toDate == null){
			alert("Please select the filter parameters!");
			
			$('#getContacts').html("Get Contacts");
			$('#blNoteMsg').hide();
			
			return "";
		}
		
		/* END Added by A*/
		
		var url = ctx+"/pr/getContactsBL?projectSfid="+selectedProject+"&fromDate="+fromDate+"&toDate="+toDate;
		submitToURL(url,"populateContacts");
		
		/*var project = $('#projectList').find(':selected').val();
		var fromDate = $('#fromDate').val();
		var toDate =  $('#toDate').val();
		if(project == null || fromDate ==null || toDate == null){
			alert("Please select the filter parameters!")
			return "";
		}
		var url = ctx+"/pr/getContactsBL?projectSfid="+project+"&fromDate="+fromDate+"&toDate="+toDate;
		submitToURL(url,"populateContacts");*/
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
		
		var html = '';
		var touchpointID = '';

		$.each(contacts, function(key, entry){
			/*$('#surveyContactTable tbody').append('<tr><td>'+entry.firstName+'</td><td>'
					+entry.email+'</td><td>'+entry.mobile
					+'</td><td>'+entry.name+'</td><td>'+entry.bookingDate+'</td>'
					+'</td><td>'+entry.surveyType+'</td>'
					+'<td>'+entry.field15+'</td>'
					+'<td>'+entry.propertyName+'</td>'
					+'</tr>');*/
			
			if (entry.surveyType != undefined && entry.surveyType != null && entry.surveyType != 'null') {
				if (entry.surveyType == "PRE_POSSESSION") {
					touchpointID = 'ntp4_touchpoint';
				} else if (entry.surveyType == "POST_POSSESSION") {
					touchpointID = 'epn8_touchpoint';
				}
			} else {
				touchpointID = '';
			}
			
			/*Added by A*/
			html += '<tr>'
				+'<td>Touchpoint</td>'
				+'<td>'+touchpointID+'</td>'
				+'<td>'+entry.mobile+'</td>'
				+'<td>'+entry.transactionDate+'</td>'
				+'<td>'+entry.email+'</td>'
				+'<td>'+entry.firstName+'</td>'
				+'<td>'+entry.name+'</td>'
				+'<td>'+entry.bookingDate+'</td>'
				+'<td>'+entry.sentDate+'</td>' 
				+'<td>'+entry.sentStatus+'</td>' 
				+'<td>'+entry.field1+'</td>' 
				+'<td>'+entry.field2+'</td>' 
				+'<td>'+entry.field3+'</td>' 
				+'<td>'+entry.field4+'</td>' 
				+'<td>'+entry.field6+'</td>' 
				+'<td>'+entry.field8+'</td>' 
				+'<td>'+entry.field9+'</td>' 
				+'<td>'+entry.field10+'</td>' 
				+'<td>'+entry.field11+'</td>' 
				+'<td>'+entry.field13+'</td>' 
				+'<td>'+entry.field14+'</td>' 
				+'<td>'+entry.field15+'</td>' 
				+'<td>'+entry.field16+'</td>' 
				+'<td>'+entry.field18+'</td>' 
				+'<td>'+entry.field20+'</td>' 
				+'<td>'+entry.surveyType+'</td>' 
				+'<td>'+entry.propertyName+'</td>' 
				+'<td>'+entry.field15+'</td>' 
			+'</tr>';
			/*END Added by A*/
		});		
		
		html = html.replace(/null/g, "");
		$('#surveyContactTable tbody').append(html);
		
		/*$('#surveyContactTable').DataTable( {
			dom: 'Bfrtip',
			"buttons": [{ "extend": 'excel', "text":'Export To Excel',"className": 'btn btn-default btn-xs' }],
		});*/
		
		$('#surveyContactTable').DataTable({
			dom: 'Bfrtip',
	        "buttons": [{ 
	        		"title": null,  
	        		"messageTop": null,
	        		"messageBottom": null,   
	        		"extend": 'excel', 
	        		"text":'Export To Excel',
	        		"className": 'btn btn-default btn-xs' 
	        	}],
	    });
		
		$('#getContacts').html("Get Contacts");
		$('#blNoteMsg').hide();
		
/*		$('#surveyContactTable').DataTable( ).fnClearTable();
		$('#surveyContactTable').DataTable( ).fnDestroy();
*/		return;
	}