<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="addexample" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
				&times;
			</button>
			<h4 class="modal-title" id="myModalLabel">
				新增牛人
			</h4>
		</div>
		<div class="modal-body" >
			<div class="form-horizontal" role="form" id="form_addex">
				<div class="form-group">
					<label for="nickname" class="col-sm-2 control-label">牛人昵称</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="nickname" msg="请输入牛人昵称" id="nickname"  placeholder="">
						<input type="hidden" name="id" id="accountinfoid" >
					</div>
				</div>
				<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">联系方式</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="mobile" vrule="tel" msg="请输入联系方式" id="mobile"  placeholder="">
					</div>
				</div>
				<div class="form-group">
					<label for="golds" class="col-sm-2 control-label">金币数量</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="golds" vrule="num" msg="请输入金币数量" id="golds"  placeholder="">
					</div>
				</div>
				<div class="form-group">
					<label for="cardno" class="col-sm-2 control-label">银行卡号</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="cardno" msg="请输入银行卡号" id="cardno"  placeholder="">
					</div>
				</div>
				<div class="form-group">
					<label for="bank" class="col-sm-2 control-label">开户行</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="bank" msg="请输入开户行" id="bank"  placeholder="">
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="name" msg="请输入身份证" id="name"  placeholder="">
					</div>
				</div>
				<div class="form-group">
					<label for="paparno" class="col-sm-2 control-label">身份证号</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="paparno" msg="请输入身份证" vrule="card" id="paparno"  placeholder="">
					</div>
				</div>
				<hr>
				<div class="form-group">
					<label for="username" class="col-sm-2 control-label">账号设置</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="username" vrule="tel" msg="请输入账号设置" id="username"  placeholder="请填写手机号">
					</div>
				</div>
				
				<div class="form-group">
					<label for="password" class="col-sm-2 control-label">密码设置</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="password" msg="请输入密码设置" id="password"  placeholder="">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn-sm" id="saveexample">提交</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
</div>
<script>

var id = <%=request.getParameter("id")%>;
$('#addexample').modal('show');


$('#saveexample').click(function(){
	if(!$('#form_addex').checkForm())return;
	var o = $('#form_addex').serializeObject();
	if(!$('#form_addex').checkformatForm()){return;}
	
	$._ajax({
		url: Q.url+"/example/addExample.action",
		data:o,
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					//reloadgrid
					$('#addexample').modal('hide');
					$("#"+Q.grid_selector).setGridParam({postData:postData,page:$('#'+Q.grid_selector).getGridParam('page')}).trigger("reloadGrid"); 
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