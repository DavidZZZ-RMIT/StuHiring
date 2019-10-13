window.onload =  ()=> {
	$('#submitBtn').on('click', function (event) {
		event.preventDefault();

		var formData = {
			description: $("#description").val(),
			title:$("#title").val(),
		}

		if(formData.title.length == 0){
			Swal.fire("The Title is empty!", "Plz input the title of this job", "warning");
			return false;
		}

		if(formData.description.length == 0){
			Swal.fire("The Description is empty!", "Plz input the description of this job", "warning");
			return false;
		}
		
		ajaxEntity("POST","./postJob",formData,(responseJson)=>{
			// console.log(responseJson);
			if(responseJson.result != undefined && responseJson.result == "success"
					){
				window.location = "myjobs.html";
			}else{
				Swal.fire("Post Job Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
			}
		},()=>{
			Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
		});
		return false;
	});


}