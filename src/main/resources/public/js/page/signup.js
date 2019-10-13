window.onload =  ()=> {
	$('#submitBtn').on('click', function (event) {
		event.preventDefault();

		var formData = {
			email: $("#email").val().toLowerCase(),
			pwd: md5($("#pwd").val()),
			firstname:$("#firstname").val(),
			lastname:$("#lastname").val(),
			username:$("#username").val(),
			gender:$("#gender").val(),
			school:$("#school").val(),
			major:$("#major").val(),
			graduateyear:$("#graduateyear").val(),
			action: "signup",
		}

		var repwd = md5($("#repwd").val());
		
		if(formData.firstname.length == 0){
			Swal.fire("Your First Name is empty!", "Plz input your first name", "warning");
			return false;
		}

		if(formData.lastname.length == 0){
			Swal.fire("Your Last Name is empty!", "Plz input your last name", "warning");
			return false;
		}
		
		if(formData.username.length == 0){
			Swal.fire("Your User Name is empty!", "Plz input your user name", "warning");
			return false;
		}

		if(formData.email.length == 0){
			Swal.fire("Your Email address is empty!", "Plz input your Email address", "warning");
			return false;
		}

		if($("#pwd").val().length == 0){
			Swal.fire("Password is empty!", "at least 6 characters", "warning");
			return false;
		}

		if($("#pwd").val().length <6){
			Swal.fire("Password is too short", "at least 6 characters", "warning");
			return false;
		}
		if ($("#pwd").val() != $("#repwd").val()) {
			Swal.fire("Password not match", "Plz check your passwords", "warning")
			return false;

		}

		ajaxEntity("POST","./registStudent",formData,(responseJson)=>{
			// console.log(responseJson);
			if(responseJson.result != undefined && 
					responseJson.result == "success"
					){
				window.location = "stuhome.html";
			}else{
				if(responseJson.isMemberExist != undefined && responseJson.isMemberExist){
					Swal.fire("Sign up Failed!", "The email has been signed up, plz try another email", "warning")

				}else
				Swal.fire("Sign up Failed!", "Unexpected issue, Plz contact s3705795!", "danger")
			}
		},()=>{
			Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
		});
		return false;
	});


}