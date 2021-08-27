

$(function(){
    $.ajaxSetup({
    	 statusCode: {
             401: function(){
            	 location.reload(); // or window.location="http://www.example.com"
                 }
         },
         headers: {
	            'X-CSRF-Token': $('meta[name="_csrf"]').attr('content')
	        }
    });
});


$( document ).ready(function() {
   $('.formSubmit').click(function(event){
	   event.preventDefault();
	   var formObj = $(this).parents('form:first');
	   submitForm(formObj);
	   return false;
   });

	  $('.formSubmitJson').click(function(event){
		   event.preventDefault();
		   var formObj = $(this).parents('form:first');
		   submitFormJson(formObj);
		   return false;
	   });
});

function submitFormJson(formObj){
	var formData = formObj.serializeJSON() ;
	var uri =formObj.attr( 'action' );
	$.ajax({
        type : "POST",
        contentType: "application/json",
        data : JSON.stringify(formData), 
        url: uri,
        dataType: "json",
        async: false,
        success: function (data) {
        	 
        	try{
        		processResponse(data);
        	}catch(e){
        		console.log(e);
        		commonResponseHandler(data);
        	}
        },
        error: function (ex) {
        	springFormExceptionHandler(ex);
        }
    });
	return false;
}

function submitForm(formObj){
	var formData = formObj.serialize() ;
	var uri =formObj.attr( 'action' );
	$.ajax({
        type : "POST",
        data : formData, 
        url: uri,
        async: false,
        success: function (data) {
        	 
        	try{
        		processResponse(data);
        	}catch(e){
        		console.log(e);
        		commonResponseHandler(data);
        	}
        },
        error: function (ex) {
        	springFormExceptionHandler(ex);
        }
    });
	//return false;
}

function submitIt(formId,action, callback){
	$("#loading-wrapper").show();
	var validate=checkValidationOnSubmit(formId);
	if(validate){
			var formData = $('#'+formId).serialize();
			var uri =action;
			setTimeout(function(){
				$.ajax({
		        type : "POST",
		        data : formData,
		        url: uri,
		        async: false,
		        success: function (data) {
		        	try{
		       			window[callback](data);
		        	}catch(e){
		        		console.log(e);
		        		commonResponseHandler(data);
		        		/*window.location.href="sessionOut"; */
		        	}
		        },
		        beforeSend : function() {
		        	$("#loading-wrapper").show();
		        },
		        complete : function() {
		        	$("#loading-wrapper").fadeOut("slow");
		        },
		        error: function (ex) {
		        	springFormExceptionHandler(ex);
		        }
				},10);
		    });
			return false;
		}
	else{
		/*CreateCaptcha();*/
		$("#loading-wrapper").fadeOut("slow");
	}
	return false;
	
}

function submitWithParam(url, paramFieldId, callback){
	 
	var val = $("#"+paramFieldId).val();
	url = url+'/'+val;
	$.ajax({
        type : "POST",
        contentType : "application/json",
        url: url,
        dataType:"json",
        async:false,
        success: function (data) {
        	 
        	try{
        		window[callback](data);
        	}catch (e){
        		console.log(e);
        		urlResponseHandler(data);
        	}
        },
        error: function (ex) {
        	springFormExceptionHandler(ex);
        }
    });
	return false;
}
function submitWithTwoParam(url, firstParamFieldId,secondParamFieldId, callback){
	 
	var firstValue = $("#"+firstParamFieldId).val();
	var secondValue = $("#"+secondParamFieldId).val();
	url = url+'/'+firstValue+'/'+secondValue;
	$.ajax({
        type : "POST",
        contentType : "application/json",
        url: url,
        dataType:"json",
        async:false,
        success: function (data) {
        	 
        	try{
        		window[callback](data);
        	}catch (e){
        		console.log(e);
        		urlResponseHandler(data);
        	}
        },
        error: function (ex) {
        	springFormExceptionHandler(ex);
        }
    });
	return false;
}

function submitToURL(url, callback){
	$.ajax({
        type : "POST",
        contentType : "application/json",
        url: url,
        dataType:"json",
        async:true,
        success: function (data) {
        	try{
        		window[callback](data);
        	}catch (e){
        		console.log(e);
        		urlResponseHandler(data);
        	}
        },
        error: function (ex) {
        	springFormExceptionHandler(ex);
        }
    });
	return false;
}


function urlResponseHandler(data){
	if(data != null && data.message !=null){
		Alert.info (data.message);
	}else{
		Alert.warn("OK");
	}
}

function commonResponseHandler(data){
	 
	/*CreateCaptcha();*/
	if(data != null && data.response !== null && data.response.message != null){
		Alert.info (data.response.message);
	}else{
		Alert.warn("OK");
	}	
}

function directSubmit(event,form, action){
	event.preventDefault();
	$("#"+form).attr( 'action', action );
	$("#"+form).submit();
}

function submitWithTwoParam(url, firstParamFieldId,secondParamFieldId, callback){
	 
	var firstValue = $("#"+firstParamFieldId).val();
	var secondValue = $("#"+secondParamFieldId).val();
	url = url+'/'+firstValue+'/'+secondValue;
	$.ajax({
        type : "POST",
        contentType : "application/json",
        url: url,
        dataType:"json",
        async:false,
        success: function (data) {
        	try{
        		window[callback](data);
        	}catch (e){
        		console.log(e);
        		urlResponseHandler(data);
        	}
        },
        error: function (ex) {
        	springFormExceptionHandler(ex);
        }
    });
	return false;
}
function submitWithThreeParam(url, name,paramFieldName,paramFieldId,callback){
	 
	$("#loading-wrapper").show();
	var value = $("#"+paramFieldId).val();
	url = url+'/'+name+'/'+paramFieldName+'/'+value;
	setTimeout(function(){
	$.ajax({
        type : "POST",
        contentType : "application/json",
        url: url,
        dataType:"json",
        async:false,
        success: function (data) {
        	 
        	try{
        		window[callback](data);
        	}catch (e){
        		console.log(e);
        		urlResponseHandler(data);
        	}
        },
        beforeSend : function() {
        	$("#loading-wrapper").show();
        },
        complete : function() {
        	$("#loading-wrapper").fadeOut("slow");
        },
        error: function (ex) {
        	springFormExceptionHandler(ex);
        }
	},10);
    });
	return false;
}
$(window).on('load',function(){
    $.ajaxSetup({
        statusCode: {
            401: function(){
                    location.reload(); // or window.location="http://www.example.com"
                }
        }
    });
});

function submitWithParamByParamClass(url, paramFieldClass, callback){
	
	var firstValue = $("."+paramFieldClass).val();
	url = url+'/'+firstValue;
	$.ajax({
        type : "POST",
        contentType : "application/json",
        url: url,
        dataType:"json",
        async:false,
        success: function (data) {
        	 
        	try{
        		window[callback](data);
        	}catch (e){
        		console.log(e);
        		urlResponseHandler(data);
        	}
        },
        error: function (ex) {
        	springFormExceptionHandler(ex);
        }
    });
	return false;
}



function springFormExceptionHandler(ex){
	var status=ex.status;
	if(status==401 || status==405 || status==403){
		window.location.href="sessionOut";
	}
	else if(status==200){
		
	}else{
		Alert.warn("Exception: "+ex.responseText);	
	}
}