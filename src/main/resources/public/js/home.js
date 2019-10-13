var pageAgent =
{
	addPost:(id,poster,gender,dateStr,liked,content,perantId)=>{
		tmp = '<div class="card my-1 mx-1 '+(perantId!=undefined?"card-inner":"")+' " id="'+id+'">\
			<div class="card-body">\
				<div class="row">\
					<div class="col-md-2">\
						<img src="./public/img/'+gender+'.png" class="img img-rounded img-fluid">\
						<p class="text-secondary text-center postDate" dateStr="'+dateStr+'">'+moment(dateStr,TimStampFromat).fromNow()+'</p>\
					</div>\
					<div class="col-md-10">\
						<p>\
							<a class="float-left"><strong>'+poster+'</strong></a>\
						</p>\
						<div class="clearfix"></div>\
						<p>'+content+'</p>\
						<p>\
							<a class="float-right btn btn-outline-primary ml-2 replyBtn" postid = "'+id+'"> <i class="fa fa-reply"></i> Reply\
							</a> <a class="float-right btn likeBtn text-white btn-'+(liked?"danger":"info")+' " postid = "'+id+'" > <i class="fa '+(liked? "fa-heart":"fa-heart-o")+'"></i> '+(liked?"Unlike":"like")+'\
							</a>\
						</p>\
					</div>\
				</div>\
			</div>\
		</div>';
		
		if(perantId != undefined && perantId != "NULL" && $("#"+perantId) != undefined){
			$("#"+perantId+" > .card-body").append(tmp).after(()=>{
				initLikeBtns($("#"+id).find(".likeBtn"));
				initReplyBtns($("#"+id).find(".replyBtn"));
			});
		}else{
			$("#postContainer").prepend(tmp).after(()=>{
				initLikeBtns($("#"+id).find(".likeBtn"));
				initReplyBtns($("#"+id).find(".replyBtn"));
			});
		}
	},
	addSearchedFriend:(screenName,fullName,email)=>{
		tmp = '<li class=" border-0 list-group-item d-flex justify-content-between align-items-center addFriendBtn" email="'+email+'" friendName="'+screenName+'('+fullName+')">'+screenName+'('+fullName+')<i class="fa fa-plus-circle text-success fa-lg "></i></li>';
		$("#searchlist").prepend(tmp).after(()=>{
			initAddFriendBtn($("#searchlist > .addFriendBtn").first());
		});
	},
	clearSearchList:()=>{
		$("#searchlist").empty();
	},
	addSearchedFrindsResult:(rst)=>{
		$("#searchlist").empty();
		for(var i =0;i<rst.EMAIL.length;i++){
			pageAgent.addSearchedFriend(rst.SCREENNAME[i],rst.FULLNAME[i],rst.EMAIL[i]);
		}
	},
	addMsg:(email,name)=>{
		tmp = '<li id="msg-'+md5(email)+'" class="list-group-item d-flex justify-content-between align-items-center">'
				+name+'<span class="badge badge-info"><i class="fa fa-envelope requestBtn"  reqemail="'+email+'"  friendName="'+name+'"></i></span> </li>';
		
		$("#msgList").prepend(tmp).after(()=>{
			initRequestBtn($("#msgList > > > .requestBtn").first());
		});
	}
	
}

