<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		公司信息
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			查看
		</small>
	</h1>
</div><!-- /.page-header -->
<ul class="nav nav-tabs" role="tablist">
  <li role="presentation" class="active"><a href="#tab_company" role="tab" data-toggle="tab">基本信息</a></li>
  <li id="panel_lower" role="presentation"><a href="#tab_lower" role="tab" data-toggle="tab">公司成员</a></li>
  <li id="panel_transaction" role="presentation"><a href="#tab_transaction" role="tab" data-toggle="tab">交易统计</a></li>
  <li id="panel_user" role="presentation"><a href="#tab_user" role="tab" data-toggle="tab">用户统计</a></li>
  <li style="position: relative;float: right;">
	<button  class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="返回" id="returnbfbtn" onclick="returnbfbtn(this)">返回</button>
  </li>
</ul>
<div class="tab-content">
	<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_company">
   <form class="form-horizontal" role="form" id="form_editcompany">
   <input type="hidden" class="form-control" id="companyid" name="id" placeholder="">
				<div class="form-group">
					<label for="company" class="col-sm-2 control-label">公司名称</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="company" msg="请输入公司名称" id="company"  placeholder="">
					</div>
					</div>
			
				  <div class="form-group">
				    <label for="mobile" class="col-sm-2 control-label">联系方式</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="mobile" msg="请输入联系方式" id="mobile"  placeholder="">
					</div>
					</div>
				  
				  <div class="form-group">
					<label for="name" class="col-sm-2 control-label">负责人</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="name" msg="请输入负责人" id="name"  placeholder="">
					</div>
					</div>
				  
				  <div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">分成比例</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="proportion" msg="请输入分成比例" id="proportion" placeholder="">
					</div>
					</div>
					
					<hr style="height:1px;border:none;border-top:1px solid red;" />
					 <!-- <div class="form-group">
				    <label for="sex" class="col-sm-2 control-label">部门</label>
				    <div class="col-sm-10">
				      <select  class="form-control" id="fdepartmentid" name="fdepartmentid" msg="请选择部门" vtype="combo">
				      </select>
				    </div> 
				  </div>--> 
				  <div class="form-group">
					<label for="username" class="col-sm-2 control-label">账号设置</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="username" msg="请输入账号" id="username"  placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="password" class="col-sm-2 control-label">密码设置</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="password" msg="请输入密码" id="password"  placeholder="">
					</div>
					</div>
					
					
  
  <div class="modal-footer">
		<button type="button" class="btn btn-primary btn-sm" id="editcompany">修改</button>
	</div>
	</form>
  </div>
  
  <!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_lower"></div>
  
   <!-- panel3 start -->
  <div role="tabpanel" class="tab-pane" id="tab_transaction"></div>
  
   <!-- panel4 start -->
  <div role="tabpanel" class="tab-pane" id="tab_user"></div>
</div>
<script>
//加载部门下拉框
/* Q.getDepartment("#fdepartmentid"); */

var companyid = '<%=request.getParameter("companyid")%>';

//根据ID查询信息
Q.getObjById("Company",companyid,function(d){
	
	$('#company').val(d.company);
	var fcompanyid = d.id;
	$._ajax({
		url: Q.url+"/company/queryuserbycompany.action",
		data:{"fcompanyid":fcompanyid},
		success: function(data){
			$("#mobile").val(data.mobile);
			$("#name").val(data.name);
			$("#proportion").val(data.proportion);
			$("#username").val(data.username);
			$("#password").val(data.password);
		}
	});
	
})

function returnbfbtn(){
	var from = "company/company.jsp";
	Q.viewJsp(Q.page_content,from);
}


$('#editcompany').click(function(){
	if(!$('#form_editcompany').checkForm())return;
	var o = {};
	o.companyid = companyid;
	o.company=$("#company").val();
	o.mobile=$("#mobile").val();
	o.proportion=$("#proportion").val();
	o.name=$("#name").val();
	o.username=$("#username").val();
	o.password=$("#password").val();
	
	$._ajax({
		url: Q.url+"/company/modifycompany.action",
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

//查看公司成员
$('#panel_lower').click(function(){
	Q.viewJsp("#tab_lower","company/companymember.jsp?companyid="+companyid);
})
//交易统计
$('#panel_transaction').click(function(){
	Q.viewJsp("#tab_transaction","company/transactionstatistic.jsp?companyid="+companyid);
})
//用户统计
$('#panel_user').click(function(){
	Q.viewJsp("#tab_user","company/userstatistic.jsp?companyid="+companyid);
})
</script>