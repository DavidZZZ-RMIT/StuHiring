function addApplicationLine(id,applicantName,jobtitle, jobdescription,status){
	var temp = '<a target="_blank" href="applicationToMe.html?id='+id+'" class="list-group-item list-group-item-action">\
				<div class="d-flex w-100 justify-content-between">\
				<h5 class="mb-1"><p>'+applicantName+'</p>'+jobtitle+' </h5>\
				<small>'+status+'</small>\
			</div>\
			<p class="mb-1">'+jobdescription+'</p> \
			</a>';
	$("#applicationslist").append(temp);
}

ajaxEntity("POST","./getReceivedApplications",{},(responseJson)=>{
	// console.log(responseJson);
	if(responseJson != undefined){
		jQuery.each(responseJson, function(i, application) {
			addApplicationLine(i,application.applicant.userName ,application.job["title"],application.job["description"],application["status"]);
			});
	}else{
		Swal.fire("Fetch Application Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
	}
},()=>{
	Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
});	