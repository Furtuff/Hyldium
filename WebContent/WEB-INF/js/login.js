window.addEventListener('load',function(){
	var token = sessionStorage.getItem('token');
	var success = sessionStorage.getItem('success');
	if(!token || 0 == token.length || !success){
		var form = document.getElementById('login-form');
		if (form.attachEvent) {
	    form.attachEvent("submit", processForm);
		} else {
	    form.addEventListener("submit", processForm);
		}
	}else{
		httpPost(token);
	}
});
function processForm(e) {
    if (e.preventDefault) e.preventDefault();
    var elements = document.getElementById("login-form").elements;
    var obj ={};
    for(var i = 0 ; i < elements.length ; i++){
        var item = elements.item(i);
        obj[item.name] = item.value;
    }
   

    obj.password = btoa(obj.password);
    

    var jsonParam = JSON.stringify(obj);
    
    httpPost(jsonParam);
    return false;
}
function httpPost(jsonParam)
{
	 sessionStorage.setItem('token',jsonParam);
	var theUrl = window.location.href 
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "POST", theUrl, true ); 
    xmlHttp.onload = function(){
        document.open();
        document.write(xmlHttp.responseText);
        document.close();
    };
	xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(jsonParam);
    return xmlHttp.responseText;
}
function base64ToArray(base64) {
	
	 bytes = new Uint8Array(base64);
    return bytes;
}
