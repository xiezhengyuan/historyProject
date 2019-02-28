<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="addorder2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					关于娃娃发货申请的回复
				</h4>
			</div>
			<div class="modal-body" >
			<form class="form-horizontal" role="form" id="form_addorder">
			   
			
					
					
					
					
					<div class="form-group">
					<label for="expressagecompany" class="col-sm-2 control-label">请输入回复内容</label>
					<div class="col-sm-10">
					    <textarea id="mycontent" rows="6" cols="70"  style="resize: none;"></textarea>
					</div>
					</div>
				
					
			</form>
			</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn-sm" id="saveorder">提交</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>
var id = '<%=request.getParameter("id")%>';


$('#addorder2').modal('show');





$('#saveorder').click(function(){
	if(!$('#form_addorder').checkForm())return;
	var content=$("#mycontent").val();
	if(content.length>200){
		Q.toastr("提示","字数太长",'error');
		return;
	}
	$._ajax({
		url: Q.url+"/device/sendtouser.action",
		data:{"content":content ,"id":id},
		
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					$(".close").click();
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