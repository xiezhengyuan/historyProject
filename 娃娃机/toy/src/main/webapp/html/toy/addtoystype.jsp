<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="addtoystype" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
				&times;
			</button>
			<h4 class="modal-title" id="myModalLabel">
				
			</h4>
		</div>
		<div class="modal-body" >
			<div class="form-horizontal" role="form" id="form_addtt">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">分类名称</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="toysType.name" msg="请输入分类名称" id="typename"  placeholder="">
						<input type="hidden" name="toysType.id" id="typeid" >
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn-sm" id="savetoystype">提交</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>

var id = <%=request.getParameter("id")%>;

title(id);
function title(id){
	if(id==null || id==''){
		$("#myModalLabel").html("新增娃娃分类");
	}else{
		$._ajax({
			url: Q.url+"/toy/findToysTypeById.action",
			data:{"id":id},
			success: function(data){
				if( typeof(data) == 'object'){
					if( data.op == 'success' ){
						//reloadgrid
						$("#typeid").val(data.id);
						$("#typename").val(data.name);
						
					}else if(data.op == 'fail'){
						Q.toastr("提示",data.msg,'error');
					}else{
						Q.toastr("提示","操作失败",'error');
					}
				}else {
					Q.toastr("提示","操作失败",'error');
				}
			}
		});
		
		$("#myModalLabel").html("修改娃娃分类");
	}
}

$('#addtoystype').modal('show');

//加载部门下拉框
/* Q.getDepartment("#fdepartmentid"); */

$('#savetoystype').click(function(){
	if(!$('#form_addtt').checkForm())return;
	
	var o = $('#form_addtt').serializeObject();
	
	$._ajax({
		url: Q.url+"/toy/addToysType.action",
		data:o,
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					//reloadgrid
					$('#addtoystype').modal('hide');
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