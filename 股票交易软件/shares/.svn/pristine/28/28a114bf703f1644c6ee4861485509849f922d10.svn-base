<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="addsaleman" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					新增业务员
				</h4>
			</div>
			<div class="modal-body" >
			<div class="form-horizontal" role="form" id="form_addsaleman">
			
				<div class="form-group">
					<label for="saleman" class="col-sm-2 control-label">名称</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="saleman" msg="请输入业务员名称" id="saleman"  placeholder="">
					</div>
					</div>
			
				  <div class="form-group">
				    <label for="mobile" class="col-sm-2 control-label">联系方式</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="mobile" msg="请输入联系方式" id="mobile"  placeholder="">
					</div>
					</div>
				  
				  <div class="form-group">
					<label for="proportion" class="col-sm-2 control-label">分成比例</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="proportion" msg="请输入分成比例" id="proportion" placeholder="">
					</div>
					</div>
					
					<!-- <div class="form-group">
					<label for="extensionurl" class="col-sm-2 control-label">推广链接</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="extensionurl" msg="请输入链接" id="extensionurl"  placeholder="">
					</div>
					</div> -->
					
					<hr style="height:1px;border:none;border-top:1px solid red;" />
					 
				  <div class="form-group">
					<label for="usernames" class="col-sm-2 control-label">账号设置</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="usernames" msg="请输入账号" id="usernames"  placeholder="">
					</div>
					</div>
					
					<div class="form-group">
					<label for="password" class="col-sm-2 control-label">密码设置</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="password" msg="请输入密码" id="password"  placeholder="">
					</div>
					</div>
			</div>
			</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary btn-sm" id="savesaleman">确认新增</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>

$('#addsaleman').modal('show');

$('#savesaleman').click(function(){
	if(!$('#form_addsaleman').checkForm())return;
	
	var o = {};
	o.saleman=$("#saleman").val();
	o.mobile=$("#mobile").val();
	o.proportion=$("#proportion").val();
	o.extensionurl = $("#extensionurl").val();
	o.username=$("#usernames").val();
	o.password=$("#password").val();

	$._ajax({
		url: Q.url+"/company/addsaleman.action",
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