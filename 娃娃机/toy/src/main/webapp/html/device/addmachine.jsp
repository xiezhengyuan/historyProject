<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="addmachine" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					新增设备
				</h4>
			</div>
			<div class="modal-body" >
			<div class="form-horizontal" role="form" id="form_addmachine">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">设备编号</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="machineInfo.machineno" msg="请输入设备编号" id="machineno"  placeholder="">
					</div>
				</div>
			</div>
			</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn-sm" id="savemachine">提交</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>
$('#addmachine').modal('show');

//加载部门下拉框
/* Q.getDepartment("#fdepartmentid"); */

$('#savemachine').click(function(){
	if(!$('#form_addmachine').checkForm())return;
	
	var o = $('#form_addmachine').serializeObject();
	o.addmachineid = $('#machineno').val();
	$._ajax({
		url: Q.url+"/device/addmachine.action",
		data:o,
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					//reloadgrid
					$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
					$('#addmachine').modal('hide');
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