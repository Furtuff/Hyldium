$(document).ready(function(){
	
	$("#user-form").submit(function(e){
	    e.preventDefault();
	  });
	$
	$("#add").click(function(){
		$("#add-form").show();
	});
	$("#add-form").submit(function(e){
		e.preventDefault();
		var elements = document.getElementById("add-form").elements;
	    var obj ={};
	    for(var i = 0 ; i < elements.length ; i++){
	        var item = elements.item(i);
	        if(item.name == 'password'){
	        	obj[item.name] =btoa(item.value);
	        }else{
	        	obj[item.name] = item.value;
	        }
	        
	    }
	    if ($('.admin-box').prop("checked")){
			
	    	obj.role = [obj.role,'lambda'];
	    } else{
	    	obj.role = ['lambda'];
	    }
	    
	    obj.password = btoa(obj.password);
	    var objs = [obj];
	    var jsonParam = JSON.stringify(objs);
	    
	    httpPostUser(jsonParam, "add");
	    return false;
		$('#add-form').trigger("reset");
		$("#add-form").hide();
		

	});
	$('form.user-form :input').change(function(){
		if(!$(this).closest('form').find('input[class="changed"]').val()){
		 $(this).closest('form').append('<input type="hidden" class="changed" value="true" /> ');
		}

	});
	
	$('form.user-form button.resetpwd').on('click',function(e){
		$('.'+this.dataset.id).show();
	});
	$('form.user-form button.delete').click(function(e){
		if(window.confirm("supprimer ?")){
			var obj = {};
			obj['id'] = this.dataset.id;
			var objs = [obj];
			httpPostUser(JSON.stringify(objs),'update');
		}
	})
	$("#cancel").click(function(){
		location.reload();
	});
	$("#save").click(function() {
		var text ="";
		var list = [];
		var count = 0; 
		$('form.user-form').each(function() {
			if($(this).closest('form').find('input[class="changed"]').val()){
				var paramObj = {};
				$.each($(this).closest('form').serializeArray(),function(_, kv){
					count++;
					if (paramObj.hasOwnProperty(kv.name)) {
					    paramObj[kv.name] = $.makeArray(paramObj[kv.name]);
					    paramObj[kv.name].push(kv.value);
					  }
					  else {
					    paramObj[kv.name] = kv.value;
					  }
				});
				if (paramObj.hasOwnProperty('admin-box')){
					paramObj['role'] = ['admin','lambda'];
			    } else{
			    	paramObj['role']= ['lambda'];
			    }
				paramObj['id'] = $(this).closest('form').attr('method');
				list.push(paramObj);
				
			}

			
		});
		
		httpPostUser(JSON.stringify(list),'update');
		
	});
	
	
	
});
function httpPostUser(jsonParam, action)
{
	var element = document.getElementById('frame-container');
	var auth =JSON.parse(sessionStorage.getItem('token'));
	var theUrl = window.location.href +"/user"+"/"+action;
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "POST", theUrl, true ); 
    xmlHttp.overrideMimeType('application/javascript');
    xmlHttp.onload = function(){
       element.innerHTML = xmlHttp.responseText;
       $(document).ready(function() {
    	var elem = document.getElementById('js');
    	$.getScript( window.location.href.replace('logged','js/users')).done(function( script, textStatus ) {
  		  $('head').append(script);
  		  console.log( textStatus );
  		});    	
       });
    };
	xmlHttp.setRequestHeader("Content-Type", "application/json");
	xmlHttp.setRequestHeader("Authorization", "Basic " + btoa(auth.login+':'+bin2Str(auth.password)));
    xmlHttp.send(jsonParam);
    return xmlHttp.responseText;
}
function string2Bin(str) {
	  var result = [];
	  for (var i = 0; i < str.length; i++) {
	    result.push(str.charCodeAt(i));
	  }
	  return result;
	}