function initRequestBtn(obj){
	$(obj).on('click', function () {
		var reqemail = $(this).attr("reqemail");
		var friendName = $(this).attr("friendName");
		
		Swal.fire({
			  title: 'Accept '+friendName+' Request ?',
			  text: "Do you agree to add "+ friendName + " as friend ?",
			  type: 'info',
			  showCancelButton: true,
			  confirmButtonText: 'Yes, Accept!'
			}).then((result) => {
			  if (result.value) {
				  acceptAddFrind(reqemail,(rst)=>{
					  if(rst){
						  $("#msg-"+md5(reqemail)).remove();
						  Swal.fire({
							  position: 'top-end',
							  type: 'success',
							  title: 'You accepted ' +friendName + " as friend.",
							  showConfirmButton: false,
							  timer: 3500
							})
					  }else{
							Swal.fire("Accept Friend Failed!", "plz contact s3705795!", "danger");
					  }
				  });
			  }else{
				  Swal.fire({
					  title: 'Reject '+friendName+' Request ?',
					  text: "Are you sure to reject "+ friendName + " ?",
					  type: 'warning',
					  showCancelButton: true,
					  confirmButtonText: 'Yes, Reject!'
					}).then((result) => {
					  if (result.value) {
						  rejectAddFrind(reqemail,(rst)=>{
							  if(rst){
								  $("#msg-"+md5(reqemail)).remove();
								  Swal.fire({
									  position: 'top-end',
									  type: 'success',
									  title: 'You rejected ' +friendName + " as friend.",
									  showConfirmButton: false,
									  timer: 3500
									})
							  }else{
									Swal.fire("Reject Friend Failed!", "plz contact s3705795!", "danger");
							  }
						  });
					  }
					})
			  }
			})
	});
}

function initAddFriendBtn(obj){
	$(obj).on('click', function (event) {
		var email = $(this).attr("email");
		var friendName = $(this).attr("friendName");

		Swal.fire({
			  title: 'Send Friendship Request to '+friendName+" ?",
			  text: "Do you want to build friendship with "+friendName,
			  type: 'question',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes, Send Request!'
			}).then((result) => {
			  if (result.value) {
				  requestAddFrind(email);
			  }
			})
		
	});
}

function like(postid,islike,callback,failcallback){
	ajaxEntity("POST",{action:"like",postid:postid,islike:(islike?"like":"unlike")},(rst)=>{
		if(callback)callback(rst.result);
	},()=>{
		if(failcallback)failcallback();
	});
}

function initLikeBtns(obj){
	$(obj).on('click', function (event) {
		var s = $(this);

		var setLike =(obj)=>{
			$(obj).addClass("btn-danger");
			$(obj).removeClass("btn-info");
			$(obj).html("<i class='fa fa-heart '></i> unlike");
		}
		
		var setUnLike =(obj)=>{
			$(this).addClass("btn-info");
			$(this).removeClass("btn-danger");
			$(this).html("<i class='fa fa-heart-o '></i> like ");
		}
		
		var setPending =()=>{
			$(this).addClass("btn-info");
			$(this).removeClass("btn-danger");
			$(this).html("<i class='fas fa-spinner fa-pulse'></i>");
		}
		
		if($(this).hasClass('btn-info')){ // unlike -> like, like it
			setPending(s);
			
			like($(this).attr("postid"),true,(isSuccess)=>{
				if(isSuccess)
					setLike(s);
				else
					setUnLike(s);
			},()=>{
				setUnLike(s);
			});
		}else{  // like -> unlike, unlike it
			setPending(s);
			
			like($(this).attr("postid"),false,(isSuccess)=>{
				if(isSuccess)
					setUnLike(s);
				else
					setLike(s);
			},()=>{
				setLike(s);
			});
		}
	});
}

function post(content,parentpostid,callback){
	ajaxEntity("POST",{action:"post",
		"content":content,
		"parentpostid":parentpostid,
		"posttimestamp":moment().format(TimStampFromat)
	},(responseJson)=>{
		if(responseJson.result != undefined && 
				responseJson.data != undefined && 
				responseJson.result == "success" &&
				responseJson.data
				){
			if(callback)callback(responseJson);
		}else{
			Swal.fire("Post Failed!", "plz contact s3705795!", "danger");
		}
	});
}

