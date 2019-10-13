var url = new URL(window.location.href);
var jobid = undefined;
var employer = undefined;
var cvs = {};

ajaxEntity("POST","./getJob",{id:url.searchParams.get("id")},(responseJson)=>{
	// console.log(responseJson);
	if(responseJson.result != undefined && 
			responseJson.result == "success"
			){
		$("#title").text(responseJson.job.title);
		$("#description").text(responseJson.job.description);
		employer = responseJson.job.employer;
		jobid = responseJson.job.jobId;
	}else{
		Swal.fire("The Job is not exist!!!", "Plz contact s3705795!", "danger")
	}
},()=>{
	Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
});	

ajaxEntity("POST","./getMyCVs",{},(responseJson)=>{
	// console.log(responseJson);
	if(responseJson != undefined){
		jQuery.each(responseJson, function(i, cv) {
			cvs[i] = cv.title;
			});
		}else{
		Swal.fire("Fetch CV Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
	}
},()=>{
	Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
});	

window.onload =  ()=> {
	$('#applyBtn').on('click', function (event) {
		event.preventDefault();
	
		Swal.fire({
			  title: 'Select CV to apply this Job',
			  input: 'select',
			  inputOptions: cvs,
			  inputPlaceholder: 'Select a CV of yours',
			  showCancelButton: true,
			}).then((cvs) => {
			  if (cvs.value) {
				  Swal.fire({
					  title: 'leave a message to the employer',
					  input: 'textarea',
					  inputOptions: cvs,
					  inputPlaceholder: 'I want to apply this job ...',
					  showCancelButton: true,
					}).then((msg) => {
					  if (msg.value) {
						  ajaxEntity("POST","./applyJob",{
							  CVID:cvs.value,
							  jobID:jobid,
							  employer:employer,
							  message:msg.value
						  },(responseJson)=>{
							  if(responseJson.result != undefined && responseJson.result == "success"){
								  Swal.fire({
									  position: 'top-end',
									  type: 'success',
									  title: 'Your Application has been sent to employer successfully !',
									  showConfirmButton: false,
									  timer: 1500
									})
							  }else{
									Swal.fire("Apply Job Failed!", responseJson.msg, "danger")
							}
							},()=>{
								Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
							});	

					  }
					})
			  }
			})

	});
}