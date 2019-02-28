<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="addcompany" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					新增地址
				</h4>
			</div>
			<div class="modal-body" >
			<div class="form-horizontal" role="form" id="form_addcompany">
				
			
					
					
				<div class="form-group">
				    <label for="mobile" class="col-sm-2 control-label">地址</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" name="address" msg="请输入地址" id="address"  placeholder="">
					</div>
					</div>
			
				
				 
			</div>
			</div>
		<div class="modal-footer">
				
				<button type="button" class="btn btn-primary btn-sm" id="savecompany">确认新增</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>

$('#addcompany').modal('show');

$('#savecompany').click(function(){
	if(!$('#form_addcompany').checkForm())return;
	
	var o = {};
	
	o.address=$("#address").val();
	
	
	if($("#address").val()==null||$("#address").val().length==0){
		 Q.toastr("提示","不能存空地址",'warning');
			return; 
	}
	
	$._ajax({
		url: Q.url+"/applyinfo/saveAdress.action",
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