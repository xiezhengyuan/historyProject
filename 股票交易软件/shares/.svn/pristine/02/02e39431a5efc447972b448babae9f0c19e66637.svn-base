<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal  inmodal fade" id="adduser" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
		<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					新增
				</h4>
			</div>
				<div class="modal-body" >
				<div class="form-horizontal" role="form" id="form_adduser">
					<div class="form-group">
					<label for="menudetail" class="col-sm-2 control-label">分类名称</label>
					<div class="col-sm-10">
					<textarea rows="" cols=""  name="menudetail" msg="请输入内容" id="menudetail" placeholder=""></textarea>
					
					</div>
					</div>
	</div>
	</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn-sm" id="saveuser">提交</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<script>
$('#adduser').modal('show');


$('#saveuser').click(function(){
	if(!$('#form_adduser').checkForm())return;
	if(!$('#form_adduser').checkformatForm())return;
	
	
	var o = {};
	
	o.name=$('#menudetail').val();
	

	$._ajax({
		url: Q.url+"/postings/addStyle.action",
		data:o,
		success: function(data){
			Q.toastr("提示",data.op,'success');
			//reloadgrid
			$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
			/* if( typeof(data) == 'object'){ */
				/* if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					//reloadgrid
					$("#"+Q.grid_selector).setGridParam({postData:postData,page:1}).trigger("reloadGrid"); 
				}else if(data.op == 'fail')
					Q.toastr("提示",data.msg,'error');
				else
					Q.toastr("提示","操作失败",'error');
			}else {
				Q.toastr("提示","操作失败",'error');
			} */
		}
	});
	
	
})
</script>