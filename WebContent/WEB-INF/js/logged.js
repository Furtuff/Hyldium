$(document).ready(function(){
	if(!sessionStorage.getItem('success')){
		url = window.location.href.replace('login','logged');

		sessionStorage.setItem('success',true);
		window.location = url;
	}
	var selected = sessionStorage.getItem('menu');

	$("a").click(function(){
		$("#titre").text($(this).text());
	    $("a.active").removeClass("active");
	    $(this).addClass("active");
	    if(this.id == 'disconnect'){
	    	sessionStorage.clear();
	    	window.location =  window.location.href.replace('logged','login');
	    }else{
		    sessionStorage.setItem('menu',this.id);
		    var container = document.getElementById('frame-container');
		    var subMenu = {};
		    subMenu['sub'] = this.id;
		    httpPost(JSON.stringify(subMenu), container);
	    }
	});
	
	if(selected){
		$('#'+selected).trigger('click');
	}
});
function httpPost(jsonParam, element)
{
	var auth =JSON.parse(sessionStorage.getItem('token'));
	var theUrl = window.location.href 
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "POST", theUrl, true ); 
    xmlHttp.overrideMimeType('application/javascript');
    xmlHttp.onload = function(){
       element.innerHTML = xmlHttp.responseText;
       $(document).ready(function() {
    	var elem = document.getElementById('js');
    	$.getScript( theUrl.replace('logged',elem.dataset.js)).done(function( script, textStatus ) {
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
function bin2Str(bin) {
	return String.fromCharCode.apply(String, bin);
}
