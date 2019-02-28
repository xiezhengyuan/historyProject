<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade" id="addcompany" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
<form class="form-horizontal" role="form" id="form_editgoods" style="">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					新增用户
				</h4>
			</div>
			<div class="modal-body" >
			<div class="form-horizontal" role="form" id="form_addcompany">
				
				<div class="form-group">
					<label for="photourl" class="col-sm-2 control-label">图片</label>
					<div class="col-sm-10">
					<div id="goodspreview" class="upload_preview" style="border:1px dashed  #bbb"></div>
					</div>
					</div>
					
				
	
			</div>
			</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default btn-sm" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary btn-sm" id="savecompany">确认新增</button>
			</div>
			</form>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script>
var myarr =  [];
Q.logimgs = [];
Q.viewlogimg("#goodspreview");
$('#addcompany').modal('show');

$('#savecompany').click(function(){
var o = $('#form_editgoods').serializeObject(); 
	
	
	/* if(Q.logimgs.length==0){
		 Q.toastr("提示","请至少上传一张图片",'warning');
		return; 
	}  */
	//检测图片数组数据是否发生变化
	/* var flag = Q.checkarrsame(myarr,Q.logimgs); */
	o.imgarr = $.toJSON(Q.logimgs);
	console.log(o.imgarr);
	/* o.id = id; */
	/* o.postingscontent=postingscontent; */
	
	/* o.flag = flag; */
	
	$._ajax({
		url: Q.url+"/orderinfo/addbanner.action",
		data:o,
		success: function(data){
			if( typeof(data) == 'object'){
				if( data.op == 'success' ){
					Q.toastr("提示","操作成功",'success');
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