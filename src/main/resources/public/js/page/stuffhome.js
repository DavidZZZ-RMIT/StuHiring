function addStuLine(email,school, major,blocked){
	var temp = '<div class="list-group-item list-group-item-action">\
					<div class="d-flex w-100 justify-content-between">\
						<h5 class="mb-1">'+email+'</h5>\
						<small>Student</small>\
					</div>\
					<p class="mb-1">\
						<label>School:</label><span class="descriptionspan">'+school+'</span> <label>Major:</label><span class="descriptionspan">'+major+'</span>\
						<button type="button" class="btn btn-'+(blocked?"success":"dark")+' float-right blockBtn" user="'+email+'" usertpye="Student">'+(blocked?"UnBlock":"Block")+'</button></p>\
				</div>';
	$("#userlist").append(temp);
}

function addEmployerLine(email,company,blocked){
	var temp = '<div class="list-group-item list-group-item-action">\
					<div class="d-flex w-100 justify-content-between">\
						<h5 class="mb-1">'+email+'</h5>\
						<small>Employer</small>\
					</div>\
					<p class="mb-1">\
						<label>Company:</label><span class="descriptionspan">'+company+'</span> \
						<button type="button" class="btn btn-'+(blocked?"success":"dark")+' float-right blockBtn" user="'+email+'" usertpye="Employer">'+(blocked?"UnBlock":"Block")+'</button></p>\
				</div>';
	$("#userlist").append(temp);
}

ajaxEntity("POST","./getAllUsers",{},(responseJson)=>{
	// console.log(responseJson);
	if(responseJson != undefined){
		jQuery.each(responseJson.students, function(i, student) {
			addStuLine(student["email"],student["school"],student["major"],student["isBlackListed"]);
			});
		jQuery.each(responseJson.employers, function(i, employer) {
			addEmployerLine(employer["email"],employer["companyName"],employer["isBlackListed"]);
			});
		
		$(".blockBtn").on('click', function (event) {
			event.preventDefault();
			var from ={
				email:$(this).attr("user"),
				usertpye:$(this).attr("usertpye"),
				action:""
			};
			
			if($(this).text() == "Block")from.action = "block";
			else if($(this).text() == "UnBlock")from.action = "unblock";
			
			ajaxEntity("POST","./addUserToBlackList",from,(responseJson)=>{
				if(responseJson.result != undefined && responseJson.result == "success"){
					if($(this).text() == "Block"){
						$(this).text("UnBlock");
						$(this).removeClass("btn-dark");
						$(this).addClass("btn-success");
					}
					else if($(this).text() == "UnBlock"){
						$(this).text("Block");
						$(this).addClass("btn-dark");
						$(this).removeClass("btn-success");
					}
				}else{
					Swal.fire("Update User Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
				}
			});	
			
			return false;
		});
	}else{
		Swal.fire("Fetch CV Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
	}
},()=>{
	Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
});	