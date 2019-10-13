var url = new URL(window.location.href);
var jobid = undefined;

ajaxEntity("POST","./getJob",{id:url.searchParams.get("id")},(responseJson)=>{
	// console.log(responseJson);
	if(responseJson.result != undefined && 
			responseJson.result == "success"
			){
		$("#title").val(responseJson.job.title);
		$("#description").val(responseJson.job.description);
		jobid = responseJson.job.jobId;
		if(!responseJson.editable){
			$("#title").prop("readonly", true);
			$("#description").prop("readonly", true);
			$('#updateBtn').hide();
		}
	}else{
		Swal.fire("The Job is not exist!!!", "Plz contact s3705795!", "danger")
	}
},()=>{
	Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
});	


window.onload =  ()=> {
	$('#updateBtn').on('click', function (event) {
		event.preventDefault();

		var formData = {
			description: $("#description").val(),
			title:$("#title").val(),
			jobId:jobid
		}

		if(formData.title.length == 0){
			Swal.fire("The Title is empty!", "Plz input the title of this job", "warning");
			return false;
		}

		if(formData.description.length == 0){
			Swal.fire("The Description is empty!", "Plz input the description of this job", "warning");
			return false;
		}
		
		ajaxEntity("POST","./updateJob",formData,(responseJson)=>{
			// console.log(responseJson);
			if(responseJson.result != undefined && responseJson.result == "success"
					){
				window.location = "job.html?id="+jobid;
			}else{
				Swal.fire("Update Job Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
			}
		},()=>{
			Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
		});
		return false;
	});
	
	$('#removeBtn').on('click', function (event) {
		event.preventDefault();
		
		ajaxEntity("POST","./removeJob",{jobId:jobid},(responseJson)=>{
			// console.log(responseJson);
			if(responseJson.result != undefined && responseJson.result == "success"
					){
				Swal.fire({
					  position: 'top-end',
					  type: 'success',
					  title: 'Removed this job successfully !',
					  showConfirmButton: false,
					  timer: 1500
					}).then(()=>{
						window.location = "myjobs.html";

					});
			}else{
				Swal.fire("Remove Job Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
			}
		},()=>{
			Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
		});
		return false;
	});
}