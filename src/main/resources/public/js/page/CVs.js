function addCVLine(id,title, content){
	var temp = '<a href="cv.html?action=update&id='+id+'" class="list-group-item list-group-item-action">\
				<div class="d-flex w-100 justify-content-between">\
				<h5 class="mb-1">'+title+' </h5>\
			</div>\
			<p class="mb-1">'+content+'</p> \
			</a>';
	$("#cvlist").append(temp);
}

ajaxEntity("POST","./getMyCVs",{},(responseJson)=>{
	// console.log(responseJson);
	if(responseJson != undefined){
		jQuery.each(responseJson, function(i, cv) {
			addCVLine(cv["id"],cv["title"],cv["content"]);
			});
	}else{
		Swal.fire("Fetch CV Failed!", responseJson.msg+"\nUnexpected issue, Plz contact s3705795!", "danger")
	}
},()=>{
	Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
});	