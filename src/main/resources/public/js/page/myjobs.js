function addJobLine(id,title, description){
	var temp = '<a href="job.html?id='+id+'" class="list-group-item list-group-item-action">\
				<div class="d-flex w-100 justify-content-between">\
				<h5 class="mb-1">'+title+' </h5>\
			</div>\
			<p class="mb-1">'+description+'</p> \
			</a>';
	$("#joblist").append(temp);
}

window.onload =  ()=> {
	ajaxEntity("POST","./getMyJobs",{},(responseJson)=>{
		// console.log(responseJson);
		if(responseJson != undefined){
			jQuery.each(responseJson, function(i, job) {
				addJobLine(job["jobId"],job["title"],job["description"]);
				});
		}else{
			Swal.fire("Post Job Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
		}
	},()=>{
		Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
	});	
}