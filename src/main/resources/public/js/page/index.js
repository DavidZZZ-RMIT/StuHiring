window.onload =  ()=> {
	$('#signinBtn').on('click', function (event) {
		event.preventDefault();
		var formData = {
				email: $("#email").val().toLowerCase(),
				pwd: md5($("#pwd").val()),
				action: "login",
			}
		
		if($("#pwd").val().length == 0){
			Swal.fire({
				  type: 'warning',
				  title: 'Oops...',
				  text: 'Password is empty!',
				  footer: 'at least 6 characters'
				});
			return false;
		}
		if(formData.email.length == 0){
			Swal.fire({
				  type: 'warning',
				  title: 'Oops...',
				  text: 'Your Email address is empty!',
				  footer: 'Plz input your Email address'
				});
			return false;
		}
		ajaxEntity("POST","./login",formData,(responseJson)=>{
			// console.log(responseJson);
			if(responseJson.result != undefined && 
					responseJson.result == "success" 
					){
				if(responseJson.usertype == "student"){
					window.location = "stuhome.html";
				}
				else if(responseJson.usertype == "employer"){
					window.location = "emphome.html";
				}
				else if(responseJson.usertype == "stuff"){
					window.location = "stuffhome.html";
				}
			}else if(responseJson.msg !=undefined){
					Swal.fire({
						  title: "Notification",
						  text: responseJson.msg,
						  type: "warning",
						});
					}
		},()=>{
			Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
		});
	
		return false;
	});
	

}