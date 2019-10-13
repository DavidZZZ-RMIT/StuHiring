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
		$("#message").val(responseJson.application.message);
		jobid = responseJson.job.jobId;
		applicationid = responseJson.application.id;
		job = responseJson.job;
		
		if(responseJson.application.status == "Requesting"){
			$("#applyBtn").hide();
			$("#cancelBtn").show();
		}else if(responseJson.application.status == "Cancelled"){
			$("#applyBtn").show();
			$("#cancelBtn").hide();
		}else{
			$("#applyBtn").hide();
			$("#cancelBtn").hide();
		}
	
		ajaxEntity("POST","./getCommentsOfApplication",{applicationId : responseJson.application.id},(cmtsJson)=>{
			jQuery.each(cmtsJson, function(i, comment) {
				addCommentBlock(i,(responseJson.application.applicant == comment.author?"Myself":"Employer"),comment.dateStr,comment.content);
			});
		});
		
	}else{
		Swal.fire("The JobApplication is not exist!!!", "Plz contact s3705795!", "danger")
	}
},()=>{
	Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
});

window.onload =  ()=> {
	$('#updateBtn').on('click', function (event) {
		event.preventDefault();

		var formData = {
			message:$("#message").val(),
			id:applicationid
		}

		
		ajaxEntity("POST","./updateApplicationMessage",formData,(responseJson)=>{
			// console.log(responseJson);
			if(responseJson.result != undefined && responseJson.result == "success"
					){
				  Swal.fire({
					  position: 'top-end',
					  type: 'success',
					  title: 'Your Message has been sent to employer successfully !',
					  showConfirmButton: false,
					  timer: 1500
					})
			}else{
				Swal.fire("Update Message Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
			}
		},()=>{
			Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
		});
		return false;
	});
	
	$('#cancelBtn').on('click', function (event) {
		event.preventDefault();

		var formData = {
			id:applicationid,
			action:"cancel"
		}

		ajaxEntity("POST","./handleApplication",formData,(responseJson)=>{
			// console.log(responseJson);
			if(responseJson.result != undefined && responseJson.result == "success"){
				  Swal.fire({
					  position: 'top-end',
					  type: 'success',
					  title: 'Your Application has been updated successfully !',
					  showConfirmButton: false,
					  timer: 1500
					}).then(()=>{
						$("#title").text(job.title+" (Cancelled)");
						$("#applyBtn").show();
						$("#cancelBtn").hide();
					});
					
			}else{
				Swal.fire("Update Application Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
			}
		},()=>{
			Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
		});
		return false;
	});
	
	$('#applyBtn').on('click', function (event) {
		event.preventDefault();

		var formData = {
			id:applicationid,
			action:"request"
		}

		ajaxEntity("POST","./handleApplication",formData,(responseJson)=>{
			// console.log(responseJson);
			if(responseJson.result != undefined && responseJson.result == "success"
					){
				  Swal.fire({
					  position: 'top-end',
					  type: 'success',
					  title: 'Your Application has been updated successfully !',
					  showConfirmButton: false,
					  timer: 1500
					}).then(()=>{
						$("#title").text(job.title+" (Requesting)");
						$("#applyBtn").hide();
						$("#cancelBtn").show();
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