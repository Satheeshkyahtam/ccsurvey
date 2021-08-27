$(document).ready(function(){
	$("#getContacts").on("click", getContacts);
});

	function onChangeProject(){		
		var project = $(this).find(':selected').val();
	    getContactList(project);
		
	}	
	
	function getContactList(project){
		var url = ctx+"/pr/getContactsREG?projectSfid="+project;
		submitToURL(url,"populateContacts");
	}
	
	function getContacts(){
		var project = $('#projectList').find(':selected').val();
		if(project == null){
			alert("Please select the filter parameters!")
			return "";
		}
		var url = ctx+"/pr/getContactsREG?projectSfid="+project;
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
		return;
	}