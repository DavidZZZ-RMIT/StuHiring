ajaxEntity("POST","./getProfile",{},(responseJson)=>{
	// console.log(responseJson);
	if(responseJson.result != undefined && 
			responseJson.result == "success"
			){
		$("#description").val(responseJson.description);
		$("#employmentrecords").val(responseJson.employmentrecords);
		$("#references").val(responseJson.references);
		$("#qualifications").val(responseJson.qualifications);
		$("#licenses").val(responseJson.licenses);
		
	}else{
		Swal.fire("The Profile is not exist!!!", "Plz contact s3705795!", "danger")
	}
},()=>{
	Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
});	


window.onload = ()=> {
$('#updateBtn').on('click', function (event) {
	event.preventDefault();

	var formData = {
		description: $("#description").val(),
		employmentrecords: $("#employmentrecords").val(),
		references: $("#references").val(),
		qualifications: $("#qualifications").val(),
		licenses: $("#licenses").val(),
	}
	
	ajaxEntity("POST","./updateProfile",formData,(responseJson)=>{
		// console.log(responseJson);
		if(responseJson.result != undefined && responseJson.result == "success"
				){
			Swal.fire({
				  position: 'top-end',
				  type: 'success',
				  title: 'Your Profile has been updated!',
				  showConfirmButton: false,
				  timer: 1500
				})
		}else{
			Swal.fire("Update CV Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
		}
	},()=>{
		Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
	});
	return false;
});
}