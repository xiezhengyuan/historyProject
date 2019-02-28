<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- <link href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet"> -->
<link href="http://mat1.gtimg.com/www/cssn/newsapp/share/share20150324b_min.css" type="text/css" rel="stylesheet"> 
<style>
#other_msg{
}
#other_msg ul{
  height:auto;
  overflow:hidden;
  margin:0;
  padding:10px 0 0;
  border-bottom:1px solid #ccc;
}
#other_msg ul:last-child{
  border:none;
}

#other_msg ul li{
  float:left;
  display:block;
  list-style:none;
}
#other_msg .headpic{
  width:15%;
  height:40px;
  max-width:40px;
  clear:left;
}
#other_msg .headpic div{
  width:40px;
  height:40px;
  overflow:hidden;
  border-radius:20px;
}
#other_msg .content{
  width:85%;
  height:auto;
  padding-left:10px;
  box-sizing:border-box;
  line-height:16px;
}
#other_msg .content h5{
  margin:2px 0 3px;
  font-weight:normal;
  font-size:13px;
}
#other_msg .content small{
  font-size:12px;
}
#other_msg .content p{
  font-weight:normal;
  font-size:14px;
  color:#666;
  text-align:justify;
  line-height:1.3;
  margin:5px 0 10px;
}
input[type="text"],
textarea{
  width:100%;
  border-radius:5px;
  border:1px solid #ccc;
  padding:5px;
  margin:5px 0;
  box-sizing:border-box;
  font-size:12px;
  color:#666;
}
input[type="button"]{
  display:block;
  width:100%;
  border-radius:5px;
  height:35px;
  line-height:33px;
  color:#fff;
  background-color:#337AB7;
  border:1px solid #2E6DA4;
}
#icons {
  line-height:14px;
  vertical-align:text-bottom;
}
#icons a span{
  margin-right:2px;
}
#icons a.likeed{
  color:rgb(235,21,73);
}
#icons a.zaned{
  color:#337AB7;
}
#icons a{
  color:#666;
  font-size:14px;
  font-weight:normal;
}
#icons a:hover{
  text-decoration: none;
}


 /* #resourceid ul{
  height:auto;
  overflow:hidden;
  margin:0;
  padding:10px 0 0;
  border-bottom:1px solid #ccc;
  text-align:center;
}
#resourceid ul:last-child{
  border:none;
}

#resourceid ul li{
  float:center;
  display:block;
  list-style:none;
}
#resourceid .headpic{
  width:15%;
  height:40px;
  max-width:40px;
   clear:center; 
}
#resourceid .headpic div{
  width:40px;
  height:40px;
  overflow:hidden;
  border-radius:20px;
  float:center;
  align:center;
  text-align:center;
}  */
</style>

<p class="title" align="left" id="title"></p><span id="model"></span>
<div class="src">
<span>帖子类型:<span id="fpostingstyle"></span></span>
<span>发帖时间:<span id="sendtime"></span></span>
<span>点赞:<span id="praise"></span> </span>
<span>作者:<span id="author" style="margin-left:3px;"></span></span>
<span  style="margin-left:4px;">阅读:<span id="readtimes"></span></span>
<p class="text" ><p align=left  id="postingscontent"></p></p>
<div id="imgarr"></div>
</div>

<div id="mpnewscontent">

</div>
<hr>
<div id="icons" class="text-right" style="text-align:right">
   <button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" onclick="passposting()">
   <span>通过</span>
	</button>
   <button class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" onclick="nopassposting()">
   <span>不通过</span>
	</button>
</div>
<hr>
<div id="other_msg"></div>
<script>

$('#postingdetail1').modal('show');
var postingid = '<%=request.getParameter("postingid")%>';
querypostingphotos();
querypostingcomments();

//根据ID查询商品信息
Q.getObjById("Postings",postingid,function(d){
	var fuserinfoid = d.fuserinfoid;
	var fpostingstyleid = d.fpostingstyleid;
	$('#title').html(d.postingsname);
	$('#postingscontent').text(d.postingscontent);
	$('#sendtime').html(d.createtime);
	$('#readtimes').html(d.views);
	$('#praise').html(d.praise);
	Q.getObjById("PostingStyle",fpostingstyleid,function(da){
		$('#fpostingstyle').html(da.name);
	})
	Q.getObjById("UserInfo",fuserinfoid,function(du){
		$('#author').html(du.username);
	})
})

function querypostingphotos(){
	$._ajax({
		url:Q.url+"/posting/querypostingphotosbyid.action",
		data:{"postingid":postingid},
		success:function(data){
			if(typeof(data)=='object'){
				/* $('#editgoods_form').resetObjectForm(data); */
				var html = '';
				var rows = data.rows;
				for(var x= 0;x<rows.length;x++){
					var o = {};
					o.id = rows[x].postingsPhotos.id;
					var fileurl = rows[x].postingsPhotos.photourl;
					o.url = fileurl ;
					o.name = fileurl.substr( fileurl.lastIndexOf("/")+1); 
					
					html += '<p class="text" ><p align=center ><img src="'+Q.url+o.url+'" width= "20%" style="display:block;"/></p></p>'	
					
				}
				$('#imgarr').html(html);
			}
		}
	})
}

