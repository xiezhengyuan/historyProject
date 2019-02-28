<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="lowerdetail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					用户详情
				</h4>
			</div>
			<div class="modal-body" >
			 <form class="form-horizontal" role="form" id="form_lowerdetail">
   				<input type="hidden" class="form-control" id="userinfoid" name="id" placeholder="">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="name" msg="请输入姓名" id="name"  placeholder="" readonly>
					</div>
					</div>
			
				  <div class="form-group">
				    <label for="sex" class="col-sm-2 control-label">性别</label>
				    <div class="col-sm-10">
				      <select  class="form-control" id="sex" name="sex" readonly>
				      <option value="男" selected>男</option>
				      <option value="女" >女</option>
				      </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
					<label for="username" class="col-sm-2 control-label">用户名</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="username" msg="请输入用户名" id="username"  placeholder="" readonly>
					</div>
					</div>
				  
				  <div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">手机号</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="mobile" msg="请输入手机号" id="mobile" placeholder="" readonly>
					</div>
					</div>
					
					<div class="form-group">
				    <label for="sex" class="col-sm-2 control-label">部门</label>
				    <div class="col-sm-10">
				      <select  class="form-control" id="departmentid" name="fdepartmentid" msg="请选择部门" vtype="combo" readonly>
				      </select>
				    </div>
				  </div>
				  <div class="form-group">
					<label for="bank" class="col-sm-2 control-label">开户行</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="bank" msg="请输入开户行" id="bank"  placeholder="" readonly>
					</div>
					</div>
					
					<div class="form-group">
					<label for="cardno" class="col-sm-2 control-label">银行卡号</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="cardno" msg="请输入银行卡号" id="cardno"  placeholder="" readonly>
					</div>
					</div>
					
					<div class="form-group">
					<label for="cardno" class="col-sm-2 control-label">上级姓名</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="parentname" msg="请输入上级姓名" id="parentname"  placeholder="" readonly>
					</div>
					</div>
					
					<div class="form-group">
					<label for="cardno" class="col-sm-2 control-label">上级电话</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="parentmobile" msg="请输入上级电话" id="parentmobile"  placeholder="" readonly>
					</div>
					</div>
			</form>
			</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
				<!-- <button type="button" class="btn btn-primary btn-sm" id="saveemp">提交</button> -->
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>
$('#lowerdetail').modal('show');

//加载部门下拉框
Q.getDepartment("#departmentid");

var userinfoid = '<%=request.getParameter("userinfoid")%>';

//根据ID查询部门信息
Q.getObjById("UserInfo",userinfoid,function(d){
	
	$('#form_lowerdetail').resetObjectForm(d);
	
	Q.getObjById("UserInfo",d.fparentid,function(r){
		$('#parentname').val(r.name);
		$('#parentmobile').val(r.mobile);
	})
})
</script>