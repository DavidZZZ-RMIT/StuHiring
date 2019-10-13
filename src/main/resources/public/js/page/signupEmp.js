window.onload =  ()=> {
	$('#brithdatetimepicker').datetimepicker({
		format: 'L',
		viewMode: 'years',
		format: 'DD/MM/YYYY',
		defaultDate: "1/1/2000",
	});

	$('#submitBtn').on('click', function (event) {
		event.preventDefault();

		var formData = {
			email: $("#email").val().toLowerCase(),
			pwd: md5($("#pwd").val()),
			firstname:$("#firstname").val(),
			lastname:$("#lastname").val(),
			username:$("#username").val(),
			companyname:$("#company").val(),
			companydiscrption:$("#companydiscrption").val(),
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
		
		if(formData.companyname.length == 0){
			Swal.fire("Your Company Name is empty!", "Plz input your Company Name", "warning");
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

		ajaxEntity("POST","./registEmployer",formData,(responseJson)=>{
			// console.log(responseJson);
			if(responseJson.result != undefined && 
					responseJson.result == "success"
					){
				window.location = "emphome.html";
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