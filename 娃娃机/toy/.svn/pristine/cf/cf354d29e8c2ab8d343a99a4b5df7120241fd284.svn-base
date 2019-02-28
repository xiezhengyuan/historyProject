<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
    .myclass{
       margin-top: 10px;
    
    }
    #button{
     
      background-color:#ccc;
      margin-left:5px;
      border-radius: 8px;
      padding:8px 20px;
      cursor:pointer;
      pointer-events:none;	
    }
</style>

<div class="modal  inmodal fade" id="edituser4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					申诉信息
		        <small>
			    <i class="ace-icon fa fa-angle-double-right"></i>
			        查看
		        </small>
				</h4>
			</div>
			<div class="modal-body"  style="overflow-y: auto;">
			 <form class="form-horizontal" role="form" id="form_edituser">
            <input type="hidden" class="form-control" id="userinfoid" name="id" placeholder="">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">申诉用户：</label>
					<div class="col-sm-10  myclass">
					<input type="text" class="form-control" name="login"  id="nickname"  readonly="readonly">
					</div>
					</div>
			
					<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">联系方式：</label>
					<div class="col-sm-10 myclass">
					<input type="text" class="form-control" name="mobile"  id="mobile"  readonly="readonly">
					</div>
					</div>
			
			 		<div class="form-group">
				    <label for="paparno" class="col-sm-2 control-label">申诉原因：</label>
				    <div class="col-sm-10 myclass">
				      <input type="text" class="form-control" name="substance" id="substance"  readonly="readonly">
				    </div>
				    </div>
			
			         <div class="form-group">
				    <label for="paparno" class="col-sm-2 control-label">使用机器编号：</label>
				    <div class="col-sm-10 myclass">
				      <input type="text" class="form-control" name="machieno" id="machieno"  readonly="readonly">
				    </div>
				    </div>
			
					<div class="form-group">
					<label for="loginpwd" class="col-sm-2 control-label">抓取视频：</label>
					<div id="vid" class="col-sm-10 myclass" style="width: 472px;height:240px;margin-left: 10px;">
					   
					</div>
					</div>
			
					<div class="form-group">
					<label for="paypwd" class="col-sm-2 control-label">回复该用户：</label>
					<div class="col-sm-10 myclass">
				    <div style="float: left;">
				      <textarea id="sendcontent" style="width:390px ;height: 35px ;resize: none;"></textarea>
				    </div>
					<div style="float: left; margin-top:7px;">
					   <span id="button" onclick="ab()">回复</span>
					</div>
					
					</div>
					</div>
					
			</form>
			</div>
		<div class="modal-footer">
				<button title="删除申诉" class="btn btn-danger btn-xs" style="color:#f60;margin-left:3px;width: 60PX; height:35px; " onclick="del_tableid()">删除</button>
				<button id="hehehe" title="完成处理" class="btn btn-success btn-xs" style="color:#f60;margin-left:3px; width: 60PX; height:35px; " onclick="pass_tableid()">处理完成</button> 
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>
$('#edituser4').modal('show');

var appealid = '<%=request.getParameter("appealid")%>';
var type = '<%=request.getParameter("type")%>';
$._ajax({
	url: Q.url+"/UserAppeal/appealtetail.action",
	data:{"appealid":appealid},
	dataType: "json",
	success: function(json){	
		$("#nickname").val(json.nickname);
		$("#mobile").val(json.mobile);
		$("#substance").val(json.substance);
		$("#machieno").val(json.machieno);
		var html='';
		if(json.videourl.indexOf(".avi")){
			html +='<div align=center ><object type="video/x-msvideo" data="'+Q.url+json.videourl+'"  width="472" height="240">'+
				'<param name="src"value="'+Q.url+json.videourl+'" />'+
			        '<param name="autostart" value="false" />'+
			        '<param name="controller" value="true" />'+
			'</object></div>';
		}else{
			html += '<div align=center ><object id="MediaPlayer" classid="clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95" width="472" height="240" standby="Loading Windows Media Player components…" type="application/x-oleobject" codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,7,1112">'+
		    '<param name="FileName" value="'+Q.url+json.videourl+'">'+
	    '<param name="AutoStart" value="false">'+
	    '<param name="ShowControls" value="true">'+
	    '<param name="BufferingTime" value="2">'+
	    '<param name="ShowStatusBar" value="true">'+
	    '<param name="AutoSize" value="false">'+
	    '<param name="InvokeURLs" value="false">'+
	    '<param name="AnimationatStart" value="1">'+
	    '<param name="TransparentatStart" value="1">'+
	    '<param name="Loop" value="1">'+
	    '<embed type="application/x-mplayer2" src="'+Q.url+json.videourl+'" name="MediaPlayer" autostart="1" showstatusbar="1" showdisplay="1" showcontrols="1" loop="0" videoborder3d="0" pluginspage="http://www.microsoft.com/Windows/MediaPlayer/" width="472" height="240"></embed>'+
	    '</object></div>';
		}
		$('#vid').html(html);
	}
});	

if(type=='0'){
	$("#hehehe").html("未处理"); 
}else{
	$("#hehehe").html("完成处理");
}

$('#sendcontent').bind('input',function(){  
	var content=$.trim($('#sendcontent').val());
	if(content==''){
		$("#button").css("pointer-events", 'none');
		$("#button").css("background-color","gray");
		
	}else{
		$("#button").css("pointer-events", 'auto');
		$("#button").css("background-color","green");
	}
}); 








//驳回
function del_tableid(){
	
	Q.confirm("确定删除么？",function(r){
		if(r){
		$._ajax({
			url: Q.url+"/UserAppeal/deleteuserappeal.action",
			data:{"id":appealid,"type":"-1"},
			success: function(data){
				if( typeof(data) == 'object'){
					if( data.op == 'success' ){
						Q.toastr("提示","操作成功",'success');
						$(".close").click();
						$("#newappealdetail").empty();
						$("#oldappealdetail").empty();
						postData = getcondition();
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
	})
}

//同意
function pass_tableid(){
	$._ajax({
		url: Q.url+"/UserAppeal/deleteuserappeal.action",
		data:{"id":appealid,"type":type},
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					postData = getcondition();
					$("#newappealdetail").empty();
					$("#oldappealdetail").empty();
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


//回复
function ab(){
	$._ajax({
		url: Q.url+"/UserAppeal/sendcontent.action",
		data:{"id":appealid,"content":$.trim($('#sendcontent').val())},
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					$('#sendcontent').val("");
					$("#button").css("pointer-events", 'none');
					$("#button").css("background-color","gray");
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

</script>