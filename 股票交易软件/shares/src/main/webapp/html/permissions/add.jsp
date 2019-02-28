<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="addpermissions" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
				&times;
			</button>
			<h4 class="modal-title" id="myModalLabel">
				权限设置
			</h4>
		</div>
		<div class="modal-body" >
			<div class="form-horizontal" role="form" id="form_addpm">
				<div class="form-group">
					<label for="username" class="col-sm-2 control-label">账号</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="username" msg="请输入账号" id="username"  placeholder="">
						<input type="hidden" name="id" id="accountinfoid" >
					</div>
				</div>
				
				<div class="form-group">
					<label for="password" class="col-sm-2 control-label">密码</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="password" msg="请输入密码" id="password"  placeholder="">
					</div>
				</div>
				
				<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">手机</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="mobile" msg="请输入手机" id="mobile"  placeholder="">
					</div>
				</div>
				
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">名称</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="name" msg="请输入名称" id="na"  placeholder="">
					</div>
				</div>
				
				<div class="form-group" style="text-align: left;">
						<label for="name" class="col-sm-2 control-label">权限设置</label>
						<hr>
						<div class="col-sm-offset-2 col-sm-10">
							<div class="checkbox" id="menu">
							</div>
						</div>
				</div>
				<div class="form-group" style="text-align: center;">
					<input type="button" value="全选" class="btn btn-info btn-sm" id="selectAll">&nbsp;&nbsp;
					<input type="button" value="全不选" class="btn btn-info btn-sm" id="unSelect">&nbsp;&nbsp;
					<input type="button" value="反选" class="btn btn-info btn-sm" id="reverse">
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn-sm" id="savepermissions">提交</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
</div>
<script>

var id = <%=request.getParameter("id")%>;
$('#addpermissions').modal('show');

//菜单展示
menu(id);
function menu(id){
	if(id==null || id==''){
		$._ajax({
			url: Q.url+"/permissions/queryMenu.action",
			success: function(data){
				if( typeof(data) == 'object'){
					if( data.op == 'success' ){
						var menu=data.rows;
						for (var i = 0; i < menu.length; i++) {
							$("#menu").append(
									"<label style='width: 30%;padding-bottom: 10px'>"
									+"<input type='checkbox' name='menu' value='"+menu[i].id+"'><span> "+menu[i].name+"&nbsp;&nbsp;</span>"
									+"</label>");
						}
					}else if(data.op == 'fail')
						Q.toastr("提示",data.msg,'error');
					else
						Q.toastr("提示","操作失败",'error');
				}else {
					Q.toastr("提示","操作失败",'error');
				}
			}
		});
	}else{
		$._ajax({
			url: Q.url+"/permissions/queryMenu.action",
			data:{'id':id},
			success: function(data){
				if( typeof(data) == 'object'){
					if( data.op == 'success' ){
						var defaultaccount=data.defaultaccount;
						if(defaultaccount!=1){
							$("#username").val(data.username);
							$("#username").attr("readonly","readonly");
							$("#password").val("******");
							$("#password").attr("readonly","readonly");
							$("#mobile").val(data.mobile);
							$("#mobile").attr("readonly","readonly");
							$("#na").val(data.name);
							$("#na").attr("readonly","readonly");
							$("#accountinfoid").val(id);
							$("#accountinfoid").attr("readonly","readonly");
						}else{
							$("#username").val(data.username);
							$("#username").attr("readonly","readonly");
							$("#password").val(data.password);
							$("#mobile").val(data.mobile);
							$("#na").val(data.name);
							$("#accountinfoid").val(id);
						}
						var menu=data.rows;
						for (var i = 0; i < menu.length; i++) {
							$("#menu").append(
									"<label style='width: 30%;padding-bottom: 10px'>"
									+"<input type='checkbox' "+menu[i].checked+" name='menu' value='"+menu[i].id+"'><span> "+menu[i].name+"&nbsp;&nbsp;</span>"
									+"</label>");
						}
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
}

$('#savepermissions').click(function(){
	if(!$('#form_addpm').checkForm())return;
	var o = $('#form_addpm').serializeObject();
	var chk_value =[];//定义一个数组      
    $('input[name="menu"]:checked').each(function(){//遍历每一个名字为nodes的复选框，其中选中的执行函数      
        chk_value.push($(this).val());//将选中的值添加到数组chk_value中      
    });  
    o.menu = chk_value.join(",");
	$._ajax({
		url: Q.url+"/permissions/addPermissions.action",
		data:o,
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					//reloadgrid
					$('#addpermissions').modal('hide');
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

/* 全选 */
$("#selectAll").click(function () {
	$("input:checkbox").each(function () {
		$(this).prop('checked', true);
	});   
});   
      
/* 全不选 */
$("#unSelect").click(function () {
	$("input:checkbox").removeAttr("checked");
});
      
/* 反选 */
$("#reverse").click(function () {
	$("input:checkbox").each(function () {
		this.checked = !this.checked;
	});
});
</script>