$(document).ready(function(){

	$
    $("#add-product").click(function () {
		$("#add-item-form").show();
	});
    $('form.search-product :input').change(function () {
        var elements = document.getElementById("search").elements;

        var obj = {};
        for (var i = 0; i < elements.length; i++) {
            var item = elements.item(i);
            obj[item.name] = item.value;
        }
        var objs = [obj];
        var jsonParam = JSON.stringify(objs);
        httpPostProduct(jsonParam, "search");
    });
    $("#add-item-form").submit(function (e) {
		e.preventDefault();
        var elements = document.getElementById("add-item-form").elements;
	    var obj ={};
	    for(var i = 0 ; i < elements.length ; i++){
	        var item = elements.item(i);
	        	obj[item.name] = item.value;   
	    }
	    
	    var objs = [obj];
	    var jsonParam = JSON.stringify(objs);
        httpPostProduct(jsonParam, "update");
	    return false;
        $('#add-item-form').trigger("reset");
        $("#add-item-form").hide();
		

	});
	$('form.product-form :input').change(function(){
		if(!$(this).closest('form').find('input[class="changed"]').val()){
		 $(this).closest('form').append('<input type="hidden" class="changed" value="true" /> ');
		}

	});
	
	$('form.product-form button.delete').click(function(e){
		if(window.confirm("supprimer ?")){
			var obj = {};
			obj['id'] = this.dataset.id;
			var objs = [obj];
            httpPostProduct(JSON.stringify(objs), 'update');
		}
	})
    $("#cancel-product").click(function () {
		location.reload();
	});
    $("#save-product").click(function () {
		var text ="";
		var list = [];
		var count = 0; 
		$('form.product-form').each(function() {
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
				paramObj['id'] = $(this).closest('form').attr('method');
				list.push(paramObj);
				
			}

			
		});

        httpPostProduct(JSON.stringify(list), 'update');
		
	});
	
	
	
});
function httpPostProduct(jsonParam, action)
{
	var element = document.getElementById('frame-container');
	var auth =JSON.parse(sessionStorage.getItem('token'));
	var theUrl = window.location.href +"/products"+"/"+action;
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "POST", theUrl, true ); 
    xmlHttp.overrideMimeType('application/javascript');
    xmlHttp.onload = function(){
       element.innerHTML = xmlHttp.responseText;
       $(document).ready(function() {
    	var elem = document.getElementById('js');
           $.getScript(window.location.href.replace('logged', 'js/products')).done(function (script, textStatus) {
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