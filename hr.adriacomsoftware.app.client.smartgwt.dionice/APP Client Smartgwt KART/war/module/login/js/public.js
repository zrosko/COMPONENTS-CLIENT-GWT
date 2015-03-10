$(document).ready(function () {
	$('#loginform').submit(function (e) {
		e.preventDefault();
		
		$('#login').button('loading');
    	$('.form-group').each(function () { $(this).removeClass('has-error'); });	 
    	$('fieldset').attr('disabled', 'disabled');
		
		var request = $.ajax ({
         	type: "POST",
         	url: "/ajax/json.php?login&lang=" + $('html').attr('lang'),
         	dataType: "json",
         	data: {	         		
            	"user_name" : $('#user_name').val(),
            	"user_password" : $('#user_password').val(),
            	"login" : $('#login').val()
            }
      	});
	 
      	request.done(function(data, textStatus, jqXHR){
      		if (data.success) {
	      		location = '/';
      		} else {
	      		$('#alert').html('<i class="fa fa-warning fa-fw fa-lg"></i> ' + data.errors[0]);
	      		
				for (var i = 0; i < data.fields.length; i++) {
					$('#' + data.fields[i]).parent().parent().addClass('has-error');
				}	      		
	      		
      			$('#login').button('reset'); 
    			$('fieldset').removeAttr('disabled');
      		}
     	});
 
      	request.fail(function(jqXHR, textStatus, errorThrown) {	
      		$('#alert').html('<i class="fa fa-warning fa-fw fa-lg"></i> ' + errorThrown);
	      		
  			$('#login').button('reset'); 
			$('fieldset').removeAttr('disabled');
      	});	
	});
});