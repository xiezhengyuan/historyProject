<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		员工信息
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			查看
		</small>
	</h1>
</div><!-- /.page-header -->
<ul class="nav nav-tabs" role="tablist">
  <li role="presentation" class="active"><a href="#tab_userinfo" role="tab" data-toggle="tab">基本信息</a></li>
  <li id="panel_lower" role="presentation"><a href="#tab_lower" role="tab" data-toggle="tab">下级成员</a></li>
  <li id="panel_transaction" role="presentation"><a href="#tab_transaction" role="tab" data-toggle="tab">交易统计</a></li>
  <li style="position: relative;float: right;">
	<button  class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="返回" id="returnbfbtn" onclick="returnbfbtn(this)">返回</button>
  </li>
</ul>
<div class="tab-content">
	<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_userinfo">
   <form class="form-horizontal" role="form" id="form_editemp">
   <input type="hidden" class="form-control" id="userinfoid" name="id" placeholder="">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="name" msg="请输入姓名" id="name"  placeholder="">
					</div>
					</div>
			
				  <div class="form-group">
				    <label for="sex" class="col-sm-2 control-label">性别</label>
				    <div class="col-sm-10">
				      <select  class="form-control" id="sex" name="sex">
				      <option value="男" selected>男</option>
				      <option value="女" >女</option>
				      </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
					<label for="username" class="col-sm-2 control-label">用户名</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="username" msg="请输入用户名" id="username"  placeholder="">
					</div>
					</div>
				  
				  <div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">手机号</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="mobile" msg="请输入手机号" id="mobile" placeholder="">
					</div>
					</div>
					
					<div class="form-group">
				    <label for="sex" class="col-sm-2 control-label">部门</label>
				    <div class="col-sm-10">
				      <select  class="form-control" id="fdepartmentid" name="fdepartmentid" msg="请选择部门" vtype="combo">
				      </select>
				    </div>
				  </div>
				  <div class="form-group">
					<label for="bank" class="col-sm-2 control-label">开户行</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="bank" msg="请输入开户行" id="bank"  placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="cardno" class="col-sm-2 control-label">银行卡号</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="cardno" msg="请输入银行卡号" id="cardno"  placeholder="">
					</div>
					</div>
  
  <div class="modal-footer">
		<button type="button" class="btn btn-primary btn-sm" id="editemployee">修改</button>
	</div>
	</form>
  </div>
  
  <!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_lower"></div>
  
   <!-- panel3 start -->
  <div role="tabpanel" class="tab-pane" id="tab_transaction"></div>
</div>
<script>
//加载部门下拉框
Q.getDepartment("#fdepartmentid");

var userinfoid = '<%=request.getParameter("userinfoid")%>';

//根据ID查询部门信息
Q.getObjById("UserInfo",userinfoid,function(d){
	
	$('#form_editemp').resetObjectForm(d);
})

function returnbfbtn(){
	var from = "employee/employee.jsp";
	Q.viewJsp(Q.page_content,from);
}


$('#editemployee').click(function(){
	if(!$('#form_editemp').checkForm())return;
	
	var o = $('#form_editemp').serializeObject();
	
	$._ajax({
		url: Q.url+"/employee/modifyemployee.action",
		data:o,
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
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

//查看下级成员
$('#panel_lower').click(function(){
	Q.viewJsp("#tab_lower","employee/lowermember.jsp?userinfoid="+userinfoid);
})
</script>