var url = new URL(window.location.href);
var applicationid = undefined;
var jobid = undefined;
var job = undefined;

ajaxEntity("POST","./getApplication",{id:url.searchParams.get("id")},(responseJson)=>{
	// console.log(responseJson);
	if(responseJson.result != undefined && 
			responseJson.result == "success"
			){
		$("#title").text(responseJson.job.title+" ("+responseJson.application.status+")");
		$("#description").text(responseJson.job.description);
		$("#message").text(responseJson.application.message);
		$("#applicant").text(responseJson.applicant.userName);
		$("#cv").text(responseJson.cv.content);
		jobid = responseJson.job.jobId;
		applicationid = responseJson.application.id;
		job = responseJson.job;
		
		if(responseJson.application.status == "Accepted"){
			$("#acceptBtn").hide();
			$("#rejectBtn").hide();
			$("#resetBtn").show();
		}else if(responseJson.application.status == "Rejected"){
			$("#acceptBtn").hide();
			$("#rejectBtn").hide();
			$("#resetBtn").show();
		}else{
			$("#acceptBtn").show();
			$("#rejectBtn").show();			
			$("#resetBtn").hide();
		}
		ajaxEntity("POST","./getCommentsOfApplication",{applicationId : responseJson.application.id},(cmtsJson)=>{
			jQuery.each(cmtsJson, function(i, comment) {
				addCommentBlock(i,(responseJson.application.employer == comment.author?"Myself":"Applicant"),comment.dateStr,comment.content);
			});
		});
	}else{
		Swal.fire("The JobApplication is not exist!!!", "Plz contact s3705795!", "danger")
	}
},()=>{
	Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
});

window.onload =  ()=> {
	$('#acceptBtn').on('click', function (event) {
		event.preventDefault();

		var formData = {
			id:applicationid,
			action:"accept"
		}

		ajaxEntity("POST","./handleApplication",formData,(responseJson)=>{
			// console.log(responseJson);
			if(responseJson.result != undefined && responseJson.result == "success"
					){
				  Swal.fire({
					  position: 'top-end',
					  type: 'success',
					  title: 'The Application has been updated successfully !',
					  showConfirmButton: false,
					  timer: 1500
					}).then(()=>{
						$("#title").text(job.title+" (Accepted)");
						$("#acceptBtn").hide();
						$("#rejectBtn").hide();
						$("#resetBtn").show();
					});
					
			}else{
				Swal.fire("Update Application Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
			}
		},()=>{
			Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
		});
		return false;
	});
	
	$('#rejectBtn').on('click', function (event) {
		event.preventDefault();

		var formData = {
			id:applicationid,
			action:"reject"
		}

		ajaxEntity("POST","./handleApplication",formData,(responseJson)=>{
			// console.log(responseJson);
			if(responseJson.result != undefined && responseJson.result == "success"
					){
				  Swal.fire({
					  position: 'top-end',
					  type: 'success',
					  title: 'The Application has been updated successfully !',
					  showConfirmButton: false,
					  timer: 1500
					}).then(()=>{
						$("#title").text(job.title+" (Rejected)");
						$("#acceptBtn").hide();
						$("#rejectBtn").hide();
						$("#resetBtn").show();
					});
					
			}else{
				Swal.fire("Update Application Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
			}
		},()=>{
			Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
		});
		return false;
	});
	
	$('#resetBtn').on('click', function (event) {
		event.preventDefault();

		var formData = {
			id:applicationid,
			action:"reset"
		}

		ajaxEntity("POST","./handleApplication",formData,(responseJson)=>{
			// console.log(responseJson);
			if(responseJson.result != undefined && responseJson.result == "success"
					){
				  Swal.fire({
					  position: 'top-end',
					  type: 'success',
					  title: 'The Application has been updated successfully !',
					  showConfirmButton: false,
					  timer: 1500
					}).then(()=>{
						$("#title").text(job.title+" (Requesting)");
						$("#acceptBtn").show();
						$("#rejectBtn").show();
						$("#resetBtn").hide();
					});
					
			}else{
				Swal.fire("Update Application Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
			}
		},()=>{
			Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
		});
		return false;
	});

	$('#commentBtn').on('click', function (event) {
		event.preventDefault();

		Swal.fire({
			  title: 'leave a comment in this application',
			  input: 'textarea',
			  showCancelButton: true,
			}).then((result)=>{
				if(result.value){					
					ajaxEntity("POST","./postCommentOnApplication",{
						applicationId:applicationid,
						dateStr:moment().format(TimStampFromat),
						content:result.value
					},(responseJson)=>{
						if(responseJson.result != undefined && responseJson.result == "success"){
							addCommentBlock(responseJson.commentId,"Myself",moment().format(TimStampFromat),result.value);
						}
					});
				}else{
					Swal.fire("Comment is empty", "Plz write something here", "info")
				}
			});
		return false;
	});
}