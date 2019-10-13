var user ={
	email:undefined,
	type:undefined
};

const  TimStampFromat = "YYYY-MM-DD HH:mm:ss";

function addCommentBlock(id,poster,dateStr,content){
	tmp = '<div class="card my-1 mx-1 " id="'+id+'">\
		<div class="card-body">\
			<div class="row">\
				<div class="col-md-2">\
					<img src="./user.png" class="img img-rounded img-fluid">\
					<p class="text-secondary text-center postDate" dateStr="'+dateStr+'">'+moment(dateStr,TimStampFromat).fromNow()+'</p>\
				</div>\
				<div class="col-md-10">\
					<p>\
						<a class="float-left"><strong>'+poster+'</strong></a>\
					</p>\
					<div class="clearfix"></div>\
					<p>'+content+'</p>\
				</div>\
			</div>\
		</div>\
	</div>';
	$("#commentContainer").append(tmp);
	
}

var ajaxEntity = (type,path,json,callback,fialedCallback)=>{
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function () {
		if (this.readyState == 4 && this.status == 200) {
			if(callback)callback(JSON.parse(this.responseText));
		}else if(this.status == 403 || this.status == 404){
			if(fialedCallback)fialedCallback(this);
		}
	};
	xmlhttp.open(type, path , true);
	xmlhttp.setRequestHeader("Content-type", "application/json");
	xmlhttp.send(JSON.stringify(json));
}


$('#logoutBtn').on('click', function (event) {
	event.preventDefault();
	
	ajaxEntity("POST","./logout",{},(responseJson)=>{
		// console.log(responseJson);
		if(responseJson.result != undefined && 
				responseJson.result == "success"
				){
			window.location = "index.html";
		}
	},()=>{
		Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
	});
	return false;
});

if(!window.location.href.endsWith("index.html")){
	ajaxEntity("POST","./isLive",{},(responseJson)=>{
		// console.log(responseJson);
		if(responseJson.result != undefined && 
				responseJson.result == "success"
				){
		}else{
			window.location = "index.html";
		}
	},()=>{
		Swal.fire("Access Error!!!", "Plz contact s3705795!", "danger")
	});	
}