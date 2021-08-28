$(document).ready(function(){
	$("#getContacts").on("click", getContacts);
});

	function onChangeProject(){		
		var project = $(this).find(':selected').val();
	    getContactList(project);
		
	}	
	
	function getContactList(project){
		var url = ctx+"/pr/getContactsHOV?projectSfid="+project;
		submitToURL(url,"populateContacts");
	}
	
	function getContacts(){
		
		/*Added by A*/
		
		$('#getContacts').html("Loading...");
		$('#hoNoteMsg').show();
		
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
		/* END Added by A*/
		
		/*
		var project = $('#projectList').find(':selected').val();
		if(project == null){
			alert("Please select the filter parameters!")
			return "";
		}*/
		
		var url = ctx+"/pr/getContactsHOV?projectSfid="+selectedProject;
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
		
		var html = '';
		
		$.each(contacts, function(key, entry){
			/*$('#surveyContactTable tbody').append('<tr><td>'+entry.firstName+'</td><td>'
					+entry.email+'</td><td>'+entry.mobile
					+'</td><td>'+entry.name+'</td><td>'+entry.bookingDate+'</td>'
					+'</td><td>'+entry.surveyType+'</td>'
					+'<td>'+entry.field15+'</td>'
					+'<td>'+entry.propertyName+'</td>'
					+'</tr>');*/
			/*Added by A*/
			 
			html += '<tr>'
						+'<td>Touchpoint</td>'
						+'<td>ivhh_touchpoint</td>'
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
		
		$('#surveyContactTable').DataTable( {
	        dom: 'Bfrtip',
	        "buttons": [{ 
	        		"title": null,  
	        		"messageTop": null,
	        		"messageBottom": null,   
	        		"extend": 'excel', 
	        		"text":'Export To Excel',
	        		"className": 'btn btn-default btn-xs' 
	        	}],
	    } );
		
		$('#getContacts').html("Get Contacts");
		$('#hoNoteMsg').hide();
		return;
	}