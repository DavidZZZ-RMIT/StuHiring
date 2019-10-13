var url = new URL(window.location.href);
var cvid = undefined;

var action = url.searchParams.get("action")
if(action == "add"){
	$("#addBtn").show();
	$("#updateBtn").hide();
	$("#removeBtn").hide();
	
}else if(action == "update"){
	$("#addBtn").hide();
	$("#updateBtn").show();
	$("#removeBtn").show();
	
	ajaxEntity("POST","./getCV",{cvId:url.searchParams.get("id")},(responseJson)=>{
		// console.log(responseJson);
		if(responseJson.result != undefined && 
				responseJson.result == "success"
				){
			$("#title").val(responseJson.cv.title);
			$("#content").val(responseJson.cv.content);
			cvid = responseJson.cv.id;
			if(!responseJson.editable){
				$("#title").prop("readonly", true);
				$("#description").prop("readonly", true);
				$('#updateBtn').hide();
			}
		}else{
			Swal.fire("The CV is not exist!!!", "Plz contact s3705795!", "danger")
		}
	},()=>{
		Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
	});	
}


window.onload = ()=> {
	$('#updateBtn').on('click', function (event) {
		event.preventDefault();

		var formData = {
			content: $("#content").val(),
			title:$("#title").val(),
			cvId:cvid
		}

		if(formData.title.length == 0){
			Swal.fire("The Title is empty!", "Plz input the title of this CV", "warning");
			return false;
		}

		if(formData.content.length == 0){
			Swal.fire("The Content is empty!", "Plz input the content of this CV", "warning");
			return false;
		}
		
		ajaxEntity("POST","./updateCV",formData,(responseJson)=>{
			// console.log(responseJson);
			if(responseJson.result != undefined && responseJson.result == "success"
					){
				window.location = "cv.html?action=update&id="+cvid;
			}else{
				Swal.fire("Update CV Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
			}
		},()=>{
			Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
		});
		return false;
	});
	
	$('#addBtn').on('click', function (event) {
		event.preventDefault();

		var formData = {
			content: $("#content").val(),
			title:$("#title").val(),
		}

		if(formData.title.length == 0){
			Swal.fire("The Title is empty!", "Plz input the title of this CV", "warning");
			return false;
		}

		if(formData.content.length == 0){
			Swal.fire("The Content is empty!", "Plz input the content of this CV", "warning");
			return false;
		}
		
		ajaxEntity("POST","./addCV",formData,(responseJson)=>{
			// console.log(responseJson);
			if(responseJson.result != undefined && responseJson.result == "success"
					){
				window.location = "CVs.html";
			}else{
				Swal.fire("Update CV Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
			}
		},()=>{
			Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
		});
		return false;
	});
	
	$('#removeBtn').on('click', function (event) {
		event.preventDefault();

		Swal.fire({
			  title: 'Are you sure?',
			  text: "You won't be able to revert this!",
			  type: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes, Remove this CV!'
			}).then((result) => {
			  if (result.value) {
				  ajaxEntity("POST","./removeCV",{cvId:cvid},(responseJson)=>{
						// console.log(responseJson);
						if(responseJson.result != undefined && responseJson.result == "success"
								){
							Swal.fire(
								      'Remove!',
								      'Your CV has been deleted.',
								      'success'
								    )
							setTimeout(()=>{
								window.location = "CVs.html";
							},2000);
						}else{
							Swal.fire("Remove CV Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
						}
					},()=>{
						Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
					});
				  
			    
			  }
			})
		
		return false;
	});
}