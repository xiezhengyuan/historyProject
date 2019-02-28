<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<<style type="text/css">

.col-sm-10{
    width: 50%;
}
</style>
<div class="page-header">
	<h1>
		代理详情
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			列表
		</small>
	</h1>
</div><!-- /.page-header -->

<div class="tab-content">
<!-- panel1 start -->
  <div role="tabpanel" class="tab-pane active" id="tab_orderinfo_state2">
  		 <form class="form-horizontal" role="form" id="form_edituser">
            <input type="hidden" class="form-control" id="id" name="id" placeholder="">
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
					
					<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="name"  id="name" readonly="readonly" >
					</div>
					</div>
					
					
					<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">手机号码</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="mobile"  id="mobile" readonly="readonly" >
					</div>
					</div>
					
					
					
					<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">拥有娃娃机数量</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="mechonumber"  id="mechonumber" readonly="readonly" >
					</div>
					</div>
					
					
					
					
					<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">娃娃机消费总喵比</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="miaobinumber"  id="miaobinumber" readonly="readonly" >
					</div>
					</div>
					
			</form>
			 
				<button style="margin-left: 70%"  class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="top" title="确认修改" id="sumbit" onclick="updatedaili()">确认修改</button>			
  	         
  	</div>
  	
  	<!-- panel2 start -->
  	
  
  

 
  	
</div>		

<script>

//加载部门下拉框
/* Q.getProxylevel("#proxylevel"); */
 
 var id = '<%=request.getParameter("id")%>';
 
 function query(){
	 $._ajax({
		    url: Q.url+"/agentinfo/updateagent.action",
			data:{"id":id},
			success: function(data){
				if( typeof(data) == 'object'){
					if( data.op == 'success' ){
						
						$("#id").val(data.id);
						$("#login").val(data.username);
						$("#password").val(data.password);
						$("#name").val(data.name);
						$("#mobile").val(data.mobile);
						$("#mechonumber").val(data.mechonumber);
						$("#miaobinumber").val(data.miaobinumber);

						
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
  
 query();

function  updatedaili(){
	var username=$.trim($("#login").val());
	var password=$.trim($("#password").val());
	if(username==''||password==''){
		Q.toastr("提示","登陆账号和密码不能为空",'error');
		return;
	}
	Q.confirm("确定修改么？",function(r){
		if(r){
			$._ajax({
				url: Q.url+"/agentinfo/changeinfo.action",
				data:{"id":	$("#id").val(),"username":username,"password":password},
				success: function(data){
					if( typeof(data) == 'object'){
						if( data.op == 'success' ){
							Q.toastr("提示","操作成功",'success');
							query();
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



function returnbfbtn(){
	var from = "agent/agentapply.jsp";
	Q.viewJsp(Q.page_content,from);
}
</script>

