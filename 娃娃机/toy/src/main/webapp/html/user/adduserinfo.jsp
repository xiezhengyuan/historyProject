<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="adduser" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					新增用户
				</h4>
			</div>
			<div class="modal-body" >
			<div class="form-horizontal" role="form" id="form_adduser">
				<div class="form-group">
					<label for="username" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="userInfo.username" msg="请输入姓名" id="username"  placeholder="">
					</div>
					</div>
					
					 <div class="form-group">
				    <label for="sex" class="col-sm-2 control-label">性别</label>
				    <div class="col-sm-10">
				      <select  class="form-control" id="sex" name="userInfo.sex">
				      <option value="男" selected>男</option>
				      <option value="女" >女</option>
				      </select>
				    </div>
				  </div>
					
					<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">手机号</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="userInfo.mobile" msg="请输入手机号" id="mobile" placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="password" class="col-sm-2 control-label">密码</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="userInfo.password" msg="请输入密码" id="password" placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="balance" class="col-sm-2 control-label">喵币</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="userInfo.balance" msg="请输入喵币" id="balance" placeholder="">
					</div>
					</div>
					
				  <div class="form-group">
					<label for="freevoucher" class="col-sm-2 control-label">免玩劵</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="userInfo.freevoucher" msg="请输入免玩劵数量" id="freevoucher"  placeholder="">
					</div>
					</div>
					
				
			</div>
			</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn-sm" id="saveuser">提交</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>
$('#adduser').modal('show');

$('#saveuser').click(function(){
	if(!$('#form_adduser').checkForm())return;
	
	var o = $('#form_adduser').serializeObject();
	
	$._ajax({
		url: Q.url+"/userinfo/adduserinfo.action",
		data:o,
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					$(".close").click();
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