function initReplyBtns(obj){
	$(obj).on('click', async function () {
		var parentPostId = $(this).attr("postid");
		
		const { value: text } = await Swal.fire({
			  input: 'textarea',
			  inputPlaceholder: 'Type your mind here...',
			  inputAttributes: {
			    'aria-label': 'Reply here'
			  },
			  showCancelButton: true
			})

			if (text.length > 0) {
			  //Swal.fire(text)
				post(text,parentPostId,(responseJson)=>{
					pageAgent.addPost(responseJson.postid,"Myself",facebooklite.gender,moment().format(TimStampFromat),false,text,parentPostId);
				});
			}
	});
}

function getPosts(){
	ajaxEntity("POST",{action:"getposts",
	},(responseJson)=>{
		if(responseJson.result != undefined && 
				responseJson.data != undefined && 
				responseJson.result == "success" &&
				responseJson.data
				){
			var posts = responseJson.data;
			for(var i =0;i<posts.POSTID.length;i++){
				pageAgent.addPost(posts.POSTID[i],(posts.MEMBER_EMAIL[i] == facebooklite.email ?"Myself":posts.SCREENNAME[i]),posts.GENDER[i],posts.POSTTIMESTAMP[i],(posts.ISLIKE[i]?true:false),posts.BODY[i],posts.PARENTPOSTID[i]);
			}
		}else{
			Swal.fire("Fetch Posts Failed!", "plz contact s3705795!", "danger");
		}
	});
}

function getMsgs(){
	ajaxEntity("POST",{action:"getRequests",
	},(responseJson)=>{
		if(responseJson.result != undefined && 
				responseJson.data != undefined && 
				responseJson.result == "success" &&
				responseJson.data
				){
			var msgs = responseJson.data;
			for(var i=0;i<msgs.REQUESTER_EMAIL.length;i++){
				pageAgent.addMsg(msgs.REQUESTER_EMAIL[i],msgs.SCREENNAME[i]+"("+msgs.FULLNAME[i]+")");
			}
			
		}else{
			Swal.fire("Fetch Messages Failed!", "plz contact s3705795!", "danger");
		}
	});
}

window.onload =  ()=> {
	checkUnAlive();
	initLogoutBtn();
	getPosts();
	getMsgs();
	
	$('#searchBtn').on('click', function (event) {
		event.preventDefault();
		searchFrinds($("#searchInput").val(),(rst)=>{
			pageAgent.addSearchedFrindsResult(rst);
		});
		return false;
	});
	
	$('#postBtn').on('click', function (event) {
		event.preventDefault();
		
		if($("#postInput").val().length > 0)
			post($("#postInput").val(),"NULL",(responseJson)=>{
				if(responseJson.postid!=undefined){
					pageAgent.addPost(responseJson.postid,"Myself",facebooklite.gender,moment().format(TimStampFromat),false,$("#postInput").val(),undefined);
					$("#postInput").val("");
				}
			});
	
		
		return false;
	});
		
	$('#deleteAccountBtn').on('click', function (event) {
		event.preventDefault();
		
		Swal.fire({
			  title: 'Are you sure to Delete your account?',
			  text: "Do you want to delele your account on Facebooklite ?",
			  type: 'warning',
			  showCancelButton: true,
			  cancelButtonColor: '#3085d6',
			  confirmButtonColor: '#d33',
			  confirmButtonText: 'Yes, Delete!'
			}).then((result) => {
			  if (result.value) {
				  Swal.fire({
					  title: 'Really Delete Your Account?',
					  text: "This operation can be revert!!!",
					  type: '',
					  showCancelButton: true,
					  cancelButtonColor: '#3085d6',
					  confirmButtonColor: '#d33',
					  confirmButtonText: 'Delete plz, I am sure!'
					}).then((result) => {
					  if (result.value) {
						  deleteAccount((rst)=>{
							  if(rst == 1){
								  Swal.fire(
									      'Deleted!',
									      'See you, Your account has been deleted.',
									      'success'
									    ).then(()=>{
											window.location = "index.html";
									    });
							  }else{
									Swal.fire("Delete Account Failed!", "plz contact s3705795!", "danger");
							  }
						  });
					    
					  }
					})
			  }
			})
		return false;
	});
}