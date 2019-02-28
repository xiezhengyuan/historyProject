<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="addemp" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					新增员工
				</h4>
			</div>
			<div class="modal-body" >
			<div class="form-horizontal" role="form" id="form_addemp">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="userInfo.name" msg="请输入姓名" id="name"  placeholder="">
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
					<label for="username" class="col-sm-2 control-label">用户名</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="userInfo.username" msg="请输入用户名" id="username"  placeholder="">
					</div>
					</div>
				  
				  <div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">手机号</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="userInfo.mobile" msg="请输入手机号" id="mobile" placeholder="">
					</div>
					</div>
					
					<div class="form-group">
				    <label for="sex" class="col-sm-2 control-label">部门</label>
				    <div class="col-sm-10">
				      <select  class="form-control" id="fdepartmentid" name="userInfo.fdepartmentid" msg="请选择部门" vtype="combo">
				      </select>
				    </div>
				  </div>
				  <div class="form-group">
					<label for="bank" class="col-sm-2 control-label">开户行</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="userInfo.bank" msg="请输入开户行" id="bank"  placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="cardno" class="col-sm-2 control-label">银行卡号</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="userInfo.cardno" msg="请输入银行卡号" id="cardno"  placeholder="">
					</div>
					</div>
			</div>
			</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn-sm" id="saveemp">提交</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>
$('#addemp').modal('show');

//加载部门下拉框
Q.getDepartment("#fdepartmentid");

$('#saveemp').click(function(){
	if(!$('#form_addemp').checkForm())return;
	
	var o = $('#form_addemp').serializeObject();
	
	$._ajax({
		url: Q.url+"/employee/addemployee.action",
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