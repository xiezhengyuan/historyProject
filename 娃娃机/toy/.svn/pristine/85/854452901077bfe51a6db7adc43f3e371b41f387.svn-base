<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal  inmodal fade" id="edituser4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" on aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					设置后台管理账号
		        <small>
			    <i class="ace-icon fa fa-angle-double-right"></i>
			                设置
		        </small>
				</h4>
			</div>
			<div class="modal-body"  style="overflow-y: auto;">
			 <form class="form-horizontal" role="form" id="form_edituser">
            <input type="hidden" class="form-control" id="userinfoid" name="id" placeholder="">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">登陆账号</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="login"  id="login"  >
					</div>
					</div>
			
					<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">初始密码</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="password"  id="password"  >
					</div>
					</div>
			
			        
					
			</form>
			</div>
		<div class="modal-footer">
				
				<button title="确认添加" class="btn btn-success btn-xs" style="color:#f60;margin-left:3px; width: 60PX; height:35 " onclick="pass_tableid()">确认添加</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>
$('#edituser4').modal('show');

var id = '<%=request.getParameter("id")%>';


//同意
function pass_tableid(){
	
	var username=$.trim($("#login").val());
	var password=$.trim($("#password").val());
	if(username==''||password==''){
		Q.toastr("提示","登陆账号和密码不能为空",'error');
		return;
	}
	
	$._ajax({
		url: Q.url+"/agentinfo/agreeapply.action",
		data:{"id":id,"username":username,"password":password},
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					postData = getcondition();
					$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
					$('.close').click();
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