$(document).ready(function(){
	
	$("#regionList").on("change", onChangeRegion);
	$("#getContacts").on("click", getContacts);
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
	function onChangeProject(){
		
		var project = $(this).find(':selected').val();
	    getContactList(project);
		
	}	
	
	function getContactList(project){
		var url = ctx+"/pr/getContactsBL?projectSfid="+project;
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
		var url = ctx+"/pr/getContactsBL?projectSfid="+project+"&fromDate="+fromDate+"&toDate="+toDate;
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