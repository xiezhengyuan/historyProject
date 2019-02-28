<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="addpostingstyle" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					新增帖子分类
				</h4>
			</div>
			<div class="modal-body" >
			<div class="form-horizontal" role="form" id="form_addpostingstyle">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">分类名称</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="postingStyle.name" msg="请输入设备编号" id="postingstylename"  placeholder="">
					</div>
				</div>
			</div>
			</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn-sm" id="savepostingstyle">提交</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>
$('#addpostingstyle').modal('show');

//加载部门下拉框
/* Q.getDepartment("#fdepartmentid"); */

$('#savepostingstyle').click(function(){
	if(!$('#form_addpostingstyle').checkForm())return;
	
	//var o = $('#form_addpostingstyle').serializeObject();
	var stylename = $('#postingstylename').val();
	
	$._ajax({
		url: Q.url+"/posting/addpostingstyle.action",
		data:{"stylename":stylename},
		success: function(data){
			if( typeof(data) == 'object'){
				if(data.id !=null){
					Q.toastr("提示","操作成功",'success');
					//reloadgrid
					$("#fpostingstyle").html('<option value="-1"  selected>请选择</option>');
					querypostingstyle("#fpostingstyle");
				}
				else
					Q.toastr("提示","操作失败",'error');
			}else {
				Q.toastr("提示","操作失败",'error');
			}
		}
	});
	
	
})
</script>