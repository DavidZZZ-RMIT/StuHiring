$("#jobListTitle").hide();
$("#joblist").hide();

function addJobLine(id,title, description){
	var temp = '<a href="jobreview.html?id='+id+'" target="_blank" class="list-group-item list-group-item-action">\
				<div class="d-flex w-100 justify-content-between">\
				<h5 class="mb-1">'+title+' </h5>\
			</div>\
			<p class="mb-1">'+description+'</p> \
			</a>';
	$("#joblist").append(temp);
}

window.onload =  ()=> {
	$('#searchJobBtn').on('click', function (event) {
		event.preventDefault();

		var formData = {
			keywords: $("#keywords").val(),
		}

		if(formData.keywords.length == 0){
			Swal.fire("The Keywords is empty!", "Plz input the Keywords which you want to search", "warning");
			return false;
		}

		
		return false;
	});
	
	$("#moreJobsBtn").on('click', function (event) {
		event.preventDefault();
		
		Swal.fire({
			  title: 'Type a keywords and try to search',
			  input: 'text',
			  inputAttributes: {
			    autocapitalize: 'off'
			  },
			  showCancelButton: true,
			  confirmButtonText: 'Search jobs',
		}).then((result) => {
			  if (result.value) {
				  ajaxEntity("POST","./findJobs",{keywords:result.value},(responseJson)=>{
						// console.log(responseJson);
						if(responseJson.result != undefined && responseJson.result == "success"
								){
							if(Object.keys(responseJson.jobs).length > 0){
								$("#jobListTitle").show();
								$("#joblist").empty();
								$("#joblist").show();
								$("#jobListTitle").text("Job:"+result.value);
								jQuery.each(responseJson.jobs, function(i, job) {
									addJobLine(i,job["title"],job["description"]);
									});
							}else{
								$("#jobListTitle").hide();
								$("#joblist").hide();
							}
						}else{
							Swal.fire("Find Job Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
						}
					},()=>{
						Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
					});
			  }
			})
		
		return false;
		});
}