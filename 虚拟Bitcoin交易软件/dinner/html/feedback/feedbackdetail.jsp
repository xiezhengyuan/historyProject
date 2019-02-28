<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="addreply" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					反馈详情
				</h4>
			</div>
			<div class="modal-body" >
			<div class="form-horizontal" role="form" id="form_addcompany">
			
				<div class="form-group">
				
					<div class="col-sm-10">
					<!-- <input type="text" class="form-control" name="company" msg="请输入公司名称" id="company"  placeholder=""> -->
					<p id ="nickname"></p>
					</div>
					</div>
			
				  
					
					<!-- <hr style="height:1px;border:none;border-top:1px solid red;" /> -->
					 
				  <!-- <div class="form-group">
					<label for="title" class="col-sm-2 control-label"></label>
					<div class="col-sm-10">
					<span>标题：<span id="title"></span></span>
					</div>
					</div> -->
					
					 <div class="form-group">
					<!-- <label for="contents" class="col-sm-2 control-label"></label> -->
					<div class="col-sm-10">
					<!-- <textarea rows="" cols="" id= "contents" style="width: 100%"></textarea> -->
					<span id= "contents"></span>
					</div>
					</div>
					
					<div><span id="createtime" style="float:right;"></span></div>
					
					<div class="form-group">
					<!-- <label for="replyinfo" class="col-sm-2 control-label"></label> -->
					<div class="col-sm-10">
					<textarea rows="" cols="" id= "replyinfo" style="width: 120%"></textarea>
					</div>
					</div>
					
					
			</div>
			</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary btn-sm" id="savereply">回复</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>
$('#addreply').modal('show');
var feedbackid = '<%=request.getParameter("feedbackid")%>';
Q.getObjById("FeedBack",feedbackid,function(d){
	var fuserinfoid = d.fuserinfoid;
	$("#title").html(d.title);
	$("#contents").html(d.content);
	$("#createtime").html(d.createtime);
	$("#replyinfo").html(d.replayto);
	Q.getObjById("UserInfo",fuserinfoid,function(du){
		$("#nickname").html(du.nickname);
	})
})


$('#savereply').click(function(){
	var o = {};
	o.feedbackid = feedbackid;
	o.replyinfo=$("#replyinfo").val();

	$._ajax({
		url: Q.url+"/feedback/replyfeedback.action",
		data:o,
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
	
	
})
</script>