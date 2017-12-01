$(document).ready(function(){
	sessionStorage.setItem('success',true);
	var selected = sessionStorage.getItem('menu')

	$("a").click(function(){
	    
	    $("a.active").removeClass("active");
	    $(this).addClass("active");
	    sessionStorage.setItem('menu',this.id);
	    var cac = this;
	});
	
	if(selected || selected.length >0){
		$('#'+selected).trigger('click');
	}
});


