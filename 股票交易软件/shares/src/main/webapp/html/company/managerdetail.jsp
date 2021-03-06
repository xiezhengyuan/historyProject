<%@page import="com.hxy.isw.entity.AccountInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-header">
	<h1>
		公司信息
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			公司成员
		</small>
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			<span id="managername"></span>
		</small>
	</h1>
</div><!-- /.page-header -->
<ul class="nav nav-tabs" role="tablist">
  <li role="presentation" class="active"><a href="#tab_manager" role="tab" data-toggle="tab">基本信息</a></li>
  <li id="panel_salesman" role="presentation"><a href="#tab_salesman" role="tab" data-toggle="tab">业务员</a></li>
  
  <li style="position: relative;float: right;">
	<button  class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="返回" id="returnbfbtn" onclick="returnbfbtn(this)">返回</button>
  </li>
</ul>
<div class="tab-content">
	<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_manager">
   <form class="form-horizontal" role="form" id="form_editmanager">
   <input type="hidden" class="form-control" id="companyid" name="id" placeholder="">
			
				
				<div class="form-group">
					<label for="company" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-10">
				<!-- 	<input type="text" class="form-control" name="company" msg="请输入姓名" id="company"  placeholder=""> -->
					<h5 id="name"></h5>
					</div>
					</div>
			
				  <div class="form-group">
				    <label for="mobile" class="col-sm-2 control-label">联系方式</label>
					<div class="col-sm-10">
					<!-- <input type="text" class="form-control" name="mobile" msg="请输入联系方式" id="mobile"  placeholder=""> -->
					<h5 id="mobile"></h5>
					</div>
					</div>
				  
				  <div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">分成比例</label>
					<div class="col-sm-10">
					<!-- <input type="text" class="form-control" name="proportion" msg="请输入分成比例" id="proportion" placeholder=""> -->
					<h5 id="proportion"></h5>
					</div>
					</div>
					
					<div class="form-group" id = "manageracc" style="display: none;">
					<label for="username" class="col-sm-2 control-label">账号设置</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="username" msg="请输入账号" id="username"  placeholder="">
					</div>
					</div>
					
					<div class="form-group" id ="managerpwd" style="display: none;">
					<label for="password" class="col-sm-2 control-label">密码设置</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="password" msg="请输入密码" id="password"  placeholder="">
					</div>
					</div>
					
					
					
  <div class="modal-footer" id ="editbtn" style="display: none;">
		<button type="button" class="btn btn-primary btn-sm" id="editmanager">修改</button>
	</div>
	</form>
  </div>
  
  <!-- panel2 start -->
  <div role="tabpanel" class="tab-pane" id="tab_salesman"></div>
  
</div>
<script>
//加载部门下拉框
/* Q.getDepartment("#fdepartmentid"); */
var roles = '<%=((AccountInfo)session.getAttribute("loginEmp")).getRole()%>';

if(roles==2){
	$("#manageracc").css("display", "block");
	$("#managerpwd").css("display", "block");
	$("#editbtn").css("display", "block");
}


var companyid = '<%=request.getParameter("companyid")%>';
var accountinfoid = '<%=request.getParameter("accountinfoid")%>';

//根据ID查询信息
Q.getObjById("AccountInfo",accountinfoid,function(d){
	
	$('#managername').html(d.name+"经理");
    $('#name').html(d.name);
    $('#mobile').html(d.mobile);
    $('#proportion').html(d.proportion);
    $("#username").val(d.username);
	$("#password").val(d.password);
	
})

function returnbfbtn(){
	var from = "company/company.jsp";
	if(roles==0){
	Q.viewJsp(Q.page_content,from);
	}
	if(roles==2){
		from = "employee/employee.jsp";
		Q.viewJsp(Q.page_content,from);
	}
}


$('#editmanager').click(function(){
	if(!$('#form_editmanager').checkForm())return;
	var o = {};
	o.companyid = companyid;
	o.manager=$("#name").text();
	o.mobile=$("#mobile").text();
	o.proportion=$("#proportion").text();
	o.username=$("#username").val();
	o.password=$("#password").val();
	o.id=accountinfoid;
	$._ajax({
		url: Q.url+"/company/modifymanager.action",
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
$('#panel_salesman').click(function(){
	Q.viewJsp("#tab_salesman","company/manager_salesmanmember.jsp?accountinfoid="+accountinfoid);
})

</script>