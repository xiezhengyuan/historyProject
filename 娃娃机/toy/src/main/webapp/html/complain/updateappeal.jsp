<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="addappeal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					修改系统申诉选项
				</h4>
			</div>
			<div class="modal-body" style="height: 200px;">
			<div class="form-horizontal" role="form" id="form_adduser">
				<div class="form-group">
					<label for="username" class="col-sm-2 control-label">申诉内容:</label>
					<div class="col-sm-10">
					<textarea style="height: 180px;" class="form-control" msg="请输入申诉内容" id="allpealcontent"  placeholder="">
					
					</textarea>
					</div>
					</div>
					
					
				
			</div>
			</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn-sm" id="saveappeal">提交</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>
$('#addappeal').modal('show');


var id = '<%=request.getParameter("id")%>';



$._ajax({
		url: Q.url+"/UserAppeal/updateappeal.action",
		data:{"id":id,"type":"kan"},
		success: function(data){
			if( typeof(data) == 'object'){
				$("#allpealcontent").val(data.content);
			}else {
				Q.toastr("提示","操作失败",'error');
			}
		}
	});


$('#saveappeal').click(function(){
	var content=$("#allpealcontent").val();
	if($.trim(content)==''){
		Q.toastr("提示","请输入申诉内容",'error');
		return;
	}
	$._ajax({
		url: Q.url+"/UserAppeal/updateappeal.action",
		data:{"newcontent":$.trim(content),"id":id,"type":"gai"},
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
					$(".close").click();
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