function querypostingcomments(){
	$._ajax({
		url:Q.url+"/posting/querypostingcomments.action",
		data:{"postingid":postingid},
		success:function(data){
			if(typeof(data)=='object'){
				/* $('#editgoods_form').resetObjectForm(data); */
				var comments = '';
				var rows = data.rows;
				for(var x= 0;x<rows.length;x++){
					var postingscomment = data.rows[x];
					
					comments += '<ul>'+
		    	/* 	'<li class="headpic"><div><img src="'+(postingscomment.headphoto==null?'image/user.jpg':postingscomment.headphoto==''?'image/user.jpg':postingscomment.headphoto)+'" width="100%" height="100%"></div></li>'+
		    		'<li class="content">'+ */
		      		'<h5>'+postingscomment.username+'</h5>'+
		      		'<small>'+postingscomment.createtime+'</small>'+
		      		'<p>'+(postingscomment.superaddition==1?("回复@"+postingscomment.replyto+"："+postingscomment.comment):postingscomment.comment)+'</p>'+
		    		'</li>'+
		  			'</ul>';
					
				}
				$('#other_msg').html(comments);
			}
		}
	})
}

function passposting(){
	$._ajax({
		url: Q.url+"/posting/passposting.action",
		data:{"postingid":postingid},
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					//reloadgrid
					$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
				}else if(data.op == 'fail')
					Q.toastr("提示",data.msg,'error');
				else
					Q.toastr("提示","操作失败",'error');
			}else {
				Q.toastr("提示","操作失败",'error');
			}
		}
	}); 
	
}
function nopassposting(){
	$._ajax({
		url: Q.url+"/posting/nopassposting.action",
		data:{"postingid":postingid},
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					//reloadgrid
					$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
				}else if(data.op == 'fail')
					Q.toastr("提示",data.msg,'error');
				else
					Q.toastr("提示","操作失败",'error');
			}else {
				Q.toastr("提示","操作失败",'error');
			}
		}
	}); 
	
}

/* $('#content').smsLoading(); */
/* $.ajax({
	url: Q.url+"/model/getpostingsdetail.action",
	data:{"pid":pid},
	success: function(data){
		if(typeof(data)=='object'){
			if(data.op=='timeout' ){
				location.reload();
				return;
			}
			$('#title').html(data.postingsname);
			$('#sendtime').html(data.time);
			$('#author').html(data.username);
			$('#readtimes').html(data.views);
			$('#model').html(data.firstsection+'>'+data.secondsection);
			var html = ''; */
		/* 	for(var i = 0;i<data.postingsdetail.length;i++){
				var postingsdetail = data.postingsdetail[i];
				if(postingsdetail.type=='IMAGE'){
					html += '<p class="text" ><p align=center ><img src="'+Q.url+postingsdetail.url+'" width= "100%" style="display:block;"/></p></p>'
				}else if(postingsdetail.type=='VIDEO'){
					//html += '<p class="text" ><p align=center ><audio autoplay="autoplay"><source src="'+Q.url+postingsdetail.url+'" /></audio></p></p>'
				
					if(postingsdetail.url.indexOf(".avi")){
						html +='<div align=center ><object type="video/x-msvideo" data="'+Q.url+postingsdetail.url+'"  width="400" height="600">'+
							'<param name="src"value="'+Q.url+postingsdetail.url+'" />'+
						        '<param name="autostart" value="true" />'+
						        '<param name="controller" value="true" />'+
						'</object></div>';
					}else{
						html += '<div align=center ><object id="MediaPlayer" classid="clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95" width="400" height="600" standby="Loading Windows Media Player components…" type="application/x-oleobject" codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,7,1112">'+
					    '<param name="FileName" value="'+Q.url+postingsdetail.url+'">'+
				    '<param name="AutoStart" value="true">'+
				    '<param name="ShowControls" value="true">'+
				    '<param name="BufferingTime" value="2">'+
				    '<param name="ShowStatusBar" value="true">'+
				    '<param name="AutoSize" value="true">'+
				    '<param name="InvokeURLs" value="false">'+
				    '<param name="AnimationatStart" value="1">'+
				    '<param name="TransparentatStart" value="1">'+
				    '<param name="Loop" value="1">'+
				    '<embed type="application/x-mplayer2" src="'+Q.url+postingsdetail.url+'" name="MediaPlayer" autostart="1" showstatusbar="1" showdisplay="1" showcontrols="1" loop="0" videoborder3d="0" pluginspage="http://www.microsoft.com/Windows/MediaPlayer/" width="400" height="600"></embed>'+
				  '</object></div>'
					}
					
				
				}
			} */
		/* 	html += '<p class="text" ><p align=left >'+data.postingscontent+'</p></p>';
			
			$('#mpnewscontent').html(html);
		}
		$('#content').smsLoaded();
	}
}) */

function changepostingsState(state){
	$.ajax({
		url: Q.url+"/model/changepostingsState.action",
		data:{"pid":pid,"state":state},
		success: function(data){
			if( typeof(data) == 'object'  ){
				if(data.op=='timeout' ){
					location.reload();
					return;
				}
				if(data.op=='success' ){
					Q.alert("提示","操作成功");
					
				}else{
					Q.alert("提示","操作失败");
				}
			}else{
				Q.alert("提示","操作失败");
			}
		}
	})
}

/* $('#sure').die('click').live('click',function(){
	$.messager.confirm('提示', '确定此帖子通过审核吗？', function(r){
		if (r){
			changepostingsState(1);
		}
	})
}) */

/* $('#notsure').die('click').live('click',function(){
	$.messager.confirm('提示', '确定此帖子不通过审核吗？', function(r){
		if (r){
			changepostingsState(-1);
		}
	})
})


$('#returnbf').die('click').live('click',function(){
	$.ajax({
		url:Q.url+"/html/model/addbatch.jsp",
		dataType:'text',
		success:function(html){
			$("#content").attr("style","display:block;");
			$("#content").html(html);
		}
	}) 
}) */
</script>